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
    @Pattern(regexp="ISBN[1-9]+", message="{Pattern.book.bookId}")
    private String bookId; //도서ID

    @Size(min=4, max=50, message="{Size.book.name}")
    private String name; // 도서명

    @Min(value=0, message="{Min.book.unitPrice}")
    @Digits(integer=8, fraction=2, message="{Digits.book.unitPrice}")
    @NotNull(message="{NotNull.book.unitPrice}")
    private BigDecimal unitPrice; // 가격
    private String author; // 저자
    private String description; // 설명
    private String publisher; // 출판사
    private String category; // 분류
    private long unitsInStock; // 재고수
    private String releaseDate; // 출판일
    private String condition; // 상태 : 신규도서/E-Book/중고도서
    private String fileName; //도서 이미지 파일
    private MultipartFile bookImage;  //도서 이미지
}
