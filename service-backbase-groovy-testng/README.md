## service-backbase-groovy-testng

### Alternative Project Structure

```text

Application Client
        |
    Service A
        ...
    Service B
        ...
    Service C
            |
        Service Endpoint A
            ...
        Service Endpoint B
            ...
        Service Endpoint C
                    |
                Service Endpoint Request Model
                    Service Endpoint Request Model Sampler
                Service Endpoint Response Model
                Service Endpoint Error Response Model
        
```

#### Use Application

Expected to use for Application Flows context testing.

```groovy
ApplicationClient application = new ApplicationClient(token)

SomeResponseObject responseObject = application.someService().someEndpoint().execute(someRequestObject)
```

#### Use Service

Expected to use for Endpoints Group context testing.

```groovy
SomeService service = new SomeService(token)

SomeResponseObject responseObject = service.someEndpoint().execute(someRequestObject)
```

#### Use Endpoint

Expected to use for Endpoint context testing.

```groovy
SomeEndpoint endpoint = new SomeEndpoint(token)

SomeResponseObject responseObject = endpoint.execute(someRequestObject)
```

### Endpoint Definition

```groovy
interface Endpoint<Input, Success, Error> {

  Response<Success, Error> execute(Input body)

}
```

### Request Sampler

- `minimal` implementation should generate object with only required fields
- `full` implementation should generate object with all the fields

```groovy
interface RequestSampler<T> {

  T minimal()

  T full()
}

```