# Getting Started

## Requirements
There are should be installed :  
* docker(https://docs.docker.com/install/)
check 
```bash
 docker -v
```

* docker-compose(https://docs.docker.com/compose/) 
```bash
docker-compose --version
```

##How to run 

### step 1. Build maven project 

```bash
mvn clean package -DskipTests
```

### step 2. Build docker image 
```bash
    docker build .   
```
### step 3. Run docker -compose
```bash    
    docker-compose up
```

### step 3. Check 

backend 
```http request
http://localhost:8000/swagger-ui.html
```

data base:

| param     | value                       | 
| ---       | :----                        | 
| `url`     | jdbc:mysql://localhost:6033 | 
| `user`    | root                        | 
| `pass`    | 31323334                    | 

### Additional 
#### docker command for rebuild (update, only debug as quick as possible)

* clean volumes 
```bash
 sudo rm -rf my-sql
```

* stop all containers 
```bash
docker container stop $(docker container ls -aq)
```
* remove all containers
```bash
docker container rm $(docker container ls -aq)
```
* remove all images 
```bash
docker rmi $(docker images -a -q)
```

### Debug on IDE 
#### Development 
 use **local** environment  (application-local.properties)
 need to comment Bean DataSource(I have no time for setting up profiles)
#### remove debug 
port 5005



