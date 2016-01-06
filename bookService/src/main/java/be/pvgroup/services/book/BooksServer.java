package be.pvgroup.services.book;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Note that the configuration for this application is imported from
 * {@link AccountsWebApplication}. This is a deliberate separation of concerns
 * and allows the application to run:
 * <ul>
 * <li>Standalone - by executing {@link AccountsWebApplication#main(String[])}</li>
 * <li>As a microservice - by executing {@link BooksServer#main(String[])}</li>
 * </ul>
 * 
 * @author Paul Chapman
 */
@EnableAutoConfiguration
@Import(BookWebApplication.class)
public class BooksServer {

	@Autowired
	protected BookRepository bookRepository;

	protected Logger logger = Logger.getLogger(BooksServer.class.getName());
	
	public static void main(String[] args) {

		SpringApplication.run(BooksServer.class, args);
	}
}
