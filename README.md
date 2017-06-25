# SPARK-SWAGGER-DEFS

This is a [Spark](http://sparkjava.com/) utility extension for adding [Swagger definitions](http://swagger.io/) to routes.

## Assumptions

1. Spark is great for building REST-services
2. Swagger is great for documenting REST-services
3. Current status of these two working together is not optimal
4. Full power of Spark comes from using lambda expression for defining routes, instead of using class based approaches
5. This library should be almost drop-in type extension to already implemented spark Handlers
6. No annotations, no extra classes required. Using [swagger-core](https://github.com/swagger-api/swagger-core) annotations would be ok, but there is no easy way of using annotations with lambda expressions

## Initial idea

Initial idea is that we can wrap spark routes with "DocumentationBuilder" which uses builder for defining swagger documentation
for current route and then returns the actual handler.

```java
get("/info", (operation) -> {
    // describe GET operation in /info
    operation.produces("application/json")
            .queryParam("foo", "Foo is a foo and is optional").example("one-two-foo")
            .required().headerParam("api-key", "Correct api key").example("9192838139131");
    // return the actual handler
    return (req, res) -> {
        return "Here's some info";
    };
});
```

In means of simplicity, this example does not actually use parameters (foo & api-key) in handler.

Current implementation supports sparks `path()` route builder as well.

```java
get("/info", (operation) -> {
    // describe GET operation in /info
    operation.produces("application/json")
            .queryParam("foo", "Foo is a foo and is optional").example("one-two-foo")
            .required().headerParam("api-key", "Correct api key").example("9192838139131");
    // return the actual handler
    return (req, res) -> {
        return "Here's some info";
    };
});
path("/api", () -> {
    get("/person", (operation) -> {
        // describe GET operation in /api/person
        operation.produces("application/json")
                .queryParam("personId", "Id of the person").example("12")
                .required().headerParam("api-key", "Correct api key").example("9192838139131");
        // return the actual handler
        return (req, res) -> {
            // Implementation omitted
        };
    });
    post("/person", (operation) -> {
        // describe POST operation in /api/person
        operation.produces("application/json")
                .required().bodyParam("person", "New person data").example(new Person())
                .required().headerParam("api-key", "Correct api key").example("9192838139131");
        // return the actual handler
        return (req, res) -> {
            // Implementation omitted
        };
    });
});
```