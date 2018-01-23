
package ${basePackage};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by ${author} on ${date}.
 */
@SpringBootApplication
public class ${appNameUpperCamel}Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(${appNameUpperCamel}Application.class, args);
    }
}

