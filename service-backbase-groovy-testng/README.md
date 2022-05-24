## service-backbase-groovy-testng

### Alternative Project Structure

```text

Application Client
        |
    Service A Requests
        ...
    Service B Requests
        ...
    Service C Requests
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

SomeResponseObject responseObject = application.someServiceRequests().someRequest(someRequestObject)
```

#### Use ServiceRequests

Expected to use for Endpoints Group context testing.

```groovy
SomeServiceRequests service = new SomeServiceRequests(token)

SomeResponseObject responseObject = service.someRequest(someRequestObject)
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