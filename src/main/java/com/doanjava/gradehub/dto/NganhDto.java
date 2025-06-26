package com.doanjava.gradehub.dto;

public record NganhDto(
    String maNganh,
    String tenNganh
) {
    public NganhDto {
        if (maNganh == null || maNganh.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã ngành không được để trống");
        }
        if (tenNganh == null || tenNganh.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên ngành không được để trống");
        }
    }
}
