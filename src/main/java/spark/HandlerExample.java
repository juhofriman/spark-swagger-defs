package spark;


import static spark.SwaggeredSpark.*;

/**
 * Created by juhof on 23/06/2017.
 */
public class HandlerExample {

    public static void main(String[] args) {

        serveSwagger("/swagger");

        get("/info", (operation) -> {
            operation.produces("application/json")
                    .queryParam("foo", "Foo is foo")
                    .headerParam("api-key", "Correct api key");
            return (req, res) -> {
                System.out.println("running handler");
                return "juuh";
            };
        });
    }

}
