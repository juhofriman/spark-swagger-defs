package spark;

import io.swagger.models.ModelImpl;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.parameters.*;

/**
 * Created by juhof on 24/06/2017.
 */
public class SwaggerOperationBuilder {

    private final String pathUri;
    private final Operation operation = new Operation();
    private boolean nextIsRequired = false;
    private AbstractParameter latestQueryOrHeader;
    private BodyParameter latestBodyParameter;

    public SwaggerOperationBuilder(String pathUri) {
        this.pathUri = pathUri;
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
        this.latestQueryOrHeader = queryParameter;
        this.nextIsRequired = false;
        return this;
    }

    public SwaggerOperationBuilder headerParam(String key, String description) {
        HeaderParameter headerParameter = new HeaderParameter();
        headerParameter.setName(key);
        headerParameter.setDescription(description);
        headerParameter.setRequired(this.nextIsRequired);
        this.operation.addParameter(headerParameter);
        this.latestQueryOrHeader = headerParameter;
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
        this.latestQueryOrHeader = queryParameter;
        this.nextIsRequired = false;
        return this;
    }

    public SwaggerOperationBuilder bodyParam(String key, String description) {
        BodyParameter bodyParameter = new BodyParameter();
        bodyParameter.setName(key);
        bodyParameter.setDescription(description);
        this.latestBodyParameter = bodyParameter;
        this.operation.addParameter(bodyParameter);
        this.nextIsRequired = false;
        return this;
    }

    public SwaggerOperationBuilder example(String example) {
        if(this.latestQueryOrHeader == null) {
            throw new RuntimeException("No query of header parameter set. Set it before calling example()");
        }
        if(this.latestQueryOrHeader instanceof QueryParameter) {
            ((QueryParameter) this.latestQueryOrHeader).example(example);
        }
        if(this.latestQueryOrHeader instanceof PathParameter) {
            ((PathParameter) this.latestQueryOrHeader).example(example);
        }
        if(this.latestQueryOrHeader instanceof HeaderParameter) {
            ((HeaderParameter) this.latestQueryOrHeader).example(example);
        }
        return this;
    }

    public SwaggerOperationBuilder example(String mediaType, Object example) {
        if(this.latestBodyParameter == null) {
            throw new RuntimeException("No bodyParam set. Set it before calling example()");
        }
        this.latestBodyParameter.setSchema(new ModelImpl());
        this.latestBodyParameter.getSchema().setExample(example);
        return this;
    }
}
