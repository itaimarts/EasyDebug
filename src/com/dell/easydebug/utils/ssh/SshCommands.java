package com.dell.easydebug.utils.ssh;

public class SshCommands {

    public static final String VERSION = "cat /home/kos/kbox/src/initialization/tweak_params/tweak.params.version";
    public static final String KILL_TOMCAT = "kill -9 `ps -ef | grep tomcat | grep -i juli | grep -v grep | perl -ane 'print $F[1].\"\\n\";'`";
    public static final String RESTART_CONNECTOR = "/home/kos/connectors/scripts/restart_connector.sh";
    public static final String FIREWALL_STOP = "/etc/init.d/firewall stop";
    public static final String INSERT_TOMCAT_DEBUG_CONFIGURATION =
            "sed -i 's/home\\/kos\\/RPServers\\/apache-tomcat\\/logs/home\\/kos\\/RPServers\\/apache-tomcat\\/logs -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005/g' /home/kos/RPServers/apache-tomcat/bin/tomcat-start.sh";
    public static final String INSERT_CONNECTOR_DEBUG_CONFIGURATION =
            "sed -i 's/usr\\/kashya\\/java\\/jre\\/bin\\/java/usr\\/kashya\\/java\\/jre\\/bin\\/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005/g' /home/kos/connectors/connectors_loop.tcsh";
    public static final String IS_DEBUG_CONFIGURED_IN_TOMCAT =
            "grep -F \"java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005\" /home/kos/RPServers/apache-tomcat/bin/tomcat-start.sh";
    public static final String IS_DEBUG_CONFIGURED_IN_CONNECTORS =
            "grep -F \"java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005\" /home/kos/connectors/connectors_loop.tcsh";

    public static final String RESET_VERSION =
        "cd %s\n" +
            "\n" +
            "git reset --soft HEAD^\n" +
            "\n" +
            "git stash\n" +
            "\n" +
            "git pull\n" +
            "\n" +
            "git reset --hard v_%s\n" +
            "\n" +
            "git stash pop";

    public static final String GET_TAGGED_COMMIT_VERSION_LIST =
        "cd %s\n" +
            "\n" +
            "git tag --contains HEAD\n";

    public static final String GET_UNTAGGED_COMMIT_VERSION =
        "cd %s\n" +
            "\n" +
            "git describe --tags\n";
}
