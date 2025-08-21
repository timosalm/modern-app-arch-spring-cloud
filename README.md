# Demo for a typical Modern App Architecture with Spring Cloud

![](docs/architecture.png)

[Presentation slides](docs/slides.pdf)

## Prerequisites
- A Tanzu Platform 10.2 environment
- [Spring Application Advisor installed](https://techdocs.broadcom.com/us/en/vmware-tanzu/spring/spring-application-advisor/1-4/spring-app-advisor/run-app-advisor-cli.html)

## Demo

Checkout and follow previous steps: [part 1](https://github.com/timosalm/modern-app-arch-spring-cloud/tree/1_tanzu-platform-sb-2-7), [part2](https://github.com/timosalm/modern-app-arch-spring-cloud/tree/2_tanzu-platform-services-resiliency)

### Add Spring Cloud Services
```
cf marketplace
cf create-service p.service-registry standard service-registry
cf create-service p.gateway standard gateway
cf create-service  p.config-server standard configserver

cf bind-service order-service service-registry
cf bind-service order-service service-registry gateway
cf bind-service order-service service-registry configserver

./mvnw clean package
cf push
```

