package kr.ac.kopo.yj.bookmarket.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.ac.kopo.yj.bookmarket.domain.Book;
import kr.ac.kopo.yj.bookmarket.repository.BookRepository;
import kr.ac.kopo.yj.bookmarket.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

public class BookIdValidator implements ConstraintValidator<BookId, String> {
    @Autowired
    private BookService bookService;

    @Override
    public void initialize(BookId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String bookId, ConstraintValidatorContext constraintValidatorContext) {
        Book book = null;

        try
        {
            book = bookService.getBookById(bookId);
        }
        catch (RuntimeException e)
        {
            return true;
        }

        if(book != null) {
            return false; // 이미 사용 중인 ID일 경우
        }
        return true;
    }
}
