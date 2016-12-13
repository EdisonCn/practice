package com.edison.io.netty.protocol.request;

/**
 * 文件名:com.edison.io.netty.protocol.request.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public abstract class AbstractRequest {

    protected int cmd;

    protected String requestId;

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
}
