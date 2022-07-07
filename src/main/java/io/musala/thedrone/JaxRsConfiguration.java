    package io.musala.thedrone;

    import io.musala.thedrone.util.Constants;
    import io.swagger.v3.oas.annotations.OpenAPIDefinition;
    import io.swagger.v3.oas.annotations.info.Contact;
    import io.swagger.v3.oas.annotations.info.Info;
    import io.swagger.v3.oas.annotations.info.License;
    import io.swagger.v3.oas.annotations.servers.Server;
    import io.swagger.v3.oas.annotations.servers.ServerVariable;

    import javax.ws.rs.ApplicationPath;
    import javax.ws.rs.core.Application;

    @OpenAPIDefinition(
            info = @Info(
                    title = "The Drone",
                    version = "0.0",
                    description = "My API",
                    license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"),
                    contact = @Contact(url = "https://mail.google.com/", name = "Wilfred", email = "wilfredkim5@gmail.com")
            ),
            servers = {
                    @Server(
                            description = "server 1",
                            url = Constants.BASE_URL,
                            variables = {
                                    @ServerVariable(name = "var1", description = "var 1", defaultValue = "1", allowableValues = {"1", "2"})
                            })
            }
    )
    @ApplicationPath("/api")
    public class JaxRsConfiguration extends Application {

    }