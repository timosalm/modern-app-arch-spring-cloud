# Demo for a typical Modern App Architecture with Spring Cloud

![](docs/architecture.png)

[Presentation slides](docs/slides.pdf)

## Prerequisites
- A Tanzu Platform 10.2 environment
- [Spring Application Advisor installed](https://techdocs.broadcom.com/us/en/vmware-tanzu/spring/spring-application-advisor/1-4/spring-app-advisor/run-app-advisor-cli.html)
- For demo script: [Demo Magic](https://github.com/paxtonhare/demo-magic)
## Demo

Checkout and follow previous steps: [part 1](https://github.com/timosalm/modern-app-arch-spring-cloud/tree/1_tanzu-platform-sb-2-7), [part2](https://github.com/timosalm/modern-app-arch-spring-cloud/tree/2_tanzu-platform-services-resiliency)

Instead of running the commands manually, you can run them via a demo script.
```
./demo.sh
```

### Add Spring Cloud Services
```
cf marketplace
cf create-service p.service-registry standard service-registry
cf create-service p.gateway standard gateway
cf create-service -c '{ "git": { "uri": "https://github.com/timosalm/modern-app-arch-spring-cloud", "label": "3_tanzu-platform-sc-services", "searchPaths": "externalized-configuration" }}' p.config-server standard configserver

cf bind-service order-service service-registry
cf bind-service order-service gateway -c '{"routes": [{"path": "/order-service/**"}]}'
cf bind-service order-service configserver

./mvnw clean package
cf push

(cd product-service && ./mvnw clean package && cf push --no-start)
cf bind-service product-service service-registry
cf bind-service product-service gateway -c '{"routes": [{"path": "/product-service/**"}]}'
cf bind-service product-service configserver
cf restage product-service
```

### Scaling
```
cf scale product-service -i 2

export GATEWAY_URL=$(cf service gateway | grep "dashboard url:" | sed -E 's/.*https:\/\/([^/]+).*/\1/')
curl https://$GATEWAY_URL/order-service/actuator/metrics/application.started.time
curl https://$GATEWAY_URL/order-service/actuator/metrics/jvm.memory.used

advisor build-config get
advisor upgrade-plan get
advisor upgrade-plan apply --squash 9

./mvnw clean package && cf push
curl https://$GATEWAY_URL/order-service/actuator/metrics/application.started.time
curl https://$GATEWAY_URL/order-service/actuator/metrics/jvm.memory.used
```




