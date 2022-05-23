Requirements:
- jdk11

1. running tests:
`./mvnw clean test`

2. generate and serve test report
`./mvnw allure:serve`


### Run groovy tests

Run tests with allure report generation.

```bash
./mvnw clean test -fn -pl service-backbase-groovy && ./mvnw -pl service-backbase-groovy allure:report
```

then open

```text
service-backbase-groovy/target/site/index.html
```

### Run groovy TestNG tests

*specific suite file*

```bash
./mvnw clean test -fn -pl service-backbase-groovy-testng -Dsurefire.suiteXmlFiles="src/test/resources/test-suite.yaml" && ./mvnw allure:report
```

*all the tests*

```bash
./mvnw clean test -fn -pl service-backbase-groovy-testng && ./mvnw allure:report
```

then open

```text
service-backbase-groovy-testng/target/site/index.html
```