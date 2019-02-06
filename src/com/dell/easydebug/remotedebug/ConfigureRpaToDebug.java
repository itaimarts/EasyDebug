package com.dell.easydebug.remotedebug;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

public class ConfigureRpaToDebug {

    Session session;

    public enum Process {
        CONNECTOR,
        TOMCAT
    }

    public void configureRpaProcessToWorkInDebugMode(String rpaIp, String userName, String password, Process process) throws JSchException, IOException {
        // connect to RPA
        JSch jsch = new JSch();
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session = jsch.getSession(userName, rpaIp, 22);
        session.setConfig(config);
        session.setPassword(password);
        session.connect();
        System.out.println(sendCommand("version"));

        // replace looper
        // restart process

    }

    public String sendCommand(String command)
    {
        StringBuilder outputBuffer = new StringBuilder();

        try
        {
            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();

            while(readByte != 0xffffffff)
            {
                outputBuffer.append((char)readByte);
                readByte = commandOutput.read();
            }

            channel.disconnect();
        }
        catch(IOException | JSchException ioX)
        {
            System.out.println(ioX.getMessage());
            return null;
        }

        return outputBuffer.toString();
    }

}
