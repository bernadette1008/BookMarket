package kr.ac.kopo.yj.bookmarket.domain;

import jakarta.validation.constraints.*;
import kr.ac.kopo.yj.bookmarket.validator.BookId;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
//@Getter
//@Setter
//@NoArgsConstructor // 공백 주의
public class Book {
    @BookId
    @Pattern(regexp = "isbn[0-9]+", message = "{Pattern.book.bookId}")
    private String bookId; // 도서번호

    @Size(min = 4, max = 50, message = "{Size.book.name}")
    private String name; // 도서명

    @NotNull(message="{NotNull.book.unitPrice}")
    @Min(value = 0, message = "{Min.book.unitPrice}")
    @Digits(integer = 8, fraction = 2, message = "{Digits.book.unitPrice}")
    private BigDecimal unitPrice; // 단가

    private String author; // 저자
    private String description; // 도서 설명
    private String publisher; // 출판사
    private String category; // 도서 분류
    private long unitsInStock; // 재고량
    private String releaseDate; // 출판일
    private String condition; // 신규도서 or 중고도서 or 전자책
    private String fileName; // 이미지 파일 이름 저장
    private MultipartFile bookImage;
}
