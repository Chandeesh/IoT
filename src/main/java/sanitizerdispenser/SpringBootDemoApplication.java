package sanitizerdispenser;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories; 
 

@SpringBootApplication
@EnableMongoRepositories("sanitizerdispenser.repository")
public class SpringBootDemoApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}