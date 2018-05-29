#!/usr/bin/env bash
VIRI="file:///Users/viri/IdeaProjects/afternoon_at_races/src/dir_servers/"
PLANCK="file:///home/planck/Desktop/afternoon_at_races/src/dir_servers/"
VIRI_GEN="file:///Users/viri/IdeaProjects/afternoon_at_races/src/common_infrastructures/genclass.jar"
PLANCK_GEN="file:///home/planck/Desktop/afternoon_at_races/src/common_infrastructures/genclass.jar"

java -cp "$PLANCK_GEN":. -Djava.rmi.server.codebase="$PLANCK"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     servers.ServerMain $1
