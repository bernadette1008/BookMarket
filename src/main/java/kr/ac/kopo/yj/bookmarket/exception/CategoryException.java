package kr.ac.kopo.yj.bookmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class CategoryException extends RuntimeException {
    private String message;
    private String category;
    public CategoryException(String category) {
        super();
        this.category = category;
        message = "요청한 도서 분야를 찾을 수 없습니다.";
        System.out.println(message);
    }

    public String getCategory() {
        return category;
    }
}
