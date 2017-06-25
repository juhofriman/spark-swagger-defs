package spark;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created by juhof on 24/06/2017.
 */
public class SwaggerDefinitionContainer {

    private Swagger swagger = new Swagger();

    private LinkedList<String> path = new LinkedList<>();

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
        String fullPath = createFullPath(pathString);
        Path path = this.swagger.getPath(fullPath);
        if(path == null) {
            path = new Path();
            this.swagger.path(fullPath, path);
        }
        return path;
    }

    private String createFullPath(String pathString) {
        return this.path.stream().collect(Collectors.joining("")) + pathString;
    }

    public void pushPath(String path) {
        this.path.addLast(path);
    }

    public void popPath() {
        this.path.removeLast();
    }
}
