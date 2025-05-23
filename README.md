```
./mvnw spring-boot:build-image
docker compose up
```

```
GATEWAY_URL="http://0.0.0.0:$(docker compose port gateway 8080 | awk -F ':' '{print $2}')"
curl $GATEWAY_URL/services/product-service/api/v1/products
curl $GATEWAY_URL/services/order-service/api/v1/orders
curl -XPOST "$GATEWAY_URL/services/order-service/api/v1/orders" --header "Content-Type: application/json" --data '{"productId":1,"shippingAddress":"Test address"}'
     
```