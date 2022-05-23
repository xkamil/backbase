Requirements:
- jdk11

1. running tests:
`./mvnw clean test`

2. generate and serve test report
`./mvnw allure:serve`


### Run groovy tests

Run tests will allure report generation.

```bash
./mvnw clean test -fn -pl service-backbase-groovy && ./mvnw -pl service-backbase-groovy allure:report
```

then open

```text
service-backbase-groovy/target/site/index.html
```