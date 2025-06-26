package com.doanjava.gradehub.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "chuyen_nganh")
@Data
public class ChuyenNganh {

    @Id
    @Column(name = "ma_chuyen_nganh", length = 10)
    @jakarta.validation.constraints.NotBlank(message = "Mã chuyên ngành không được để trống")
    private String maChuyenNganh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_nganh", nullable = false)
    private Nganh nganh;

    @Column(name = "ten_chuyen_nganh", length = 100)
    @jakarta.validation.constraints.NotBlank(message = "Tên chuyên ngành không được để trống")
    private String tenChuyenNganh;

    @OneToMany(mappedBy = "chuyenNganh", fetch = FetchType.LAZY)
    private List<SinhVien> sinhViens;
}
