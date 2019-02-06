package com.dell.easydebug.model;

import com.jcraft.jsch.JSchException;

public interface GetVersionsService {

	String getRpaVersion(RpaDetails rpaDetails) throws JSchException;

	String getBranchVersion(RpcsDetails rpcsDetails);

	void resetVersion(RpcsDetails rpcsDetails, String version);
}
