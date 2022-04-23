package com.zanygeek.rememberme.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginForm {
    @NotBlank(message = "아이디가 비었습니다.")
    private String userId;
    @Size(min = 10, message = "비밀번호는 10자 이상입니다.\n")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{10,}$", message = "비밀번호는 영어 대소문자, 특수기호를 모두 포함 합니다.\n")
    private String password;
}
