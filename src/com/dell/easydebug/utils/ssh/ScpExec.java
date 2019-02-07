package com.dell.easydebug.utils.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class ScpExec extends SshSession {

    private String outputStream;

    private final static String CHANNEL_TYPE = "sftp";

    public ScpExec(String host, String password) {
        super(host, password);
    }

    public void execCommand(String srcPath, String destPath) {
        try {
            ChannelSftp sftpChannel = (ChannelSftp) getSession().openChannel(CHANNEL_TYPE);
            sftpChannel.connect();

            sftpChannel.put(srcPath, destPath);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

}
