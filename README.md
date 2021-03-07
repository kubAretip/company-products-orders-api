## RESTful API Company product orders.

#### Technologies
* Spring
* Docker
* MySql
* FlyWay

#### Features:
* JSON Web Token authentication
* Support for i18n (available languages: PL, EN)
* Users registration and activation via mail or with generated pdf with activation details.
* Management of users roles
* Management of clients
* Management of products
* Management of clients orders and statuses
* Allocation of contractors for the client orders

#### Requirements:
* Install Maven, see https://maven.apache.org/download.cgi
* Install Docker, see https://docs.docker.com/get-docker

#### Steps to run
1. Clone repo
``git clone https://github.com/kubAretip/company-products-orders-api.git``
2. Package
``mvn clean install -P prod``
3. Run
``docker-compose up -d``