package spark;


import com.fasterxml.jackson.annotation.JsonInclude;

import static spark.SwaggeredSpark.*;

/**
 * Created by juhof on 23/06/2017.
 */
public class HandlerExample {

    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public static class ExamplePojo {
        public String name;
        public String description;
    }

    public static void main(String[] args) {

        serveSwagger("/swagger");

        get("/at-root", (operation) -> {
            return (req, resp) -> {
                return "void";
            };
        });

        path("/sub-path", () -> {
            get("/operation", (operation) -> {
                return (req, resp) -> {
                    return "void";
                };
            });
            path("/nested-path", () -> {
                get("/info", (operation) -> {
                    operation.produces("application/json")
                            .required().queryParam("foo", "Foo is foo").example("one-two-foo")
                            .headerParam("api-key", "Correct api key").example("9192838139131");
                    return (req, res) -> {
                        System.out.println("running GET handler");
                        return "void";
                    };
                });
                post("/user", (operation) -> {
                    operation.produces("application/json")
                            .required().queryParam("foo", "Foo is foo").example("one-two-foo")
                            .bodyParam("foo", "Example").example("application/json", new ExamplePojo());
                    return (req, res) -> {
                        System.out.println("running POST handler");
                        return "void";
                    };
                });
            });
            get("/without-nesting", (operation) -> {
                return (req, resp) -> {
                    return "void";
                };
            });
        });

        get("/at-root-again", (operation) -> {
            return (req, resp) -> {
                return "void";
            };
        });
    }

}
