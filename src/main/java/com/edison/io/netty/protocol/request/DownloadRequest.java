package com.edison.io.netty.protocol.request;

import com.edison.io.netty.util.HexUtils;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadRequest{");
        sb.append("cmd=").append(HexUtils.toHexString(cmd)).append(',');
        sb.append("requestId=").append(requestId).append(',');
        sb.append("md5Key=").append(md5Key).append(',');
        sb.append('}');
        return sb.toString();
    }
}
