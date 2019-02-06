package com.dell.easydebug.remotedebug;

import com.dell.easydebug.utils.ssh.SshExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;

import static com.dell.easydebug.utils.ssh.SshCommands.*;

public class ConfigureRpaToDebug {

    Session session;

    public enum Process {
        CONNECTOR,
        TOMCAT
    }

    public void configureRpaProcessToWorkInDebugMode(String rpaIp, String userName, String password, Process process) throws JSchException, IOException {
        // connect to RPA

        // replace looper
        // restart process

    }

    public static boolean configureRpaToDebugMode(String rpaIpAndUserName, String password, Process process) throws JSchException {
        SshExec admin = new SshExec(rpaIpAndUserName, password);
        admin.connect();
        if (Process.CONNECTOR.equals(process)) {
            return admin.execCommand(IS_DEBUG_CONFIGURED_IN_CONNECTORS) == 0;
        } else {
            return admin.execCommand(IS_DEBUG_CONFIGURED_IN_TOMCAT) == 0;
        }
    }

    public static boolean isRpaDebugConfigured(String rpaIpAndUserName, String password, Process process) throws JSchException {
        SshExec admin = new SshExec(rpaIpAndUserName, password);
        admin.connect();
        if (Process.CONNECTOR.equals(process)) {
            return admin.execCommand(IS_DEBUG_CONFIGURED_IN_CONNECTORS) == 0;
        } else {
            return admin.execCommand(IS_DEBUG_CONFIGURED_IN_TOMCAT) == 0;
        }
    }

}
