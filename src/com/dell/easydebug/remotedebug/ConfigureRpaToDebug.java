package com.dell.easydebug.remotedebug;

import com.dell.easydebug.model.RemoteDebugService;
import com.dell.easydebug.model.RpaDetails;
import com.dell.easydebug.utils.ssh.SshExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import static com.dell.easydebug.utils.ssh.SshCommands.*;

public class ConfigureRpaToDebug implements RemoteDebugService {

    Session session;

    @Override
    public void debug(RpaDetails rpaDetails, String process) {
        try {
            configureRpaProcessToWorkInDebugMode(rpaDetails.getRpaUser()+"@"+rpaDetails.getRpaIp(),
                    rpaDetails.getRpaPass(), Process.valueOf(process));
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public enum Process {
        CONNECTOR,
        TOMCAT
    }

    public static void configureRpaProcessToWorkInDebugMode(String rpaIpAndUserName, String password, Process process) throws JSchException {
        if (!isRpaDebugConfigured(rpaIpAndUserName, password, process)) {
            configureRpaToDebugMode(rpaIpAndUserName, password, process);
        }
    }

    public static void configureRpaToDebugMode(String rpaIpAndUserName, String password, Process process) throws JSchException {
        runCommandPerProcess(rpaIpAndUserName, password, process, INSERT_CONNECTOR_DEBUG_CONFIGURATION, INSERT_TOMCAT_DEBUG_CONFIGURATION);
        SshExec admin = new SshExec(rpaIpAndUserName, password);
        admin.connect();
        admin.execCommand(FIREWALL_STOP);
        if (Process.CONNECTOR.equals(process)) {
            admin.execCommand(RESTART_CONNECTOR);
        } else {
            admin.execCommand(KILL_TOMCAT);
        }
        admin.close();
    }

    public static boolean isRpaDebugConfigured(String rpaIpAndUserName, String password, Process process) throws JSchException {
        return runCommandPerProcess(rpaIpAndUserName, password, process, IS_DEBUG_CONFIGURED_IN_CONNECTORS, IS_DEBUG_CONFIGURED_IN_TOMCAT);
    }

    private static boolean runCommandPerProcess(String rpaIpAndUserName, String password, Process process, String insertConnectorDebugConfiguration, String insertTomcatDebugConfiguration) throws JSchException {
        SshExec admin = new SshExec(rpaIpAndUserName, password);
        admin.connect();
        if (Process.CONNECTOR.equals(process)) {
            boolean b = admin.execCommand(insertConnectorDebugConfiguration).getKey() == 0;
            admin.close();
            return b;
        } else {
            boolean b = admin.execCommand(insertTomcatDebugConfiguration).getKey() == 0;
            admin.close();
            return b;
        }
    }

}
