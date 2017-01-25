# jwt-cracker
A jwt cracker via brute force approach

## USAGE
Downlaod the project and run mvn clean package. A jar will be created in your {projectdir}/target directory.
An executable jar is also checked in the repo [jwt-cracker-0.0.1-SNAPSHOT.jar](lib/).

## Command Line Options
| Flags                   | Descriptions                                                | Required | Default    |
| ---------------------   |-------------                                                | -----    | --------   |               
| "-t", "--token"         | jwt token to be cracked                                     | true     | No Default |
| "-mt", "--max-threads"  | max no of threads to be used                                | false    | 20         |
| "-l", "--length"        | max possible length of the jwt secret key                   | true     | 10         |
| "-c", "--chars"         | charset to be included combination of a-z, A-Z and 0-9 only | true     | a-zA-Z0-9  |

## Examples
1. java -jar jwt-cracker-0.0.1-SNAPSHOT.jar -c a-z -t eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwianRpIjoiZTczMWJhZWYtNzA5MS00YTMxLWJjOWUtOWI5NWY3ZWZkOGY5IiwiaWF0IjoxNDg1MzMwMTk5LCJleHAiOjE0ODUzMzM3OTl9.t-pea7zd3MRfPI2M8hRKFum-4RY0l4xqbCUIyh8kiAc
 
 **Output**<br/>
 password cracked: [powers]<br/>
 total time taken [hh::mm:ss:SSS] 0:11:38.600
 
2. java -jar target/jwt-cracker-0.0.1-SNAPSHOT.jar -c a-z -t eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwianRpIjoiNzM4YmY5N2YtMzZiZi00MGViLWEwNzAtYWIyNjU2ODBkYzI2IiwiaWF0IjoxNDg1MzI5ODQ2LCJleHAiOjE0ODUzMzM0NDZ9.nbvi9BQJHbfPxAzGZHO6YbfKqAxrCjedJPPVnD0_QLY
 
 **Output**<br/>
 password cracked: [power]<br/>
 total time taken [hh::mm:ss:SSS] 0:00:26.349
 
3. java -jar target/jwt-cracker-0.0.1-SNAPSHOT.jar -c a-z0-9 -t eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwianRpIjoiN2JiODdjOGItMzJiMS00NTk3LWFlMGYtMmI1MWY3MTQ1YjNlIiwiaWF0IjoxNDg1MzM2NTkzLCJleHAiOjE0ODUzNDAxOTN9.sxua3rDJcSH0cKWu5F6v0Xq__1DZ5HdjcnRfwWqnEpA

 **Output**<br/>
 password cracked: [new123]<br/>
 total time taken [hh::mm:ss:SSS] 0:20:52.520
 
4. java -jar target/jwt-cracker-0.0.1-SNAPSHOT.jar -c a-z0-9 -t eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwianRpIjoiN2JiODdjOGItMzJiMS00NTk3LWFlMGYtMmI1MWY3MTQ1YjNlIiwiaWF0IjoxNDg1MzM2NTkzLCJleHAiOjE0ODUzNDAxOTN9.sxua3rDJcSH0cKWu5F6v0Xq__1DZ5HdjcnRfwWqnEpA -mt 50

 **Output**<br/>
 password cracked: [new123]<br/>
 total time taken [hh::mm:ss:SSS] 0:38:19.124
 
 5. java -jar target/jwt-cracker-0.0.1-SNAPSHOT.jar -c a-z0-9 -t eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwianRpIjoiN2JiODdjOGItMzJiMS00NTk3LWFlMGYtMmI1MWY3MTQ1YjNlIiwiaWF0IjoxNDg1MzM2NTkzLCJleHAiOjE0ODUzNDAxOTN9.sxua3rDJcSH0cKWu5F6v0Xq__1DZ5HdjcnRfwWqnEpA -mt 4

 **Output**<br/>
 password cracked: [new123]<br/>
 total time taken [hh::mm:ss:SSS] 1:01:22.845
 
## Note
* Its not always beneficial to use more thread for better performance.
* See example 3 (20 thread), example 4 (50 thread) and example 5 (4 thread). More threads may add overhead of context switch to the system resulting in performance degradation.
* All above tests were executed on my system with Intel i5 @3.20GHz 2 core, 8 GB RAM.
