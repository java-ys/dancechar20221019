package com.litian.dancechar.framework.common.context;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * body reader请求
 *
 * @author tojson
 * @date 2021/6/14 22:38
 */
public class BodyReaderRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public BodyReaderRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder sb = new StringBuilder();
        InputStream ins = request.getInputStream();
        BufferedReader isr = null;
        try {
            if (ins != null) {
                isr = new BufferedReader(new InputStreamReader(ins));
                char[] charBuffer = new char[128];
                int readCount = 0;
                while ((readCount = isr.read(charBuffer)) != -1) {
                    sb.append(charBuffer, 0, readCount);
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (isr != null) {
                isr.close();
            }
        }
        sb.toString();
        body = sb.toString();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayIns = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletIns = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayIns.read();
            }
        };
        return servletIns;
    }
}