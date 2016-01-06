package be.pvgroup.services.book;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;


@SpringBootApplication
@EntityScan("be.pvgroup.services.book")
@EnableJpaRepositories("be.pvgroup.services.book")
@PropertySource("classpath:db-config.properties")
public class BookWebApplication {

	protected Logger logger = Logger.getLogger(BookWebApplication.class
			.getName());

	public static void main(String[] args) {
		SpringApplication.run(BookWebApplication.class, args);
	}


	@Bean
	public DataSource dataSource() {

		DataSource dataSource = (new EmbeddedDatabaseBuilder())
				.addScript("classpath:testdb/schema.sql")
				.addScript("classpath:testdb/data.sql").build();

		return dataSource;
	}
}
