# Demo for a typical Modern App Architecture with Spring Cloud

![](docs/architecture.png)

[Presentation slides](docs/slides.pdf)

## Prerequisites
- A Tanzu Platform 10.2 environment
- [Spring Application Advisor installed](https://techdocs.broadcom.com/us/en/vmware-tanzu/spring/spring-application-advisor/1-4/spring-app-advisor/run-app-advisor-cli.html)

## Demo

Checkout and follow previous steps [here](https://github.com/timosalm/modern-app-arch-spring-cloud/tree/tanzu-platform-sb-2-7)

### Add caching
```
cf marketplace
cf create-service p-cloudcache extra-small cache
cf bind-service order-service cache
cf push
```
