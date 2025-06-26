package com.doanjava.gradehub.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "nganh")
@Data
public class Nganh {

    @Id
    @Column(name = "ma_nganh", length = 10)
    @jakarta.validation.constraints.NotBlank(message = "Mã ngành không được để trống")
    private String maNganh;

    @Column(name = "ten_nganh", unique = true, nullable = false, length = 100)
    @jakarta.validation.constraints.NotBlank(message = "Tên ngành không được để trống")
    private String tenNganh;

    @OneToMany(mappedBy = "nganh", fetch = FetchType.LAZY)
    private List<ChuyenNganh> chuyenNganhs;

    @OneToMany(mappedBy = "nganh", fetch = FetchType.LAZY)
    private List<SinhVien> sinhViens;

    @OneToMany(mappedBy = "nganh", fetch = FetchType.LAZY)
    private List<GiangVien> giangViens;
}
