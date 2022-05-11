package com.zanygeek.rememberme.form;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EditPasswordForm {
    private String savedPassword;
    @Size(min = 10, message = "최소 10자 이상이여야 합니다.\n")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{10,}$", message = "영어 대소문자, 특수기호를 모두 사용해야합니다.")
    private String password;
    private String password2;
}
