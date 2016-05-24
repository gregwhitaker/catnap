package catnap.examples.springboot;

import com.github.gregwhitaker.catnap.springboot.annotation.EnableCatnap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCatnap
public class WidgetApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WidgetApplication.class, args);
    }
}
