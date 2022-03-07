# My Wallet
### Description
A digital wallets that you can deposit, without and transfer money between users.


### Requirements
linux, docker, java11 
 
### Setup
On the folder ~/wallet/
Start the mysql database by docker componse 
``
cd src/dockers/mysql/
bash run.sh
cd -
``

Now, create the tables using the liquibase framework

Start the datastore database by the command
``
docker run -it --name wallet_datastore --rm -p 8081:8081  google/cloud-sdk gcloud beta emulators datastore start --no-store-on-disk --project=giovanic --host-port 0.0.0.0:8081
``

Build and Start the wallets, user and transaction microservices by the command
``
bash build.sh
``

### Access the services
Check if the services are running using the command 
``docker ps``
should contains 5 services: user, wallet, transactiom, datastore and mysql.

Find the ip address from user service, replacing the variable CONTAINER_ID in command below by user container id from 
``docker ps
``

``
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' CONTAINER_ID
``
will return ip address.

Now you can access the rest api using the swagger: 
user service example:
http://172.17.0.4:8080/swagger-ui.html#/
wallet service example:
http://172.17.0.5:8080/swagger-ui.html#/
transaction service example:
http://172.17.0.6:8080/swagger-ui.html#/


## User service has the RESP API
GET
/api/v1/users/{key}
Get User by Key

GET
/api/v1/users/me
Get Logged User

GET
/api/v1/users/refresh
RefreshToken

POST
/api/v1/users/signin
Signin

POST
/api/v1/users/signup
Signup

## Wallet service has the RESP API
POST
/api/v1/wallets/deposit/{money}
Deposit money to logged user

GET
/api/v1/wallets/my-wallet
Get informations from Logged User

POST
/api/v1/wallets/transfer/{toKey}/money/{money}
transfer money from logged user to other user by Key

POST
/api/v1/wallets/withdraw/{money}
Withdraw money from logged user

## Transaction service has the RESP API
GET
/api/v1/transactions/my-pretty-transactions
Get pretty transactions from Logged User

GET
/api/v1/transactions/my-transactions
Get transactions from Logged User

## Steps to test

Visit the user service and create two users like
/api/v1/users/signup -> Signup
```
{
  "firstName": "Tester",
  "lastName": "PicPay",
  "key": "giovanic",
  "password": "test123"
}
```
```
{
  "firstName": "PicPay",
  "lastName": "Company",
  "key": "picpay",
  "password": "test123"
}
```

Create the users and signin for autentication
/api/v1/users/signin -> Signin
Get the JWT token 
```
{
  "key": "giovanic",
  "password": "test123"
}
```

The get endpoint will return a JWT Token
example:
```

```
 

## Integration Document
https://docs.google.com/document/d/1Qb6aun5pNtMIhE_g-UsYEIgmnf2eCwNMV1STSG5JyL0/edit

