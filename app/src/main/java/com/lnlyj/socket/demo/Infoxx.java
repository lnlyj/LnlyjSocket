package com.lnlyj.socket.demo;

import android.text.TextUtils;

import com.lnlyj.socket.LnlyjSocketInfo;

/**
 * Created by Wanglei on 2019/4/29.
 */

public class Infoxx {

    String ip;

    int port;

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

        return obj instanceof Infoxx && TextUtils.equals(((Infoxx) obj).ip, this.ip) && ((Infoxx) obj).port == port;
    }


    @Override
    public int hashCode() {
        int result = ip.hashCode();
        result = 31 * result + port;
        return result;
    }
}
