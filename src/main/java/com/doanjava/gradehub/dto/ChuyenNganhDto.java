package com.doanjava.gradehub.dto;

public record ChuyenNganhDto(
    String maChuyenNganh,
    String tenChuyenNganh,
    String maNganh
) {
    public ChuyenNganhDto {
        if (maChuyenNganh == null || maChuyenNganh.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã chuyên ngành không được để trống");
        }
        if (tenChuyenNganh == null || tenChuyenNganh.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên chuyên ngành không được để trống");
        }
        if (maNganh == null || maNganh.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã ngành không được để trống");
        }
    }
}
