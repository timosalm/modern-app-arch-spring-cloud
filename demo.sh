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
pei "cf create-service p.redis vk-plan cache --wait"
pei "cf bind-service order-service cache"
wait
clear
pei "./mvnw clean package"
wait
clear
pei "cf push"

export ORDER_SERVICE=$(cf app order-service | grep routes: | awk '{print $2}')
clear
pe "curl -XPOST https://$ORDER_SERVICE/api/v1/orders --data '{\"productId\":1,\"shippingAddress\":\"Stuttgart\"}' --header 'Content-Type: application/json'"
wait
clear
pei "cf logs order-service --recent"
wait
clear
pe "curl https://$ORDER_SERVICE/api/v1/orders -v"