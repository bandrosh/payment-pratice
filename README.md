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

Steps:
- make build-image

This command will use your database for build project with this local variables and build docker image.

- Turn off you database container
- Run docker-compose up -d

Docker image: payment-practice
service running in :8080

```

### Documentation

API Documentation in :
[doc](https://github.com/bandrosh/https://https://github.com/bandrosh/payment-pratice/doc/payment-practice-api.yaml)

### Future Work
```
-Changed TDD/Unitary tests to BDD library
``` 