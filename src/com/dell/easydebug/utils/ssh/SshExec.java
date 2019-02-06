package com.dell.easydebug.utils.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class SshExec extends SshSession {

    private String outputStream;

    private final static String CHANNEL_TYPE = "exec";
    private final static int THREAD_SLEEP_IN_TIME_MILLIS = 1000;

    public SshExec(String host, String password) {
        super(host, password);
    }

    public void execSshCommands(String[] commands) {
        Arrays.stream(commands).forEach(this::execCommand);
    }

    public int execCommand(String command) {
        final Channel channel;
        try {
            channel = getSession().openChannel(CHANNEL_TYPE);
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);

            channel.setInputStream(null);
            channelExec.setErrStream(System.err, true);
            InputStream in = channelExec.getInputStream();

            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(out)) {

                channel.setOutputStream(ps);
                channelExec.connect();

                int exitCode = getOutput(channelExec, in);
                channelExec.disconnect();
                outputStream = out.toString();
                System.out.println(outputStream);

                return exitCode;
            }
        } catch (JSchException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
