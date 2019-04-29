package com.lnlyj.socket;

import java.util.HashMap;

/**
 * Created by Wanglei on 2019/4/28.
 */

public class LnlyjSocket {

    private static HashMap<LnlyjSocketInfo, LnlyjConnectManager> mManagerMap = new HashMap<>();
    private final static Object mSync = new Object();

    private LnlyjSocket() {

    }

    public static LnlyjConnectManager open(String ip, int port) {
        LnlyjSocketInfo info = new LnlyjSocketInfo(ip, port);
        return open(info);
    }

    public static LnlyjConnectManager open(LnlyjSocketInfo info) {

        return open(info, null);
    }

    public static LnlyjConnectManager open(LnlyjSocketInfo info, LnlyjSocketCallback callback) {
        synchronized (mSync) {
            LnlyjConnectManager manager = mManagerMap.get(info);

            if (manager == null) {
                manager = new LnlyjConnectManager(info, callback);
                mManagerMap.put(info, manager);
            }
            return manager;
        }
    }


    protected static void remove(LnlyjSocketInfo info) {
        synchronized (mSync) {
            if (mManagerMap.containsKey(info)) {
                mManagerMap.remove(info);
            }
        }
    }
}
