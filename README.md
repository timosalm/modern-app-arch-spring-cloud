# Demo for a typical Modern App Architecture with Spring Cloud

## Prerequisites
- A Tanzu Platform 10.2 environment
- [Spring Application Advisor installed](https://techdocs.broadcom.com/us/en/vmware-tanzu/spring/spring-application-advisor/1-4/spring-app-advisor/run-app-advisor-cli.html)

## Demo

### Get CVEs
Go to Tanzu Hub, select the Repositories menu.
Click Manage Connections, and Attach Spring App Advisor menu.
Fill out the form and copy/paste the printed environment variables into your terminal.
```
advisor build-config get && advisor build-config publish
```

### Create CF manifest and initial push
```
./mvnw clean package

advisor advice list
advisor advice apply --name=tanzu

cf push -f .tanzu/order-service/manifest.yml
```

### Create backing services
```
cf marketplace
cf create-service postgres small db
cf create-service p.rabbitmq rmq-single-node rabbit

cf bind-service order-service db
cf bind-service order-service rabbit
cf restage order-service
```
