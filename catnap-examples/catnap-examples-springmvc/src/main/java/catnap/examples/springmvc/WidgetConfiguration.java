package catnap.examples.springmvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@ComponentScan(basePackages = "catnap.examples.springmvc")
public class WidgetConfiguration extends WebMvcConfigurationSupport {

}
