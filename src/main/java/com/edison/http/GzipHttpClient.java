/*
package com.edison.http;

public class GzipHttpClient {

    public static void main(String[] args) {

    }

    public static void sendHttp(String url, String message) {
        if (StringUtils.isBlank(message)) {
            LOGGER.info("a blank message, return.");
            return;
        }
        PostMethod postMethod = new PostMethod(url);
        postMethod.setContentChunked(true);
        postMethod.addRequestHeader("Accept", "text/plain");
        postMethod.setRequestHeader("Content-Encoding", "gzip");
        postMethod.setRequestHeader("Transfer-Encoding", "chunked");

        try {
            ByteArrayOutputStream originalContent = new ByteArrayOutputStream();
            originalContent
                    .write(message.getBytes(Charset.forName("UTF-8")));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
            originalContent.writeTo(gzipOut);
            gzipOut.finish();

            postMethod.setRequestEntity(new ByteArrayRequestEntity(baos
                    .toByteArray(), "text/plain; charset=utf-8"));
        } catch (IOException e) {
            LOGGER.error("write message fail.", e);
            return;
        }

        int retry = 0;
        do {
            try {
                int status = httpClient.executeMethod(postMethod);
                if (HttpStatus.SC_OK == status) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("send http success, url=" + url
                                + ", content=" + message);
                    }
                    return;
                } else {
                    String rsp = postMethod.getResponseBodyAsString();
                    LOGGER.error("send http fail, status is: " + status
                            + ", response is: " + rsp);
                }
            } catch (HttpException e) {
                LOGGER.info("http exception when send http.", e);
            } catch (IOException e) {
                LOGGER.info("io exception when send http.", e);
            } finally {
                postMethod.releaseConnection();
            }
            LOGGER.info("this is "+ retry + " time, try next");
        } while (retry++ < 3);

    }
*/
