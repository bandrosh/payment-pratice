# payment-practice

API with functionality of Payment and Managing accounts.

### Prerequisites:
```
Java 17
Spring Boot
Maven
Postgresql
Docker
Docker-compose
```

### How to use:

```
Set DB environments from postgres database:
DB_USER ; DB_PASS ; DB_URL

If you have any postgres docker up, just down and execute:
make build-image

This command will turn up local database, build project with this local variables and build docker image.

Docker image: payment-practice 

with docker builded. Run docker-compose up -d


```

### Documentation

API Documentation in :
[doc](https://github.com/bandrosh/https://https://github.com/bandrosh/payment-pratice/doc/payment-practice-api.yaml)

### Future Work
```
-Changed TDD/Unitary tests to BDD library
``` 