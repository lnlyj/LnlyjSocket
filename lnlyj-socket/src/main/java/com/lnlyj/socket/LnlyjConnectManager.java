package com.lnlyj.socket;

/**
 * Created by Wanglei on 2019/4/28.
 */

public class LnlyjConnectManager {

    private LnlyjSocketThread mSocket;
    private LnlyjSocketInfo mInfo;

    private final Object mSync = new Object();

    protected LnlyjConnectManager(LnlyjSocketInfo info) {
        this(info, null);
    }

    protected LnlyjConnectManager(LnlyjSocketInfo info, LnlyjSocketCallback callback) {
        this.mInfo = info;
        mSocket = new LnlyjSocketThread(info);
        if (callback != null) {
            mSocket.addCallback(callback);
        }
    }

    public LnlyjConnectManager connect() {
        if (!mSocket.isConnect()) {
            mSocket.start();
        }
        return this;
    }

    public LnlyjConnectManager disConnect() {
        LnlyjSocket.remove(mInfo);
        mSocket.release();
        mSocket = null;
        return this;
    }

    public LnlyjConnectManager sendData(ILnlyjSender sender) {
        if (mSocket != null && mSocket.isConnect()) {
            mSocket.sendData(sender.parse());
        }
        return this;
    }

    public LnlyjConnectManager addCallback(LnlyjSocketCallback callback) {
        mSocket.addCallback(callback);
        return this;
    }

    public LnlyjConnectManager removeCallback(LnlyjSocketCallback callback) {
        mSocket.removeCallback(callback);
        return this;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        return obj instanceof LnlyjConnectManager && ((LnlyjConnectManager) obj).mInfo.equals(this.mInfo);

    }
}
