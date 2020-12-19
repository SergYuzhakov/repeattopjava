# Curl-queries for testing MealRestController:

### 1. Get all meals:
`curl http://localhost:8080/topjava/rest/meals`
### 2. Get meals by filter:
`curl 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=08:00&endTime=20:00'`
### 3. Get meal by id(id=100006):
`curl http://localhost:8080/topjava/rest/meals/100006`
### 4. Add new meal:
`curl  -X POST http://localhost:8080/topjava/rest/meals
-H "Content-Type: application/json" 
-d {
"dateTime": "2020-12-15T08:00:00",
"description": "Breakfast",
"calories": 666
}`
### 5. Update existing meal by id:
`curl -X PUT http://localhost:8080/topjava/rest/meals/100006 
-H "Content-Type: application/json" 
-d{
     "dateTime": "2020-01-31T10:00:00",
    "description": "Завтрак2",
    "calories": 499
}`
### 6. Delete existing meal by id(id=100006):
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100006`
