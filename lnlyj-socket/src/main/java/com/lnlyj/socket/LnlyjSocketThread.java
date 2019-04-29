package com.lnlyj.socket;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.lnlyj.socket.LnlyjSocketCallback.ERROR_RECEIVED_DATA;
import static com.lnlyj.socket.LnlyjSocketCallback.ERROR_SEND_DATA;

/**
 * Created by Wanglei on 2019/4/28.
 */

class LnlyjSocketThread extends Thread implements Handler.Callback {

    private LnlyjSocketInfo mInfo;

    private Socket mSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private boolean isConnect;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private boolean isDisconnectByServer = false;

    private List<LnlyjSocketCallback> mCallbacks = new ArrayList<>();

    public LnlyjSocketThread(LnlyjSocketInfo info) {
        super("LnlyjSocketThread");

        mInfo = info;

        mHandlerThread = new HandlerThread("SocketHandler");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper(), this);
    }

    @Override
    public void run() {
        super.run();
        connect();

        receiveData();
    }

    @Override
    public boolean handleMessage(Message msg) {

        innerSendData((byte[]) msg.obj);

        return false;
    }

    private void connect() {
        try {
            mSocket = new Socket(mInfo.getIp(), mInfo.getPort());

            isConnect = mSocket.isConnected();

            if (isConnect) {

                mInputStream = mSocket.getInputStream();
                mOutputStream = mSocket.getOutputStream();
                callConnect();
                receiveData();
            }

        } catch (Exception e) {
            e.printStackTrace();
            isDisconnectByServer = true;
        } finally {
            callDisConnect(isDisconnectByServer);
            release();
        }
    }

    private void receiveData() {

        byte[] bytes = new byte[1024];

        int readLength = 0;

        while (isConnect) {

            try {
                readLength = mInputStream.read(bytes, 0, bytes.length);

                if (readLength > 0) {
                    byte[] data = new byte[readLength];
                    System.arraycopy(bytes, 0, data, 0, readLength);
                    callReceivedData(data);
                }

            } catch (Exception e) {
                e.printStackTrace();
                callError(ERROR_SEND_DATA);
            }

        }
    }

    private void innerSendData(byte[] data) {
        if (data == null) {
            throw new RuntimeException("data is null");
        }

        try {
            mOutputStream.write(data);
            mOutputStream.flush();
            callSendData(data);
        } catch (Exception e) {
            e.printStackTrace();
            callError(ERROR_RECEIVED_DATA);
        }
    }

    private void callConnect() {
        for (LnlyjSocketCallback listener : mCallbacks) {
            listener.onConnect();
        }
    }

    private void callDisConnect(boolean isServer) {
        for (LnlyjSocketCallback listener : mCallbacks) {
            listener.onDisConnect(isServer);
        }
    }

    private void callReceivedData(byte[] data) {
        for (LnlyjSocketCallback listener : mCallbacks) {
            listener.onReceived(data);
        }
    }

    private void callSendData(byte[] data) {
        for (LnlyjSocketCallback listener : mCallbacks) {
            listener.onSend(data);
        }
    }

    private void callError(int err) {
        for (LnlyjSocketCallback listener : mCallbacks) {
            listener.onError(err);
        }
    }

    public void sendData(byte[] data) {
        mHandler.sendMessage(mHandler.obtainMessage(1, data));
    }

    public void addCallback(LnlyjSocketCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    public void removeCallback(LnlyjSocketCallback callback) {
        if (callback != null) {
            mCallbacks.remove(callback);
        }
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void release() {
        try {

            mHandlerThread.quitSafely();

            if (mSocket != null) {
                mSocket.shutdownInput();
                mSocket.shutdownOutput();

                mSocket.close();
                isConnect = false;
                isDisconnectByServer = false;
                interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
