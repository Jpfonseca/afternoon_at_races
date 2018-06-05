#!/usr/bin/env bash
VIRI="file:///Users/viri/IdeaProjects/afternoon_at_races/src/dir_clients/"
PLANCK="file:///home/planck/Desktop/afternoon_at_races/src/dir_clients/"
PLANCK_GEN="file:///home/planck/Desktop/afternoon_at_races/src/common_infrastructures/genclass.jar"

java -Djava.rmi.server.codebase="$VIRI"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     simulator.Simulator $1
