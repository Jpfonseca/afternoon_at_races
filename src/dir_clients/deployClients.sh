#!/usr/bin/env bash
#PLANCK_GEN="file:///home/planck/Desktop/afternoon_at_races/src/common_infrastructures/genclass.jar"
#rm ./shared_regions/*.java
#rm */*Register*.class
#rm */*.class
#echo "Just Cleared Old Files"

cp ../dir_servers/entities/*State*.class ./entities/
#cp ../dir_servers/shared_regions/*.class ./shared_regions/
cp ../dir_servers/shared_regions/RMIReply/*.class ./shared_regions/RMIReply/
cp ../dir_registry/extras/*.class ./extras/
cp ../dir_servers/interfaces/*.class ./interfaces/
javac */*.java

#./registry_com_alt.sh
