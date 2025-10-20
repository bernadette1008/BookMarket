package kr.ac.kopo.yj.bookmarket.repository;

import kr.ac.kopo.yj.bookmarket.domain.Book;
import kr.ac.kopo.yj.bookmarket.exception.BookIdException;
import kr.ac.kopo.yj.bookmarket.exception.CategoryException;
import kr.ac.kopo.yj.bookmarket.validator.BookId;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class BookRepositoryImpl implements BookRepository{
    private List<Book> listOfBooks = new ArrayList<Book>();

    public BookRepositoryImpl() {
        Book book1 = new Book();
        book1.setBookId("isbn0001");
        book1.setName("스프링 부트 완전 정복");
        // book1.setUnitPrice(new BigDecimal(35000));
        book1.setUnitPrice(BigDecimal.valueOf(35000));
        book1.setAuthor("송미영");
        book1.setDescription("스프링 부트는 스프링을 기반으로 쉽고 빠르게 웹 애플리케이션을 개발할 수 있는 도구이다. " +
                "이 책에서는 스프링 부트의 기본 개념을 쉽게 이해하고 다양한 실습 예제로 빠르게 익힐 수 있다. " +
                "그리고 단계별 실습을 따라 하다 보면 도서 쇼핑몰 구축 프로젝트를 완성할 수 있다. " +
                "개념-실습-프로젝트의 3단계 학습으로 스프링 부트를 제대로 익힌다면 개발 시간을 단축하고 " +
                "생산성을 높일 수 있는 개발자로 성장할 수 있다.");
        book1.setPublisher("길벗캠퍼스");
        book1.setCategory("IT 교재");
        book1.setUnitsInStock(1000);
        book1.setReleaseDate("2024.12.31");
        book1.setCondition("신규 도서");
        book1.setFileName("isbn0001.jpg");

        Book book2 = new Book();
        book2.setBookId("isbn0002");
        book2.setName("난생처음 인공지능 입문");
        // book2.setUnitPrice(new BigDecimal(27000));
        book2.setUnitPrice(BigDecimal.valueOf(27000));
        book2.setAuthor("서지영");
        book2.setDescription("이 책은 IT 비전공자뿐만 아니라 중고등학생도 쉽게 배울 수 있는 인공지능 입문서이다. " +
                "인공지능의 개념을 실생활에서 접할 수 있는 사례를 중심으로 재미있는 삽화와 생생한 이미지를 통해 친절하게 설명한다. " +
                "그리고 인공지능을 구현하기 위해 필요한 기술인 GPU, 5G, 클라우드, 사물인터넷, 빅데이터, " +
                "머신러닝, 인공신경망, 딥러닝 등을 자세히 알아봅니다.");
        book2.setPublisher("한빛아카데미");
        book2.setCategory("IT 교재");
        book2.setUnitsInStock(642);
        book2.setReleaseDate("2024.10.07");
        book2.setCondition("중고 도서");
        book2.setFileName("isbn0002.jpg");

        Book book3 = new Book();
        book3.setBookId("isbn0003");
        book3.setName("PSIM을 활용한 전력전자공학 실습");
        // book3.setUnitPrice(new BigDecimal(32000));
        book3.setUnitPrice(BigDecimal.valueOf(32000));
        book3.setAuthor("박영수");
        book3.setDescription("『PSIM을 활용한 전력전자공학 실습』은 PSIM 프로그램을 사용하는 방법에 대한 기본 지식을 전달하고, " +
                "PSIM을 활용한 다양한 전력변환장치 실습 예제들을 소개한다. 여러 시뮬레이션 툴 중에서 PSIM은 전력전자공학 분야에서 " +
                "가장 활발히 사용되고 있으며, 회로 구성 및 코드 구현이 용이한 장점이 있다.");
        book3.setPublisher("계명대학교 출판부");
        book3.setCategory("전기전자공학");
        book3.setUnitsInStock(705);
        book3.setReleaseDate("2024.09.25");
        book3.setCondition("신규 도서");
        book3.setFileName("isbn0003.jpg");

        listOfBooks.add(book1); // 도서 데이터 저장
        listOfBooks.add(book2);
        listOfBooks.add(book3);
    }

    public List<Book> getAllBookList() {
        return listOfBooks;
    }


    public List<Book> getBookListByCategory(String category) {
        List<Book> booksByCategory = new ArrayList<Book>();
        for(int i =0 ; i<listOfBooks.size() ; i++) {
            Book book = listOfBooks.get(i);
            if(category.equalsIgnoreCase(book.getCategory()))
                booksByCategory.add(book);
        }
        return booksByCategory;
    }

    public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
        Set<Book> booksByPublisher = new HashSet<Book>();
        Set<Book> booksByCategory = new HashSet<Book>();

        Set<String> booksByFilter = filter.keySet();

        if (booksByFilter.contains("publisher")) {
            for (int j = 0; j < filter.get("publisher").size(); j++) {
                String pubisherName = filter.get("publisher").get(j);
                for (int i = 0; i < listOfBooks.size(); i++) {
                    Book book = listOfBooks.get(i);

                    if (pubisherName.equalsIgnoreCase(book.getPublisher()))
                        booksByPublisher.add(book);
                }
            }
        }

        if (booksByFilter.contains("category")) {
            for (int i = 0; i < filter.get("category").size(); i++) {
                String category = filter.get("category").get(i);
                List<Book> list = getBookListByCategory(category);
                booksByCategory.addAll(list);
            }
        }

        booksByCategory.retainAll(booksByPublisher);
        return booksByCategory;
    }

    public Book getBookById(String bookId) {
        Book bookInfo = null;
        for(int i =0 ;i<listOfBooks.size(); i++) {
            Book book = listOfBooks.get(i);
            if (book!=null && book.getBookId()!=null && book.getBookId().equals(bookId)){
                bookInfo = book;
                break;
            }
        }
        if(bookInfo == null)
            // throw new IllegalArgumentException("도서ID가 "+bookId + "인 해당 도서를 찾을 수 없습니다.");
            throw new BookIdException(bookId);
        return bookInfo;
    }

    public void setNewBook(Book book) {
        listOfBooks.add(book);
    }

}
