usage()
{
    echo "how to use : deploy [[--all|--server|--client|--run] | [-h]]"
}

serversmac(){
    tab "ssh sd0203@l040101-ws01.ua.pt 'cd ~ && sh set_rmiregistry.sh 22229'"
    sleep 2
    tab "ssh sd0203@l040101-ws01.ua.pt 'cd ~/Private/dir_registry && sh register_com.sh'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws02.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws02.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 5' && scp sd0203@l040101-ws02.ua.pt:~/Private/dir_servers/Afternoon_At_Races.log . && cat Afternoon_at_races.log"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws03.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws03.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 4'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws04.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws04.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 3'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws05.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws05.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 2'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws06.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws06.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 1'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws07.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws07.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 0'"
    sleep 2
}

servers(){
    xfce4-terminal --tab -H -T "RMI" -e "ssh sd0203@l040101-ws01.ua.pt 'cd ~ && sh set_rmiregistry.sh 22229'"
    sleep 2
    xfce4-terminal --tab -H -T "Register" -e "ssh sd0203@l040101-ws01.ua.pt 'cd ~/Private/dir_registry && sh register_com.sh'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws02.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "General Repository" -e "ssh sd0203@l040101-ws02.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 5' && scp sd0203@l040101-ws02.ua.pt:~/Private/dir_servers/Afternoon_At_Races.log . && cat ./Afternoon_at_races.log"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws03.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "Betting Centre" -e "ssh sd0203@l040101-ws03.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 4'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws04.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "Racing Track" -e "ssh sd0203@l040101-ws04.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 3'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws05.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "Paddock" -e "ssh sd0203@l040101-ws05.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 2'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws06.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "Control Centre" -e "ssh sd0203@l040101-ws06.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 1'"
    sleep 2
    scp ${SRC_DIR}dir_servers.zip sd0203@l040101-ws07.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "Stable" -e "ssh sd0203@l040101-ws07.ua.pt 'cd ~/Private/ && unzip dir_servers.zip && cd ~/Private/dir_servers && sh servers_com.sh 0'"
    sleep 2
}

clientsmac(){
    scp ${SRC_DIR}dir_clients.zip sd0203@l040101-ws08.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws08.ua.pt 'cd ~/Private/ && unzip dir_clients.zip && cd ~/Private/dir_clients && sh clients_com.sh 0'"
    sleep 2
    scp ${SRC_DIR}dir_clients.zip sd0203@l040101-ws09.ua.pt:/home/sd0203/Private/
    tab "ssh sd0203@l040101-ws09.ua.pt 'cd ~/Private/ && unzip dir_clients.zip && cd ~/Private/dir_clients && sh clients_com.sh 1'"
    sleep 2
}

clients(){
    scp ${SRC_DIR}dir_clients.zip sd0203@l040101-ws08.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "Broker" -e "ssh sd0203@l040101-ws08.ua.pt 'cd ~/Private/ && unzip dir_clients.zip && cd ~/Private/dir_clients && sh clients_com.sh 0'"
    sleep 2
    scp ${SRC_DIR}dir_clients.zip sd0203@l040101-ws09.ua.pt:/home/sd0203/Private/
    xfce4-terminal --tab -H -T "Spectators" -e "ssh sd0203@l040101-ws09.ua.pt 'cd ~/Private/ && unzip dir_clients.zip && cd ~/Private/dir_clients && sh clients_com.sh 1'"
    sleep 2
}

tab() {
    osascript &>/dev/null <<EOF
      tell application "iTerm"
        activate
        tell current window to set tb to create tab with default profile
        tell current session of current window to write text "$*"
      end tell
EOF
}

remote(){
    ## CLEAN PUBLIC AREA
    cd /home/sd0203/Public/
    rm -r *

    REMOTE_DIR=/home/sd0203/Private/
    cd ${REMOTE_DIR}

    ## UNZIP SRC
    unzip src.zip

    ## COMPILE ALL
    javac */*.java

    ## CLEAN
    rm */*.java */*/*.java
    rm src.zip

    ## REGISTER - PREPARE REGISTER
    mkdir dir_registry
    cp java.policy dir_registry/
    mv register_com.sh dir_registry/
    mv registry/ dir_registry/
    rm -r extras/
    mkdir dir_registry/interfaces
    cp interfaces/Register.class dir_registry/interfaces/
    ## REGISTER - PUBLIC INTERFACE
    cp -r dir_registry/interfaces/ ~/Public/

    ## SERVERS - PREPARE SERVERS
    mkdir dir_servers
    #cd dir_servers/ && unzip src.zip && javac */*.java && cd ..
    cp java.policy dir_servers/
    mv servers_com.sh dir_servers/
    mv servers/ dir_servers/
    cp -r shared_regions/ dir_servers/
    mkdir ~/Public/shared_regions/
    cp shared_regions/Winners.class ~/Public/shared_regions/
    cp -r shared_regions/RMIReply/ ~/Public/shared_regions/
    cp -r interfaces/ dir_servers/
    cp -r dir_servers/interfaces/ ~/Public/
    mkdir dir_servers/entities/
    cp entities/*State*.class dir_servers/entities

    ## CLIENTS - PREPARE CLIENTS
    mkdir dir_clients
    mv clients_com.sh dir_clients/
    mv java.policy dir_clients/
    mv simulator/ dir_clients/
    mkdir dir_clients/entities/
    mkdir ~/Public/entities
    cp entities/*State*.class ~/Public/entities
    mv entities/ dir_clients/
    mv interfaces/ dir_clients/
    cp -r dir_servers/interfaces/GeneralInformationRepositoryInterface.class dir_clients/interfaces/
    mkdir dir_clients/shared_regions/
    cp -r shared_regions/RMIReply/ dir_clients/shared_regions/
    cp shared_regions/Winners.class dir_clients/shared_regions/
    rm -r shared_regions/

    ## COMPILE REGISTER
    ## NO NEED - LOCAL ONLY

    ## COMPILE SERVERS
    zip -r dir_servers.zip dir_servers/

    ## COMPILE CLIENTS
    zip -r dir_clients.zip dir_clients/


    ## COPY TO OTHER MACHINES
    ## PUBKEY NOT WORKING ?!?
    # scp dir_servers.zip sd0203@l040101-ws02.ua.pt:/home/sd0203/Private/

}

prepare(){
    SRC_DIR=/Users/viri/IdeaProjects/afternoon_at_races/src/
    # viri = /Users/viri/IdeaProjects/afternoon_at_races/src/
    # plank = ...
    cd ${SRC_DIR}

    ## ZIP SRC
    if [ -f src.zip ]
    then
        rm src.zip
    fi
    zip -r src.zip entities/ extras/ interfaces/ registry/ servers/ shared_regions/ simulator/ register_com.sh servers_com.sh clients_com.sh java.policy

    ## CLEAN REMOTE
    for i in {01,02,03,04,05,06,07,08,09}; do
        ssh sd0203@l040101-ws"$i".ua.pt "cd ~/Private/ && rm -rf *"
        ssh sd0203@l040101-ws"$i".ua.pt "pkill -f rmi ; pkill -f register"
    done

    ## COPY SRC TO REMOTE
    scp ${SRC_DIR}src.zip sd0203@l040101-ws01.ua.pt:/home/sd0203/Private/

    ## EXECUTE REMOTE
    ssh sd0203@l040101-ws01.ua.pt "$(typeset -f); remote"

    ## COPY REMOTE TO LOCAL
    scp -r sd0203@l040101-ws01.ua.pt:/home/sd0203/Private/dir_servers.zip ${SRC_DIR}
    scp -r sd0203@l040101-ws01.ua.pt:/home/sd0203/Private/dir_clients.zip ${SRC_DIR}
}


while [ "$1" != "" ]; do
    case $1 in
        -h | --help )           usage
                                exit
                                ;;
        -r | --run )            prepare
                                servers
                                clients
                                exit
                                ;;
        -rr | --runmac )        prepare
                                serversmac
                                clientsmac
                                exit
                                ;;
        * )                     usage
                                exit 1
    esac
    shift
done