    package io.musala.thedrone.exceptionmapper;

    import javax.ws.rs.core.Response;
    import javax.ws.rs.ext.Provider;

    @Provider
    public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<RuntimeException> {

        @Override
        public Response toResponse(RuntimeException exception) {
            return Response.serverError().entity(ApiResponse.builder().success(false).message(exception.getLocalizedMessage()).build()).build();
        }
    }
