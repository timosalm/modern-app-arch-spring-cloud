#!/bin/bash
set -e

. demo-magic.sh
export TYPE_SPEED=50
export DEMO_PROMPT="${GREEN}➜ ${CYAN}\W ${COLOR_RESET}"
PROMPT_TIMEOUT=0

clear

pei "cf marketplace"
wait
clear
pei "cf create-service p.service-registry standard service-registry --wait"
pei "cf create-service p.gateway standard gateway --wait"
pei "cf create-service -c '{ \"git\": { \"uri\": \"https://github.com/timosalm/modern-app-arch-spring-cloud\", \"label\": \"tanzu-platform\", \"searchPaths\": \"externalized-configuration\" }}' p.config-server standard configserver --wait"
wait
clear
pei "cf bind-service order-service service-registry"
pei "cf bind-service order-service gateway -c '{\"routes\": [{\"path\": \"/order-service/**\"}]}'"
pei "cf bind-service order-service configserver"
wait
clear
pei "./mvnw clean package"
pei "cf push"
wait
clear
pei "(cd product-service && cf push --no-push)"
wait
clear
pei "cf bind-service product-service service-registry"
pei "cf bind-service product-service gateway -c '{\"routes\": [{\"path\": \"/product-service/**\"}]}'"
pei "cf bind-service product-service configserver"
pei "(cd product-service && ./mvnw clean package && cf push)"
wait
clear
export GATEWAY_URL=$(cf service gateway | grep "dashboard url:" | sed -E 's/.*https:\/\/([^/]+).*/\1/')
clear
pe "curl -XPOST https://$GATEWAY_URL/order-service/api/v1/orders --data '{\"productId\":1,\"shippingAddress\":\"Stuttgart\"}' --header 'Content-Type: application/json'"
wait
pe "curl https://$GATEWAY_URL/order-service/api/v1/orders -v"
wait
clear
p "Scaling product service"
wait
clear
pe "cf scale product-service -i 2"
wait
clear
pe "advisor build-config get"
wait
clear
pe "advisor upgrade-plan get"
wait
clear
pe "advisor upgrade-plan apply --squash 9"