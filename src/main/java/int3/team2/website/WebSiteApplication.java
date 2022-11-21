package int3.team2.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WebSiteApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WebSiteApplication.class, args);
    }

}
