package com.doanjava.gradehub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.doanjava.gradehub.entity.NguoiDung.VaiTro;

public record RegisterRequest(
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    String email,

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    String password,

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    String confirmPassword,

    @NotBlank(message = "Họ tên không được để trống")
    String hoTen,

    VaiTro vaiTro,

    // Student specific fields
    String maSinhVien,
    String maNganh,
    String maChuyenNganh,

    // Teacher specific fields
    String maGiangVien,
    String boMon
) {
    public RegisterRequest {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
        }
        if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        if (vaiTro == null) {
            throw new IllegalArgumentException("Vai trò không được để trống");
        }
    }
}
