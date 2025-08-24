#!/bin/bash
set -e

. demo-magic.sh
export TYPE_SPEED=50
export DEMO_PROMPT="${GREEN}➜ ${CYAN}\W ${COLOR_RESET}"
PROMPT_TIMEOUT=0

clear

p "Register Spring App Advisor with Tanzu Hub and enter environment variables"
cmd
clear
pei "advisor build-config get && advisor build-config publish"
wait
clear
pei "advisor build-config get && advisor build-config publish"
wait
clear
pei "./mvnw clean package"
wait
pei "advisor advice list"
wait
pei "advisor advice apply --name=tanzu"
wait
pei "cf push -f .tanzu/order-service/manifest.yml"
wait
export ORDER_SERVICE=$(cf app order-service | grep routes: | awk '{print $2}')
clear
pei "curl https://$ORDER_SERVICE/actuator/health"
wait
clear
pei "cf logs order-service --recent"
wait
clear
pei "cf marketplace"
wait
clear
pei "cf create-service postgres small db --wait"
wait
clear
pei "cf create-service p.rabbitmq rmq-single-node rabbit --wait"
wait
clear
pei "cf bind-service order-service db"
pei "cf bind-service order-service rabbit"
wait
pei "cf restage order-service"
wait
clear
pei "cf services"
wait
export ORDER_SERVICE=$(cf app order-service | grep routes: | awk '{print $2}')
clear
pe "curl https://$ORDER_SERVICE/api/v1/orders -v"




