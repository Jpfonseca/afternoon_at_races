file1="deployEntitySim.sh"
file="deploySRServer.sh"

usage()
{
    echo "how to use : deployMain [[--all|--server|--client] | [-h]]"
}

all(){
 server
 client
 exit 1
}

server(){
 scp "$file" sd0203@l040101-ws01.ua.pt:/home/sd0203/
 ssh sdsd0203@l040101-ws01.ua.pt 'chmod +x deploySRServer.sh && ./"$file"'
 exit 1
}

client(){
 for i in {01,02,03,04,05,06}; do
    scp "$file1" sd0203@l040101-ws"$i".ua.pt:/home/sd0203/
    ssh sd0203@l040101-ws"$i".ua.pt 'nohup chmod +x deployEntitySim.sh && ./deployEntitySim.sh &'
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
