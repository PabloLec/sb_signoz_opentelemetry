curl -X POST http://localhost:8081/users \
-H "Content-Type: application/json" \
-d '{
  "name": "John Doe",
  "email": "johndoe@example.com"
}'