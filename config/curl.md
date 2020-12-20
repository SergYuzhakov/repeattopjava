# Curl-queries for testing MealRestController:

### 1. Get all meals:
`curl http://localhost:8080/topjava/rest/meals`
### 2. Get meals by filter:
`curl 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=08:00&endTime=20:00'`
### 3. Get meal by id(id=100006):
`curl http://localhost:8080/topjava/rest/meals/100006`
### 4. Add new meal:
`curl -s -X POST http://localhost:8080/topjava/rest/meals
-H "Content-Type: application/json" 
-d '{
"dateTime": "2020-12-15T08:00:00",
"description": "Breakfast",
"calories": 666
}'`
### 5. Update existing meal by id:
`curl -s -X PUT http://localhost:8080/topjava/rest/meals/100006
-H "Content-Type: application/json"
-d 
'{"dateTime":"2020-01-31T08:00", 
"description":"Updated breakfast", 
"calories":200}'
`
### 6. Delete existing meal by id(id=100006):
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100006`
### 7. Get all users:
`curl -s http://localhost:8080/topjava/rest/admin/users`
### 8. Get user by id(id=100000)
`curl -s http://localhost:8080/topjava/rest/admin/users/100000`

