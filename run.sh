docker build -t pdm/banco .

cd rest

mvn clean package

docker build -t pdm/api .

docker run -d -p 5433:5432 --name bancopdm pdm/banco
docker run -p 8081:8080 --link bancopdm:host-banco pdm/api

