package com.dell.easydebug.utils.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;

public class SshSession implements AutoCloseable {

    private static final int BUFFER_SIZE = 1024;
    private final Session session;

    private final static String KEY_CHECKING = "StrictHostKeyChecking";
    private final static int THREAD_SLEEP_IN_TIME_MILLIS = 1000;


    SshSession(String host, String password) {
        JSch jsch = new JSch();
        try {
            session = initSession(host, password, jsch);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }

    public Session getSession() {
        return session;
    }

    private Session initSession(String host, String password, JSch jsch) throws JSchException {
        final int atIndex = host.indexOf('@');
        final String user = host.substring(0, atIndex);
        final String server = host.substring(atIndex + 1);
        Session session = jsch.getSession(user, server, 22);
        session.setConfig(KEY_CHECKING, "no");
        session.setPassword(password);
        return session;
    }

    public void connect() throws JSchException {
        session.connect();
    }

    public void connect(int connectTimeout) throws JSchException {
        session.connect(connectTimeout);
    }

    int getOutput(ChannelExec channelExec, InputStream in) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int exitStatus = -1;
        int count = 10;
        while (count > 0) {
            while (in.available() > 0) {
                int i = in.read(buffer);
                if (i < 0) break;
//                log.debug(new String(buffer, 0, i));
            }
            if (channelExec.isClosed()) {
                if (in.available() > 0) continue;
                exitStatus = channelExec.getExitStatus();
//                log.debug("exit-status: " + exitStatus);
                break;
            }
            try {
                Thread.sleep(THREAD_SLEEP_IN_TIME_MILLIS);
                count--;
            } catch (InterruptedException ignored) {
            }
        }
        return exitStatus;
    }

    @Override
    public void close() throws RuntimeException {
        session.disconnect();
    }
}