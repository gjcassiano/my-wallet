#./gradlew clean
#./gradlew build
#
#docker image rm -f transaction_service
#docker rm -f transaction_service
#docker image rm -f user_service
#docker rm -f user_service
#docker image rm -f wallet_service
#docker rm -f wallet_service
#
#
#cd src/dockers/user_service/
#bash build.sh
#cd -
#
#cd src/dockers/wallet_service/
#bash build.sh
#cd -
#
#cd src/dockers/transaction_service/
#bash build.sh
#cd -

# start the datastore
# docker run -d -it --name wallet_datastore --rm -p 8081:8081  google/cloud-sdk gcloud beta emulators datastore start --no-store-on-disk --project=giovanic --host-port 0.0.0.0:8081

DATASTORE_DOCKER_ID=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' wallet_datastore)

#docker run -d --rm --name transaction_service --env DATASTORE_HOST="http://${DATASTORE_DOCKER_ID}:8081" transaction_service
#docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' transaction_service
#
#docker run -d --rm --name user_service --env DATASTORE_HOST="http://${DATASTORE_DOCKER_ID}:8081" user_service
#docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' user_service

docker run --rm --name wallet_service --env DATASTORE_HOST="http://${DATASTORE_DOCKER_ID}:8081" wallet_service
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' wallet_service


