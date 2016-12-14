package com.edison.io.netty.protocol.request;

/**
 * Created by admin on 2016/12/14.
 */
public class DownloadRequest extends AbstractRequest{

    private String md5Key;

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

}
