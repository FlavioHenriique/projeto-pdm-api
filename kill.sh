docker rmi -f $(docker image ls flavio/* -q)
docker kill $(docker container ls -a -q)

sudo docker rm -f $(sudo docker ps -aq --filter name=bancopdm)

