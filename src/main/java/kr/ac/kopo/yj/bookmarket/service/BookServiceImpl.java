package kr.ac.kopo.yj.bookmarket.service;

import kr.ac.kopo.yj.bookmarket.domain.Book;
import kr.ac.kopo.yj.bookmarket.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService{

    @Autowired // 객체 참조 자동 주입
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBookList() {return bookRepository.getAllBookList();}

    @Override
    public Book getBookById(String bookId) {return bookRepository.getBookById(bookId);}

    @Override
    public List<Book> getBookListByCategory(String category) {return bookRepository.getBookListByCategory(category);}

    @Override
    public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {return bookRepository.getBookListByFilter(filter);}

    @Override
    public void setNewBook(Book book) {bookRepository.setNewBook(book);}
}
