package com.dell.easydebug.reset.version;

import static com.dell.easydebug.utils.ssh.SshCommands.*;

import com.dell.easydebug.model.*;
import com.dell.easydebug.utils.ssh.SshExec;
import com.intellij.openapi.project.ProjectManager;
import com.jcraft.jsch.JSchException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResetVersion implements GetVersionsService {

  @Override
  public String getRpaVersion(RpaDetails rpaDetails) throws JSchException {
    SshExec sshExec = new SshExec(rpaDetails.getRpaUser() + "@" + rpaDetails.getRpaIp(), rpaDetails.getRpaPass());

    sshExec.connect();
    String rpaVersion = sshExec.execCommand(VERSION, 10).getValue();
    String[] lines = rpaVersion.split("\n");
    List<String> t_version_full_list =  Arrays.asList(lines).stream().filter(s->s.contains("t_version_full")).collect(Collectors.toList());
    if (t_version_full_list.isEmpty()) {
      sshExec.close();
      return "";
    }

    String fullVersion = t_version_full_list.get(0);
    String version = fullVersion.substring(fullVersion.indexOf("=") + 3, fullVersion.length()-1);

    sshExec.close();
    return version;
  }

  @Override
  public String getBranchVersion(RpcsDetails rpcsDetails) {
    return null;
  }

  @Override
  public void resetVersion(RpcsDetails rpcsDetails, String version) {
    SshExec sshExec = new SshExec(rpcsDetails.getRpcsUser() + "@rpcs02", rpcsDetails.getRpcsPass());
    String path = ProjectManager.getInstance().getOpenProjects()[0].getBasePath();
    path = path.substring(path.indexOf(":") + 2);

    System.out.println(path);

    try {
      sshExec.connect();
      sshExec.execCommand(String.format(RESET_VERSION, path, version), 60);
      sshExec.close();
    } catch (JSchException e) {
      e.printStackTrace();
    }
  }
}
