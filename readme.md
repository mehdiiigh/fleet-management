# Fleet Management

**Technology and Fremeworks**:
* spring-boot 3.1.3
* kotlin 1.8.2
* JWT Authentication/Authorization with spring-security
* JPA mysql
* opencsv
* flyway

### Prepare database
first of all run the [docker-compose.yml](docker-compose.yml) 
file with this command to start mysql database

```shell
docker-compose -f docker-compose.yml up -d
```
### Running the app
```shell
mvn spring-boot:run
```

### end-points
* login

login and get bearer token in response header
```shell
curl -s -i -H "Content-Type: application/json" -X POST -d '{ "username": "john.doe", "password": "test1234"}' http://localhost:8080/fleet-management/login | grep Authorization
# result: Authorization: Bearer ***
```
* /api/company 

Create a new company
 ```shell
  curl --location 'localhost:8080/fleet-management/api/company' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ***' \
--data '{
    "companyName": "triply"
}'
# result:
# status code: 201
{
    "companyId": 1,
    "companyName": "triply"
}
  ```
* /api/company/{id}

Get company with given id
```shell
curl --location 'localhost:8080/fleet-management/api/company/1' \
--header 'Authorization: Bearer ***'
# result:
# status code: 200
{
    "companyId": 1,
    "companyName": "triply"
}
  ```
* /api/upload-vehicles-csv

Upload a CSV file containing data about a company’s fleet
 ```shell
curl --location 'localhost:8080/fleet-management/api/vehicles' \
--header 'Authorization: Bearer ***' \
--form 'companyId="1"' \
--form 'file=@"/Users/mehdi/Desktop/vehicle_type.csv"'
# result:
# status code: 200
4 vehicle record added to database
# status code: 403
  ```
***vehicle_type.csv***
 ```
vehicle_type
A
B
C
D
 ```

* /api/upload-vehicle-usage-csv

CSV Parsing: The uploaded CSV files will contain columns for employee ID, vehicle type,
and average weekly mileage.
 ```shell
curl --location 'localhost:8080/fleet-management/api/upload-vehicle-usage-csv' \
--header 'Authorization: Bearer ***' \
--form 'file=@"/Users/mehdi/Desktop/vehicle-usage.csv"'
# result:
# status code: 200
2 vehicle usage record added to database
  ```
***vehicle_type.csv***
 ```
employee_id, vehicle_type, average_weekly_mileage
1, A, 1000
2, B, 200
3, C, 4000
4, A, 105.6
1, C, 5000
 ```

* /api/emission/employee/{id}

Retrieve emissions data for an individual employee based on their ID
 ```shell
curl --location 'localhost:8080/fleet-management/api/emission/employee/1' \
--header 'Authorization: Bearer ***'
# result:
# status code: 200
{
    "details": [
        {
            "vehicleType": ***,
            "vehicleEmissionFactors": ***,
            "averageWeeklyMileage": ***,
            "calculatedEmission": ***
        }
    ],
    "sumOfAverageWeeklyMileage": ***,
    "sumOfCalculatedEmissions": ***
}
# status code: 403
  ```
* /api/emission/company/{id}

Retrieve emissions data for a whole company
 ```shell
curl --location 'localhost:8080/fleet-management/api/emission/company/1' \
--header 'Authorization: Bearer ***'
# result:
# status code: 200
{
    "details": [
        {
            "vehicleType": ***,
            "vehicleEmissionFactors": ***,
            "averageWeeklyMileage": ***,
            "calculatedEmission": ***
        }
    ],
    "sumOfAverageWeeklyMileage": ***,
    "sumOfCalculatedEmissions": ***
}
# status code: 403
  ```
* /api/emission-suggestion/employee/{id}

Retrieve suggestions for vehicle replacements with electric alternatives for an individual employee based on their ID
 ```shell
  curl --location 'localhost:8080/fleet-management/api/emission-suggestion/employee/1' \
--header 'Authorization: Bearer ***'
# result:
# status code: 200
{
    "details": [
        {
            "vehicleType": ***,
            "vehicleEmissionFactors": ***,
            "averageWeeklyMileage": ***,
            "calculatedEmission": ***,
            "suggestionEmission": ***
        }
    ],
    "sumOfAverageWeeklyMileage": ***,
    "sumOfCalculatedEmissions": ***,
    "sumOfSuggestionEmission": ***
}
# status code: 403
  ```
* /api/emission-suggestion/company/{id}

Retrieve suggestions for vehicle replacements with electric alternatives for a whole company
```shell
  curl --location 'localhost:8080/fleet-management/api/emission-suggestion/company/1' \
--header 'Authorization: Bearer ***'
# result:
# status code: 200
{
    "details": [
        {
            "vehicleType": ***,
            "vehicleEmissionFactors": ***,
            "averageWeeklyMileage": ***,
            "calculatedEmission": ***,
            "suggestionEmission": ***
        }
    ],
    "sumOfAverageWeeklyMileage": ***,
    "sumOfCalculatedEmissions": ***,
    "sumOfSuggestionEmission": ***
}
# status code: 403
  ```

