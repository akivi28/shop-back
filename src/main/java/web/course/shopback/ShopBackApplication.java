package web.course.shopback;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCosmosRepositories
public class ShopBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopBackApplication.class, args);
    }

}
