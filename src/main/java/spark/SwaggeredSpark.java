package spark;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

/**
 * Created by juhof on 24/06/2017.
 */
public class SwaggeredSpark {

    private static SwaggerDefinitionContainer sdc = new SwaggerDefinitionContainer();

    public static void serveSwagger(String path) {
        Spark.get(path, (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Content-type", "application/json");
            return sdc.swaggerJson();
        });
    }

    public static void get(String path, SwaggerWrappedRoute route) {
        SwaggerOperationBuilder operationBuilder = new SwaggerOperationBuilder(path);
        Spark.get(path, route.executeSwaggeredRoute(operationBuilder));
        sdc.path(path).get(operationBuilder.getOperation());
    }

    public static void post(String path, SwaggerWrappedRoute route) {
        SwaggerOperationBuilder operationBuilder = new SwaggerOperationBuilder(path);
        Spark.post(path, route.executeSwaggeredRoute(operationBuilder));
        sdc.path(path).post(operationBuilder.getOperation());
    }

    public static void put(String path, SwaggerWrappedRoute route) {
        SwaggerOperationBuilder operationBuilder = new SwaggerOperationBuilder(path);
        Spark.put(path, route.executeSwaggeredRoute(operationBuilder));
        sdc.path(path).put(operationBuilder.getOperation());
    }

    public static void delete(String path, SwaggerWrappedRoute route) {
        SwaggerOperationBuilder operationBuilder = new SwaggerOperationBuilder(path);
        Spark.delete(path, route.executeSwaggeredRoute(operationBuilder));
        sdc.path(path).delete(operationBuilder.getOperation());
    }
}
