#!/usr/bin/env bash
VIRI="file:///Users/viri/IdeaProjects/afternoon_at_races/src/dir_clients/"
PLANCK="file:///home/planck/Desktop/afternoon_at_races/src/dir_clients/"
PLANCK_GEN="file:///home/planck/Desktop/afternoon_at_races/src/common_infrastructures/genclass.jar"

java -cp "$PLANCK_GEN":. -Djava.rmi.server.codebase="$PLANCK"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     simulator.Simulator $1
