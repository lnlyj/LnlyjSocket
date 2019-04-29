package com.lnlyj.socket;

/**
 * Created by Wanglei on 2019/4/28.
 */

public interface LnlyjSocketCallback {

    public static final int ERROR_SEND_DATA = -1;
    public static final int ERROR_RECEIVED_DATA = -2;

    void onConnect();
    void onDisConnect(boolean isServer);
    void onSend(byte[] data);
    void onReceived(byte[] data);
    void onError(int err);
}
