package com.dell.easydebug.utils.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;

import static com.dell.easydebug.ui.MyToolWindow.replaceJarOutput;

public class SshExec extends SshSession {

    private final static String CHANNEL_TYPE = "exec";
    private final static int THREAD_SLEEP_IN_TIME_MILLIS = 1000;

    public SshExec(String host, String password) {
        super(host, password);
    }


    public Pair<Integer, String> execCommand(String command, int timeoutInSeconds, OutputStream outputStream) {
        final Channel channel;
        try {
            channel = getSession().openChannel(CHANNEL_TYPE);
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);

            channel.setInputStream(null);
            channelExec.setErrStream(System.err, true);
            InputStream in = channelExec.getInputStream();

            channel.setOutputStream(replaceJarOutput, true);
            channelExec.connect();

            int exitCode = getOutput(channelExec, in ,timeoutInSeconds);
            channelExec.disconnect();
            String text = outputStream.toString();

            return new Pair(exitCode, text);
        } catch (JSchException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pair<Integer, String> execCommand(String command, int timeoutInSeconds) {
      return execCommand(command, timeoutInSeconds, new ByteArrayOutputStream());
    }

}
