#!/bin/bash

usage()
{
    echo "how to use : deployMain [[--all|--server|--client] | [-h]]"
}

all(){
 server
 client
 exit 1
}

client(){
 for i in {09,10}; do
    scp deployEntitySim.sh sd0203@l040101-ws"$i".ua.pt:/home/sd0203/
    ssh sd0203@l040101-ws"$i".ua.pt 'chmod +x  deployEntitySim.sh && ./deployEntitySim.sh && exit'
 done
 exit 1
}

server(){
 for i in {01,02,03,04,05,06}; do
    scp deploySRServer.sh sd0203@l040101-ws"$i".ua.pt:/home/sd0203/
    ssh sd0203@l040101-ws"$i".ua.pt 'chmod +x deploySRServer.sh && ./deploySRServer.sh && exit'
 done
 exit 1
}

while [ "$1" != "" ]; do
    case $1 in
        -a| --all )             all
                                exit
                                ;;
        -s|--server )           server
                                exit
                                ;;
        -c| --client )          client
                                exit
                                ;;
        -h | --help )           usage
                                exit
                                ;;
        * )                     usage
                                exit 1
    esac
    shift
done
