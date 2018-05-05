#!/bin/bash
#change your terminal here
Terminal=xfce4-terminal
usage()
{
    echo "how to use : deployMain [[--all|--server|--client|--run] | [-h]]"
}

all(){
 server
 client
}

client(){
 for i in {09,10}; do
    scp deployEntitySim.sh sd0203@l040101-ws"$i".ua.pt:/home/sd0203/
    ssh sd0203@l040101-ws"$i".ua.pt 'chmod +x  deployEntitySim.sh && ./deployEntitySim.sh && exit'
 done
}

server(){
 for i in {01,02,03,04,05,06}; do
    scp deploySRServer.sh sd0203@l040101-ws"$i".ua.pt:/home/sd0203/
    ssh sd0203@l040101-ws"$i".ua.pt 'chmod +x deploySRServer.sh && ./deploySRServer.sh && exit'
 done
}

run(){
 Terminal --tab -H -e "ssh sd0203@l040101-ws06.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java servers.ServerMain 5 && exit' && scp sd0203@l040101-ws06.ua.pt:/home/sd0203/Private/afternoon_at_races/src/Afternoon_at_races.log . && cat Afternoon_at_races.log"
 sleep 10
 Terminal --tab -H -e "ssh sd0203@l040101-ws05.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java servers.ServerMain 4 && exit'"
 sleep 10
 Terminal --tab -H -e "ssh sd0203@l040101-ws04.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java servers.ServerMain 3 && exit'"
 sleep 10
 Terminal --tab -H -e "ssh sd0203@l040101-ws03.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java servers.ServerMain 2 && exit'"
 sleep 10
 Terminal --tab -H -e "ssh sd0203@l040101-ws02.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java servers.ServerMain 1 && exit'"
 sleep 10
 Terminal --tab -H -e "ssh sd0203@l040101-ws01.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java servers.ServerMain 0 && exit'"
 sleep 10
 Terminal --tab -H -e "ssh sd0203@l040101-ws09.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java simulator.Simulator 0 && exit'"
 sleep 10
 Terminal --tab -H -e "ssh sd0203@l040101-ws10.ua.pt 'cd Private/afternoon_at_races/src/ && nohup java simulator.Simulator 1 && exit'"
}

while [ "$1" != "" ]; do
    case $1 in
        -a| --all )             all
                                run
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
        -r | --run )            run
                                exit
                                ;;
        * )                     usage
                                exit 1
    esac
    shift
done
