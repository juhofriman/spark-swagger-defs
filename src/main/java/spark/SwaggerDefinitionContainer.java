package spark;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

/**
 * Created by juhof on 24/06/2017.
 */
public class SwaggerDefinitionContainer {

    private Swagger swagger = new Swagger();

    public void basePath(String basePath) {
        this.swagger.setBasePath(basePath);
    }

    public String swaggerJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writeValueAsString(this.swagger);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Something went wrong while generating swagger json", e);
        }
    }

    public Path path(String pathString) {
        Path path = this.swagger.getPath(pathString);
        if(path == null) {
            path = new Path();
            this.swagger.path(pathString, path);
        }
        return path;
    }

    public void path(String pathString, Path path) {
    }
}
