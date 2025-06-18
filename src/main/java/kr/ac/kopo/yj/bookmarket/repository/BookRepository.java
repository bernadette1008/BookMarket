package kr.ac.kopo.yj.bookmarket.repository;

import kr.ac.kopo.yj.bookmarket.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookRepository {
    List<Book> getAllBookList(); // 도메인 Book과 연결함
    Book getBookById(String bookId);
    List<Book> getBookListByCategory(String category);
    Set<Book> getBookListByFilter(Map<String, List<String>> filter); // Set : 기본적인 틀은 배열과 같지만 중복 값 X
    void setNewBook(Book book);
}
