package com.edison.io.netty.protocol.response;

import com.edison.io.netty.util.HexUtils;

/**
 * 文件名:com.edison.io.netty.protocol.response.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class Response {

    private int cmd;

    private String requestId;

    private String md5Key;

    private String code;

    private String message;

    private String tfp;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTfp() {
        return tfp;
    }

    public void setTfp(String tfp) {
        this.tfp = tfp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Response{");
        sb.append("cmd=").append(HexUtils.toHexString(cmd));
        sb.append(", requestId=").append(requestId);
        sb.append(", md5Key=").append(md5Key);
        sb.append(", code=").append(code);
        sb.append(", message=").append(message);
        sb.append(", tfp=").append(tfp);
        sb.append('}');
        return sb.toString();
    }


}
