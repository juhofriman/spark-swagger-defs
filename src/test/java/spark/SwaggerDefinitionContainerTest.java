package spark;

import com.jayway.jsonpath.JsonPath;
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
    public void seteUp() {
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
    }

    private void assertJsonSwagger(String expected, String query) {
        assertEquals(expected, JsonPath.read(swaggerDefinitionContainer.swaggerJson(), query));
    }
}
