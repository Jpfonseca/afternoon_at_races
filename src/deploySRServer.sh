#!/bin/bash
DIRECTORY=/home/sd0203/Private/afternoon_at_races/

deploy(){
 cd Private/

 if [ -d "$DIRECTORY" ]; then
   rm -rf afternoon_at_races
   echo "Removed Last Version"
 fi

 if [ ! -d "$DIRECTORY" ]; then
   echo "running ssh-agent"
   eval `ssh-agent -s`
   ssh-agent $(ssh-add -k /home/sd0203/.ssh/id_rsa; git clone git@github.com:/Jpfonseca/afternoon_at_races.git)
 fi

 if [ ! -d "$DIRECTORY" ]; then
   echo "Couldn't Copy Last Version"
   exit 1
 fi

 cd afternoon_at_races/src/
 git checkout dev_deploy
 echo "Copy all Files Successfully"

 javac servers/ServerMain.java
 echo "Done"
}

deploy
