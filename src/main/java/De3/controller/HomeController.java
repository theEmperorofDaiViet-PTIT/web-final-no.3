package De3.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import De3.data.BookRepository;
import De3.entity.Book;

@Controller
@SessionAttributes("searchbook")
public class HomeController {
	
	@Autowired
	private BookRepository repo;
	
	@ModelAttribute("searchbook")
	public Book book() {
		return new Book();
	}

	@GetMapping("/")
	public String home(Model model, @ModelAttribute Book searchbook) {
		if(searchbook.getBookcode() != null) {
			model.addAttribute("searchbook", searchbook);
		}
		else {
			model.addAttribute("searchbook", new Book());
		}
		
		return "index";
	}
	
	@PostMapping("/search")
	public String search(Book searchbook, Model model) {
		ArrayList<Book> res = (ArrayList<Book>) repo.findByBookcodeContainingAndTitleContainingAndAuthorContainingAndCategoryContaining(searchbook.getBookcode(), searchbook.getTitle(), searchbook.getAuthor(), searchbook.getCategory());
		model.addAttribute("result", res);
		return "result";
	}
	
	@PostMapping("/book")
	public String book(@RequestParam(name="code") String code) {
		Book tmp = repo.findByBookcode(code);
		Book book = new Book(tmp.getBookcode(), tmp.getTitle(), tmp.getAuthor(), tmp.getCategory(), 1);
		repo.save(book);
		return "redirect:/";
	}
}
