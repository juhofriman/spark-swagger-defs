package spark;

/**
 * Created by juhof on 24/06/2017.
 */
public class PathParamDefinedButMissing extends RuntimeException {

    public PathParamDefinedButMissing(String path, String paramName) {
        super(String.format("Path %s does not seem to contain %s which is defined to swagger", path, paramName));
    }
}
