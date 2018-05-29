java -Djava.rmi.server.codebase="file:///Users/viri/IdeaProjects/afternoon_at_races/src/dir_servers/"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     servers.ServerMain $1
