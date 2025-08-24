# Demo for a typical Modern App Architecture with Spring Cloud

![](docs/architecture.png)

[Presentation slides](docs/slides.pdf)

## Prerequisites
- A Tanzu Platform 10.2 environment
- [Spring Application Advisor installed](https://techdocs.broadcom.com/us/en/vmware-tanzu/spring/spring-application-advisor/1-4/spring-app-advisor/run-app-advisor-cli.html)
- For demo script: [Demo Magic](https://github.com/paxtonhare/demo-magic)
## Demo

Checkout and follow previous steps [here](https://github.com/timosalm/modern-app-arch-spring-cloud/tree/1_tanzu-platform-sb-2-7)

Instead of running the commands manually, you can run them via a demo script.
```
./demo.sh
```

### Add caching and circuit breaking
```
cf marketplace
cf create-service p.redis vk-plan cache
cf bind-service order-service cache
./mvnw clean package
cf push
```

