java -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0203/"\
         -Djava.rmi.server.useCodebaseOnly=true\
         -Djava.security.policy=java.policy\
         servers.ServerMain $1
