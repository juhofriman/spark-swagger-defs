package spark;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juhof on 24/06/2017.
 */
public class SwaggerDefinitionContainerTest {

    SwaggerDefinitionContainer swaggerDefinitionContainer;

    @Before
    public void setUp() {
        this.swaggerDefinitionContainer =
                new SwaggerDefinitionContainer();
    }

    @Test
    public void testSetBasePath() throws Exception {
        swaggerDefinitionContainer.basePath("/servlet");
        assertJsonSwagger("/servlet", "$.basePath");
    }

    @Test
    public void testGettingPath() throws Exception {
        Path path = swaggerDefinitionContainer.path("/foo");
        assertTrue(path.getOperations().isEmpty());

        Operation operation = new Operation();
        path.get(operation);

        Path pathAgain = swaggerDefinitionContainer.path("/foo");
        assertSame(path, pathAgain);
        assertFalse(path.getOperations().isEmpty());

        assertJsonSwaggerPathNotNull("$.paths./foo");
    }

    @Test
    public void testPushingPath() throws Exception {
        swaggerDefinitionContainer.pushPath("/path");
        Path path = swaggerDefinitionContainer.path("/foo");

        Operation operation = new Operation();
        path.get(operation);

        assertJsonSwaggerPathNotNull("$.paths./path/foo");

    }

    @Test
    public void testPoppingPath() throws Exception {
        swaggerDefinitionContainer.pushPath("/path");
        Path path = swaggerDefinitionContainer.path("/foo");

        Operation operation = new Operation();
        path.get(operation);

        swaggerDefinitionContainer.popPath();
        path = swaggerDefinitionContainer.path("/foo");

        operation = new Operation();
        path.get(operation);

        assertJsonSwaggerPathNotNull("$.paths./path/foo");
        assertJsonSwaggerPathNotNull("$.paths./foo");

    }

    private void assertJsonSwagger(String expected, String query) {
        assertEquals(expected, JsonPath.read(swaggerDefinitionContainer.swaggerJson(), query));
    }

    private void assertJsonSwaggerPathNotNull(String query) {
        try {
            assertNotNull(JsonPath.read(swaggerDefinitionContainer.swaggerJson(), query));
        } catch (PathNotFoundException e) {
            System.out.println(swaggerDefinitionContainer.swaggerJson());
            fail(String.format("Expected %s in json but not found", query));
        }
    }
}
