package com.dell.easydebug;

import com.dell.easydebug.remotedebug.ConfigureRpaToDebug;
import com.dell.easydebug.utils.ssh.ScpExec;
import com.dell.easydebug.utils.ssh.SshExec;
import com.jcraft.jsch.*;
import org.junit.Test;

import java.io.IOException;

import static com.dell.easydebug.remotedebug.ConfigureRpaToDebug.configureRpaProcessToWorkInDebugMode;
import static com.dell.easydebug.remotedebug.ConfigureRpaToDebug.isRpaDebugConfigured;
import static com.dell.easydebug.utils.ssh.SshCommands.INSERT_CONNECTOR_DEBUG_CONFIGURATION;

public class debugTest {
    @Test
    public void testDebug() throws IOException, JSchException, SftpException {

        // to kmake from the relevant path
        // find the changed file
        // copy changed jars to .old
        // copy new jars to RPA
        // restart process


        ScpExec admin = new ScpExec("root@10.76.57.57", "admin");
        admin.connect();
        admin.execCommand("O:\\Development\\Coyote\\Itai\\S3ConnectionParams.json", "/root/");
        admin.close();
    }
}
