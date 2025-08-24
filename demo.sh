#!/bin/bash
set -e

. demo-magic.sh
export TYPE_SPEED=50
export DEMO_PROMPT="${GREEN}➜ ${CYAN}\W ${COLOR_RESET}"
PROMPT_TIMEOUT=0



clear
pei "./mvnw clean package"
pei "cf push"
wait
clear
export GATEWAY_URL=$(cf service gateway | grep "dashboard url:" | sed -E 's/.*https:\/\/([^/]+).*/\1/')
pei "curl https://$GATEWAY_URL/order-service/actuator/metrics/application.started.time"
wait
clear
pei "curl https://$GATEWAY_URL/order-service/actuator/metrics/jvm.memory.used"
