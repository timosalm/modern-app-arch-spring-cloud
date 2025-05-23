```
./mvnw spring-boot:build-image
docker compose up
```

```
ACCESS_TOKEN=
GATEWAY_URL="http://0.0.0.0:$(docker compose port gateway 8080 | awk -F ':' '{print $2}')"
curl $GATEWAY_URL/services/product-service/api/v1/products --header "Authorization: Bearer $ACCESS_TOKEN"
curl $GATEWAY_URL/services/order-service/api/v1/orders --header "Authorization: Bearer $ACCESS_TOKEN"
curl -XPOST "$GATEWAY_URL/services/order-service/api/v1/orders" --header "Content-Type: application/json" --header "Authorization: Bearer $ACCESS_TOKEN" --data '{"productId":1,"shippingAddress":"Test address"}'
     
```