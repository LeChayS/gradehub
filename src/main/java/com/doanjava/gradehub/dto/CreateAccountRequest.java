package com.doanjava.gradehub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    String email,

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    String password,

    @NotNull(message = "Vai trò không được để trống")
    String vaiTro
) {
    public CreateAccountRequest {
        if (email != null) {
            email = email.trim();
        }
        if (password != null) {
            password = password.trim();
        }
        if (vaiTro != null) {
            vaiTro = vaiTro.trim();
        }
    }
}
