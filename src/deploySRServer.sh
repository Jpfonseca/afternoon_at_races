#bin/bash
DIRECTORY=/home/sd0203/Private/afternoon_at_races/

cd $HOME/Private

if [ -d "$DIRECTORY" ]; then
  rm -rf afternoon_at_races
fi

if [ ! -d "$DIRECTORY" ]; then
  git clone git@github.com:/Jpfonseca/afternoon_at_races.git
  cd afternoon_at_races/src/
  git checkout dev_deploy
  javac servers/ServerMain.java
fi

