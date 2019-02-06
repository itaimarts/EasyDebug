package com.dell.easydebug;

import com.dell.easydebug.remotedebug.ConfigureRpaToDebug;
import com.dell.easydebug.utils.ssh.SshExec;
import com.jcraft.jsch.JSchException;
import org.junit.Test;

import java.io.IOException;

import static com.dell.easydebug.remotedebug.ConfigureRpaToDebug.isRpaDebugConfigured;
import static com.dell.easydebug.utils.ssh.SshCommands.INSERT_CONNECTOR_DEBUG_CONFIGURATION;

public class debugTest {
    @Test
    public void testDebug() throws IOException, JSchException {
        isRpaDebugConfigured("root@10.76.57.57", "admin", ConfigureRpaToDebug.Process.CONNECTOR);

        SshExec admin = new SshExec("root@10.76.57.57", "admin");
        admin.connect();

        int i = admin.execCommand(INSERT_CONNECTOR_DEBUG_CONFIGURATION);
        System.out.println(i);

        isRpaDebugConfigured("root@10.76.57.57", "admin", ConfigureRpaToDebug.Process.CONNECTOR);
    }
}
