package spark;

/**
 * Created by juhof on 24/06/2017.
 */
@FunctionalInterface
public interface SwaggerWrappedRoute {

    Route executeSwaggeredRoute(SwaggerOperationBuilder swagger);

}
