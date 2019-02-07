package com.dell.easydebug.replacejar;

import com.dell.easydebug.utils.ssh.SshExec;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.jcraft.jsch.JSchException;

import static com.dell.easydebug.ui.MyToolWindow.rpcsDetails;
import static com.dell.easydebug.utils.ssh.SshCommands.FIREWALL_STOP;
import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class ReplaceJar extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = VIRTUAL_FILE.getData(e.getDataContext());
        if (file.isDirectory()) {
            if (file.findChild("KMakefile") != null) {
                String rpcsHost = rpcsDetails.getRpcsUser() + "@rpcs0" + rpcsDetails.getRpcsNum();
                SshExec admin = new SshExec(rpcsHost, rpcsDetails.getRpcsPass());
                try {
                    admin.connect();
                    String path = file.getPath().substring(file.getPath().indexOf(":") + 2);
                    admin.execCommand("cd " + path + "\nkmake");
                    admin.close();
                } catch (JSchException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
}
