#!/usr/bin/env bash
PLANCK_GEN="file:///home/planck/Desktop/afternoon_at_races/src/common_infrastructures/genclass.jar"
rm ./shared_regions/*.java
rm */*Register*.class
rm */*.class
echo "Just Cleared Old Files"

javac -cp "$PLANCK_GEN":. */*.java
./registry_com_alt.sh