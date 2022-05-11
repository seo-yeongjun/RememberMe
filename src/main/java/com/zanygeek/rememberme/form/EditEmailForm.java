package com.zanygeek.rememberme.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EditEmailForm {
    @NotBlank
    private String savedEmail;
    @Email
    @NotBlank
    private String email;
}
