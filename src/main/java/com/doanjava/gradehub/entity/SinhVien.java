package com.doanjava.gradehub.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "sinh_vien")
@Data
public class SinhVien {

    @Id
    @Column(name = "ma_sinh_vien", length = 20)
    @jakarta.validation.constraints.NotBlank(message = "Mã sinh viên không được để trống")
    private String maSinhVien;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", unique = true, nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "ho_ten", length = 100)
    @jakarta.validation.constraints.NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh")
    private GioiTinh gioiTinh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nganh", nullable = false)
    private Nganh nganh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_chuyen_nganh")
    private ChuyenNganh chuyenNganh;

    public enum GioiTinh {
        nam,
        nu,
        khac
    }
}
