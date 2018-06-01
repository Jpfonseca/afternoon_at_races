#!/usr/bin/env bash
VIRI_GEN="file:///Users/viri/IdeaProjects/afternoon_at_races/src/common_infrastructures/genclass.jar"
PLANCK_GEN="file:///home/planck/Desktop/afternoon_at_races/src/common_infrastructures/genclass.jar"

echo "Removing Old Files"
rm */*.class

echo "Compiling Files"
javac -cp "$PLANCK_GEN":. */*.java

echo "Copying *.class files to dir_registry"

cp ./entities/*.class ../dir_registry/entities/
rm */*1.class
cp ./shared_regions/*.class ../dir_registry/shared_regions/
cp ./shared_regions/RMIReply/*.class ../dir_registry/shared_regions/RMIReply/
#cp ./extras/*.class ../dir_registry/extras/

echo "Running servers_com_alt"

./servers_com_alt.sh $1