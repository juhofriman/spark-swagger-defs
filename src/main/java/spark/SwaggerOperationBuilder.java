package spark;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;

/**
 * Created by juhof on 24/06/2017.
 */
public class SwaggerOperationBuilder {

    private final String pathUri;
    private final Operation operation = new Operation();
    private boolean nextIsRequired = false;

    public SwaggerOperationBuilder(String pathUri) {
        this.pathUri = pathUri;
    }

    public String getPathUri() {
        return pathUri;
    }

    public Operation getOperation() {
        return operation;
    }

    public SwaggerOperationBuilder required() {
        this.nextIsRequired = true;
        return this;
    }

    public SwaggerOperationBuilder produces(String contentType) {
        this.operation.produces(contentType);
        return this;
    }

    public SwaggerOperationBuilder consumes(String contentType) {
        this.operation.consumes(contentType);
        return this;
    }

    public SwaggerOperationBuilder queryParam(String key, String description) {
        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setName(key);
        queryParameter.setDescription(description);
        queryParameter.setRequired(this.nextIsRequired);
        this.operation.addParameter(queryParameter);
        this.nextIsRequired = false;
        return this;
    }

    public SwaggerOperationBuilder headerParam(String key, String description) {
        HeaderParameter queryParameter = new HeaderParameter();
        queryParameter.setName(key);
        queryParameter.setDescription(description);
        queryParameter.setRequired(this.nextIsRequired);
        this.operation.addParameter(queryParameter);
        this.nextIsRequired = false;
        return this;
    }

    public SwaggerOperationBuilder pathParam(String key, String description) {
        if(!this.pathUri.contains(String.format("{%s}", key))) {
            throw new PathParamDefinedButMissing(this.pathUri, key);
        }
        PathParameter queryParameter = new PathParameter();
        queryParameter.setName(key);
        queryParameter.setDescription(description);
        queryParameter.setRequired(this.nextIsRequired);
        this.operation.addParameter(queryParameter);
        this.nextIsRequired = false;
        return this;
    }
}
