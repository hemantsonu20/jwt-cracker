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
