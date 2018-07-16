docker kill $(docker container ls -a -q)
docker rmi -f $(docker image ls flavio/* -q)

sudo docker rm -f $(sudo docker ps -aq --filter name=bancopdm)
sudo docker rm -f $(sudo docker ps -aq --filter name=app)

