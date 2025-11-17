package kr.ac.kopo.yj.bookmarket.domain;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {
    @NotBlank(message = "ID는 필수 입력 값입니다.")
    private String memberId;
    @NotBlank(message = "PW는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "PW는 최소 8자 이상 최대 16자 이하로 입력해주세요.")
    private String password;
    @NotBlank(message = "성명은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phone;
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;
}
