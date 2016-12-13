package com.edison.io.netty.protocol.request;

import com.edison.io.netty.util.HexUtils;

/**
 * 文件名:com.edison.io.netty.protocol.request.
 * 描述:
 * 作者: wangzhengfei
 * 创建日期: 2016-12-10
 * 修改记录:
 */
public class UploadRequest extends AbstractRequest {

    private String fileName;

    /**
     * temporary file path
     */
    private String tfp;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTfp() {
        return tfp;
    }

    public void setTfp(String tfp) {
        this.tfp = tfp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadRequest{");
        sb.append("cmd=").append(HexUtils.toHexString(cmd)).append(',');
        sb.append("requestId=").append(requestId).append(',');
        sb.append("fileName=").append(fileName).append(',');
        sb.append("tfp=").append(tfp);
        sb.append('}');
        return sb.toString();
    }


}
