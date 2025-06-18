package kr.ac.kopo.yj.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.ac.kopo.yj.bookmarket.domain.Book;
import kr.ac.kopo.yj.bookmarket.exception.BookIdException;
import kr.ac.kopo.yj.bookmarket.exception.CategoryException;
import kr.ac.kopo.yj.bookmarket.exception.CommonException;
import kr.ac.kopo.yj.bookmarket.service.BookService;
import kr.ac.kopo.yj.bookmarket.validator.BookValidator;
import kr.ac.kopo.yj.bookmarket.validator.UnitsInStockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.catalog.CatalogException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value="/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Value("${file.uploadDir}")
    String fileDir;

//    @Autowired
//    private UnitsInStockValidator unitsInStockValidator;

    @Autowired
    private BookValidator bookValidator;

    @RequestMapping // 출력할 페이지
    public String requestBookList(Model model) { // HTML에서 사용할 수 있도록
        List<Book> bookList = bookService.getAllBookList();
        model.addAttribute("bookList", bookList); // 도서 리스트를 사용함
        return "books";
    }

    @RequestMapping(value = "/all") // 출력할 페이지
    public String requestAllBookList(Model model) { // HTML에서 사용할 수 있도록
        List<Book> bookList = bookService.getAllBookList();
        model.addAttribute("bookList", bookList); // 도서 리스트를 사용함
        return "books";
    }

    @RequestMapping(value="/modelView")
    public ModelAndView requestModelView(){
        ModelAndView model = new ModelAndView();
        model.setViewName("books");

        List<Book> bookList = bookService.getAllBookList();
        model.addObject("bookList", bookList);

        return model;
    }

    @GetMapping("/book")
    public String requestBookById(@RequestParam("id") String bookId, Model model){
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);

        return "book";
    }

    @GetMapping("/{category}")
    public String requestBookByCategory(@PathVariable("category") String category, Model model){
        List<Book> booksByCategory = bookService.getBookListByCategory(category);
        model.addAttribute("bookList", booksByCategory);
        if(booksByCategory == null || booksByCategory.isEmpty()){
            throw new CategoryException(category);
        }
        return "books";
    }

    @GetMapping("/filter/{bookFilter}")
    public String requestBookByFilter(@MatrixVariable(pathVar = "bookFilter")Map<String, List<String>> bookFilter, Model model){
        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);

        return "books";
    }

//    @GetMapping("/add")
//    public String requestAddBookForm(Model model){
//
//
//        return "addBook";
//    }
//
//    @PostMapping("/add")
//    public String requestSubmitNewBook(@ModelAttribute("book")Book book){
//        bookService.setNewBook(book);
//
//        return "redirect:/books";
//    }

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @GetMapping("/add")
    public String requestAddBookForm(Model model){
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/add")
    public String requestSubmitAddNewBook(@Valid @ModelAttribute Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "addBook";
        }
        MultipartFile bookImage = book.getBookImage();

        String saveName = bookImage.getOriginalFilename();
        File saveFile = new File(fileDir + saveName);
        if(bookImage != null && !bookImage.isEmpty()){
            try {
                bookImage.transferTo(saveFile);
            } catch (IOException e) {
                throw new RuntimeException("도서 이미지 업로드가 되지 않았습니다.");
            }
        }
        book.setFileName(saveName);

        bookService.setNewBook(book);

        return "redirect:/books";
    }

    @GetMapping("/download")
    public void downloadBookImage(@RequestParam("file") String paramKey, HttpServletResponse response) throws IOException {
        File imageFile = new File(fileDir + paramKey);
        response.setContentType("application/download");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ paramKey + "\"");
        response.setContentLength((int) imageFile.length());
        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(imageFile);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();

    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(bookValidator);
        binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description", "publisher", "category", "unitsInStock", "releaseDate", "condition", "bookImage");
    }

    @ExceptionHandler(value = {BookIdException.class})
    public ModelAndView handleBookIdException(HttpServletRequest request, BookIdException e){
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidBookId", e.getBookId());
        mav.addObject("exception", e.toString());
        mav.addObject("url", request.getRequestURL()+"/"+request.getQueryString());
        mav.setViewName("errorBook");

        return mav;
    }
}
