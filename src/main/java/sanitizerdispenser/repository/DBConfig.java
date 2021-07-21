package sanitizerdispenser.repository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("sanitizerdispenser.repository.*")
public class DBConfig {

	@Bean
	public CommandLineRunner initDatabase(UserRepository repository) {
		return (args) -> {

		};
	}
	
	@Bean
	public CommandLineRunner initDatabaseDevice(DeviceRepository repository) {
		return (args) -> {

		};
	}
	
	@Bean
	public CommandLineRunner initDatabaseSanitizer(SanitizerLevelRepository repository) {
		return (args) -> {

		};
	}
}
