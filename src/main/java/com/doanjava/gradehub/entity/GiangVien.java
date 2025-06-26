package com.doanjava.gradehub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "giang_vien")
@Data
public class GiangVien {

    @Id
    @Column(name = "ma_giang_vien", length = 20)
    @jakarta.validation.constraints.NotBlank(message = "Mã giảng viên không được để trống")
    private String maGiangVien;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", unique = true, nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "ho_ten", length = 100)
    @jakarta.validation.constraints.NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @Column(name = "bo_mon", length = 100)
    @jakarta.validation.constraints.NotBlank(message = "Bộ môn không được để trống")
    private String boMon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nganh", nullable = false)
    private Nganh nganh;
}
