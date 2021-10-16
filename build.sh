#!/bin/zsh

./mvnw clean package -DskipTests
./mvnw install package -DskipTests

cp target/university-0.0.1-SNAPSHOT.jar src/main/docker
cd src/main/docker

docker-compose down
docker rmi university:latest
docker-compose up
