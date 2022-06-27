package De3.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import De3.entity.Book;

public interface BookRepository extends CrudRepository<Book, String>{
	Book findByBookcode(String code);
	
	List<Book> findByBookcodeContainingAndTitleContainingAndAuthorContainingAndCategoryContaining(String bookcode, String title, String author, String category);
}
