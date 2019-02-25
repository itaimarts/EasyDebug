package com.dell.easydebug.replacejar;

import com.dell.easydebug.utils.ssh.ScpExec;
import com.dell.easydebug.utils.ssh.SshExec;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.jcraft.jsch.JSchException;

import static com.dell.easydebug.ui.MyToolWindow.rpaDetails;
import static com.dell.easydebug.ui.MyToolWindow.rpcsDetails;
import static com.dell.easydebug.utils.ssh.SshCommands.FIREWALL_STOP;
import static com.dell.easydebug.utils.ssh.SshCommands.IS_DEBUG_CONFIGURED_IN_CONNECTORS;
import static com.dell.easydebug.utils.ssh.SshCommands.IS_DEBUG_CONFIGURED_IN_TOMCAT;
import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

public class ReplaceJar extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    VirtualFile file = VIRTUAL_FILE.getData(e.getDataContext());
    if (file.isDirectory()) {
      if (file.findChild("KMakefile") != null) {
        String rpcsHost = rpcsDetails.getRpcsUser() + "@rpcs0" + rpcsDetails.getRpcsNum();
        SshExec admin = new SshExec(rpcsHost, rpcsDetails.getRpcsPass());
        File archive = new File("K:\\archive\\RP4VM\\master\\rpa\\classes");
        // TODO - safety
        long lastModified = Stream.of(archive.listFiles()).map(listedFile -> listedFile.lastModified()).max(Long::compare).get();
        try {
          admin.connect();
          String path = file.getPath().substring(file.getPath().indexOf(":") + 2);
          admin.execCommand("cd " + path + "\nkmake", 90);
          admin.close();


          File[] jarsToReplace = archive.listFiles(listedFile -> listedFile.lastModified() > lastModified && listedFile.isFile());

          String rpaHost = rpaDetails.getRpaUser() + "@" + rpaDetails.getRpaIp();
          ScpExec scpExec = new ScpExec(rpaHost, rpaDetails.getRpaPass());
          SshExec sshExec = new SshExec(rpaHost, rpaDetails.getRpaPass());
          sshExec.connect();

          scpExec.connect();
          //TODO - check if jar.old already exist, do not replace!!
          Stream.of(jarsToReplace).forEach(listedFile -> sshExec.execCommand
              ("cp /home/kos/kashya/archive/classes/" + listedFile.getName() + " /home/kos/kashya/archive/classes/" +
                  listedFile.getName() + ".old", 10));
          Stream.of(jarsToReplace).forEach(listedFile -> scpExec.execCommand(listedFile.getPath(),
              "/home/kos/kashya/archive/classes"));
          sshExec.execCommand(IS_DEBUG_CONFIGURED_IN_CONNECTORS, 2);
          sshExec.execCommand(IS_DEBUG_CONFIGURED_IN_TOMCAT, 2);
        } catch (JSchException e1) {
          e1.printStackTrace();
        }
      }
    }
  }
}
