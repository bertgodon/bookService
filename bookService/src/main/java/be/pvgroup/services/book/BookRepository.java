package be.pvgroup.services.book;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface BookRepository extends Repository<Book, Long>{

	public List<Book> findByAuthorContainingIgnoreCase(String author);

	public Book findById(Long id);
	
	public Book save(Book book);
	
	public void delete(Long id);
	
	public List<Book> findAll();
	
	@Query("SELECT count(*) from Book")
	public int countAccounts();
}
