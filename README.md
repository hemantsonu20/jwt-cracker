# jwt-cracker
A jwt cracker via brute force approach

## USAGE
Downlaod the project and run mvn clean package. An executable jar is also checked in the repo (jwt-cracker-0.0.1-SNAPSHOT.jar)[lib/jwt-cracker-0.0.1-SNAPSHOT.jar]

## Command Line Options
| Flags                   | Descriptions                                                | Required | Default    |
| ---------------------   |-------------                                                | -----    | --------   |               
| "-t", "--token"         | jwt token to be cracked                                     | true     | No Default |
| "-mt", "--max-threads"  | max no of threads to be used                                | false    | 20         |
| "-l", "--length"        | max possible length of the jwt secret key                   | true     | 10         |
| "-c", "--chars"         | charset to be included combination of a-z, A-Z and 0-9 only | true     | a-zA-Z0-9  |

## Examples
* java -jar jwt-cracker-0.0.1-SNAPSHOT.jar -c a-z -t eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwianRpIjoiZTczMWJhZWYtNzA5MS00YTMxLWJjOWUtOWI5NWY3ZWZkOGY5IiwiaWF0IjoxNDg1MzMwMTk5LCJleHAiOjE0ODUzMzM3OTl9.t-pea7zd3MRfPI2M8hRKFum-4RY0l4xqbCUIyh8kiAc
 
 Output
 password cracked: [powers]
 total time taken [hh::mm:ss:SSS] 0:11:38.600
 
 * java -jar target/jwt-cracker-0.0.1-SNAPSHOT.jar -c a-z -t eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwianRpIjoiNzM4YmY5N2YtMzZiZi00MGViLWEwNzAtYWIyNjU2ODBkYzI2IiwiaWF0IjoxNDg1MzI5ODQ2LCJleHAiOjE0ODUzMzM0NDZ9.nbvi9BQJHbfPxAzGZHO6YbfKqAxrCjedJPPVnD0_QLY
 
 Output
 password cracked: [power]
 total time taken [hh::mm:ss:SSS] 0:00:26.349
