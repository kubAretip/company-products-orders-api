## RESTful API Company product orders.

#### Technologies
* Spring
* Docker
* MySql
* FlyWay

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