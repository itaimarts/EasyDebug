package com.dell.easydebug.model;

public interface GetVersionsService {

	String getRpaVersion(RpaDetails rpaDetails);

	String getBranchVersion(RpcsDetails rpcsDetails);

	void resetVersion(RpcsDetails rpcsDetails, String version);
}
