package com.lnlyj.socket;

import android.text.TextUtils;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * Created by Wanglei on 2019/4/28.
 */

public class LnlyjSocketInfo implements Serializable {

    private static final long serialVersionUID = 5798288579974975277L;

    private String ip;
    private int port;

    public LnlyjSocketInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        return obj instanceof LnlyjSocketInfo && TextUtils.equals(((LnlyjSocketInfo) obj).ip, this.ip) && ((LnlyjSocketInfo) obj).port == port;
    }
}
