docker build -t flavio/banco .

cd rest

mvn clean package

docker build -t flavio/app .

docker run -d -p 5433:5432 --name bancopdm flavio/banco
docker run -d -p 8081:8080 --link bancopdm:host-banco flavio/app

