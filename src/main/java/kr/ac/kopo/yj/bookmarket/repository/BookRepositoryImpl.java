package kr.ac.kopo.yj.bookmarket.repository;

import kr.ac.kopo.yj.bookmarket.domain.Book;
import kr.ac.kopo.yj.bookmarket.exception.BookIdException;
import kr.ac.kopo.yj.bookmarket.exception.CategoryException;
import kr.ac.kopo.yj.bookmarket.validator.BookId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Book> listOfBooks = new ArrayList<Book>();


    public List<Book> getAllBookList() {
        String sql = "select * from book";
        listOfBooks = jdbcTemplate.query(sql, new BookRowMapper());
        return listOfBooks;
    }


    public List<Book> getBookListByCategory(String category) {
        List<Book> booksByCategory = new ArrayList<Book>();
        String sql = "SELECT * FROM book where b_category LIKE '%" + category + "%'";
        booksByCategory = jdbcTemplate.query(sql, new BookRowMapper());
//	      for(int i =0 ; i<listOfBooks.size() ; i++) {
//	         Book book = listOfBooks.get(i);
//	         if(category.equalsIgnoreCase(book.getCategory()))
//		    booksByCategory.add(book);
//	      }
        return booksByCategory;
    }

    public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
        Set<Book> booksByPublisher = new HashSet<Book>();
        Set<Book> booksByCategory = new HashSet<Book>();

        Set<String> booksByFilter = filter.keySet();

        if (booksByFilter.contains("publisher")) {
            for (int j = 0; j < filter.get("publisher").size(); j++) {
                String pubisherName = filter.get("publisher").get(j);
                String sql = "SELECT * FROM book where b_publisher LIKE '%" + pubisherName + "%'";
                List<Book> book = jdbcTemplate.query(sql, new BookRowMapper());
                booksByPublisher.addAll(book);
            }
        }

        if (booksByFilter.contains("category")) {
            for (int i = 0; i < filter.get("category").size(); i++) {
                String category = filter.get("category").get(i);
                String sql = "SELECT * FROM book where b_category LIKE '%" + category + "%'";
                List<Book> list = jdbcTemplate.query(sql, new BookRowMapper());
                booksByCategory.addAll(list);
            }
        }

        booksByCategory.retainAll(booksByPublisher);
        return booksByCategory;
    }

    public Book getBookById(String bookId) {
        Book bookInfo = null;
        String sql = "SELECT count(*) FROM book where b_bookId=?";
        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, bookId);

        if (rowCount != 0) {
            sql = "SELECT * FROM book where b_bookId=?";
            bookInfo = jdbcTemplate.queryForObject(sql, new BookRowMapper(),bookId );
        }

        if (bookInfo== null)
            throw new BookIdException(bookId);

        return bookInfo;
    }

    //	  새로운 도서 추가
    public void setNewBook(Book book) {
        String SQL = "INSERT INTO book (b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate,b_condition, b_fileName) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(SQL, book.getBookId(), book.getName(), book.getUnitPrice(), book.getAuthor(),
                book.getDescription(), book.getPublisher(), book.getCategory(), book.getUnitsInStock(),
                book.getReleaseDate(), book.getCondition(), book.getFileName());
    }

    public void setUpdateBook(Book book) {
        if (book.getFileName() != null) {
            String SQL = "UPDATE Book SET b_name = ?, b_unitPrice = ?, b_author = ?, b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?,b_releaseDate = ?, b_condition = ?, b_fileName = ?  where b_bookId = ? ";
            jdbcTemplate.update(SQL, book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(),
                    book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
                    book.getCondition(), book.getFileName(), book.getBookId());
        } else if (book.getFileName() == null) {
            String SQL = "UPDATE Book SET b_name = ?, b_unitPrice = ?, b_author = ?, b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, b_releaseDate = ?, b_condition = ?  where b_bookId = ? ";
            jdbcTemplate.update(SQL, book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(),
                    book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
                    book.getCondition(), book.getBookId());
        }
    }

    public void setDeleteBook(String bookID) {
        String SQL = "DELETE from Book where b_bookId = ? ";
        jdbcTemplate.update(SQL, bookID);
    }


}
