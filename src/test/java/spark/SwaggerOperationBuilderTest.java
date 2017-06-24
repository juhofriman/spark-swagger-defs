package spark;

import io.swagger.models.Operation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juhof on 24/06/2017.
 */
public class SwaggerOperationBuilderTest {

    @Test
    public void testProduces() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/path");

        swaggerOperationBuilder.produces("application/json").consumes("application/json");

        assertEquals("application/json", swaggerOperationBuilder.getOperation().getProduces().get(0));
        assertEquals("application/json", swaggerOperationBuilder.getOperation().getConsumes().get(0));
    }

    @Test
    public void testQueryParam() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/path");

        swaggerOperationBuilder.queryParam("foo", "foo description");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertEquals("foo description", swaggerOperationBuilder.getOperation().getParameters().get(0).getDescription());
        assertFalse(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
    }

    @Test
    public void restRequiredQueryParam() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/path");

        swaggerOperationBuilder.required().queryParam("foo", "foo description");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertEquals("foo description", swaggerOperationBuilder.getOperation().getParameters().get(0).getDescription());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
    }

    @Test
    public void testQueryParamIsZeroedAfterAdd() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/path");

        swaggerOperationBuilder.required().queryParam("foo", "foo description")
                .queryParam("bar", "bar desc").required().queryParam("doo", "doo desc");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
        assertEquals("bar", swaggerOperationBuilder.getOperation().getParameters().get(1).getName());
        assertFalse(swaggerOperationBuilder.getOperation().getParameters().get(1).getRequired());
        assertEquals("doo", swaggerOperationBuilder.getOperation().getParameters().get(2).getName());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(2).getRequired());
    }

    @Test
    public void testHeaderParam() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/path");

        swaggerOperationBuilder.headerParam("foo", "foo description");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertEquals("foo description", swaggerOperationBuilder.getOperation().getParameters().get(0).getDescription());
        assertFalse(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
    }

    @Test
    public void restRequiredHeaderParam() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/path");

        swaggerOperationBuilder.required().headerParam("foo", "foo description");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertEquals("foo description", swaggerOperationBuilder.getOperation().getParameters().get(0).getDescription());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
    }

    @Test
    public void testHeaderParamIsZeroedAfterAdd() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/path");

        swaggerOperationBuilder.required().headerParam("foo", "foo description")
                .headerParam("bar", "bar desc").required().headerParam("doo", "doo desc");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
        assertEquals("bar", swaggerOperationBuilder.getOperation().getParameters().get(1).getName());
        assertFalse(swaggerOperationBuilder.getOperation().getParameters().get(1).getRequired());
        assertEquals("doo", swaggerOperationBuilder.getOperation().getParameters().get(2).getName());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(2).getRequired());
    }

    @Test
    public void testPathParam() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/{foo}");

        swaggerOperationBuilder.pathParam("foo", "path-param description");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertEquals("path-param description", swaggerOperationBuilder.getOperation().getParameters().get(0).getDescription());
        assertFalse(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
    }

    @Test
    public void restRequiredPathParam() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/{foo}");

        swaggerOperationBuilder.required().pathParam("foo", "foo description");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertEquals("foo description", swaggerOperationBuilder.getOperation().getParameters().get(0).getDescription());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
    }

    @Test
    public void testPathParamIsZeroedAfterAdd() throws Exception {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/{foo}/{bar}/{doo}");

        swaggerOperationBuilder.required().pathParam("foo", "foo description")
                .pathParam("bar", "bar desc").required().pathParam("doo", "doo desc");

        assertEquals("foo", swaggerOperationBuilder.getOperation().getParameters().get(0).getName());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(0).getRequired());
        assertEquals("bar", swaggerOperationBuilder.getOperation().getParameters().get(1).getName());
        assertFalse(swaggerOperationBuilder.getOperation().getParameters().get(1).getRequired());
        assertEquals("doo", swaggerOperationBuilder.getOperation().getParameters().get(2).getName());
        assertTrue(swaggerOperationBuilder.getOperation().getParameters().get(2).getRequired());
    }

    @Test(expected = PathParamDefinedButMissing.class)
    public void testThrowsForMissingPath() {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/{foo}");

        swaggerOperationBuilder.pathParam("bar", "path-param description");
    }

    @Test
    public void testExampleBody() {
        SwaggerOperationBuilder swaggerOperationBuilder =
                new SwaggerOperationBuilder("/foo");

        swaggerOperationBuilder.bodyParam("foo", "Foo is foo").example("application/json", new String("hello"));
    }
}
