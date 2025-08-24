#!/bin/bash
set -e

. demo-magic.sh
export TYPE_SPEED=50
export DEMO_PROMPT="${GREEN}➜ ${CYAN}\W ${COLOR_RESET}"
PROMPT_TIMEOUT=0

clear


pei "(cd order-service && ./mvnw clean package && cf push)"
wait
clear
export GATEWAY_URL=$(cf service gateway | grep "dashboard url:" | sed -E 's/.*https:\/\/([^/]+).*/\1/')
clear
pe "curl https://$GATEWAY_URL/order-service/api/v1/chat?prompt=What%20is%20the%20status%20of%20my%20orders"