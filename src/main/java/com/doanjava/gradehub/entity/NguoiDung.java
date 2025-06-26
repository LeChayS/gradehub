package com.doanjava.gradehub.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    @jakarta.validation.constraints.Email(message = "Email không hợp lệ")
    @jakarta.validation.constraints.NotBlank(message = "Email không được để trống")
    private String email;

    @Column(name = "mat_khau_hash", nullable = false, length = 255)
    @jakarta.validation.constraints.NotBlank(message = "Mật khẩu không được để trống")
    @jakarta.validation.constraints.Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matKhauHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "vai_tro", nullable = false)
    private VaiTro vaiTro;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    public enum VaiTro {
        sinh_vien,
        giang_vien,
        quan_tri
    }

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
    }
}
