package balda.config;

import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Brought by anatolyd on 08.04.2017.
 */
@Configuration
public class WebConfigurer {
    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    /**
     * Initializes H2 console.
     */
    private void initH2Console(ServletContext servletContext) {
        log.debug("Initialize H2 console");
        ServletRegistration.Dynamic h2ConsoleServlet = servletContext.addServlet("H2Console", new org.h2.server.web.WebServlet());
        h2ConsoleServlet.addMapping("/h2-console/*");
        h2ConsoleServlet.setInitParameter("-properties", "src/main/resources/");
        h2ConsoleServlet.setLoadOnStartup(1);
    }
}
