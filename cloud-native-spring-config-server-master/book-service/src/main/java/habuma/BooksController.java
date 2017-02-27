package habuma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {

	private BookRepository bookRepo;

	@Autowired
	public BooksController(BookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}

	// tag::bookByIsbn[]	
	@RequestMapping(method=RequestMethod.GET, path="/{isbn}")
	public BookDetails bookByIsbn(@PathVariable("isbn") String isbn) {
		Book book = bookRepo.findOne(isbn);
		long onHand = bookRepo.getQuantityOnHand(isbn);
		return new BookDetails(isbn, book.getTitle(), book.getAuthor(), book.getDescription(), onHand);
	}
	// end::bookByIsbn[]	
	
	@RequestMapping(method=RequestMethod.POST)
	public Book createBook(@RequestBody Book book) {
		return bookRepo.save(book);
	}
	
}
