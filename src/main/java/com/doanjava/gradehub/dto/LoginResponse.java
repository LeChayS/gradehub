package com.doanjava.gradehub.dto;

import com.doanjava.gradehub.entity.NguoiDung.VaiTro;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(
    String token,
    String email,
    String hoTen,
    VaiTro vaiTro,
    String maSinhVien,
    String maGiangVien,
    String maNganh,
    String tenNganh
) {
    @JsonCreator
    public LoginResponse(
        @JsonProperty("token") String token,
        @JsonProperty("email") String email,
        @JsonProperty("hoTen") String hoTen,
        @JsonProperty("vaiTro") VaiTro vaiTro,
        @JsonProperty("maSinhVien") String maSinhVien,
        @JsonProperty("maGiangVien") String maGiangVien,
        @JsonProperty("maNganh") String maNganh,
        @JsonProperty("tenNganh") String tenNganh
    ) {
        this.token = token;
        this.email = email;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
        this.maSinhVien = maSinhVien;
        this.maGiangVien = maGiangVien;
        this.maNganh = maNganh;
        this.tenNganh = tenNganh;
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token không được để trống");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        if (vaiTro == null) {
            throw new IllegalArgumentException("Vai trò không được để trống");
        }
    }
}
