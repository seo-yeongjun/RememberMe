package com.zanygeek.rememberme.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

//회원가입 폼
@Data
public class JoinForm {

    @Length(min = 7, message = "아이디는 최소 7자 이상 이여야 합니다.")
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String userId;

    @Size(min = 10, message = "최소 10자 이상이여야 합니다.\n")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{10,}$", message = "영어 대소문자, 특수기호를 모두 사용해야합니다.")
    private String password;

    private String password2;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;
    @Email
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "날짜를 입력해 주세요.")
    private Date date;
    @Pattern(regexp = "[0-9]{8,}",message = "숫자만 입력해주세요.")
    private String phoneNumber;
    @AssertTrue(message = "동의해야 회원가입을 진행할 수있습니다.")
    private Boolean checkAgree;
}
