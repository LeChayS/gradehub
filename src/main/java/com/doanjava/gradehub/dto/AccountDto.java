package com.doanjava.gradehub.dto;

import com.doanjava.gradehub.entity.NguoiDung;
import java.time.LocalDateTime;

public record AccountDto(
    Integer id,
    String email,
    String vaiTro,
    LocalDateTime ngayTao
) {
    public static AccountDto fromEntity(NguoiDung nguoiDung) {
        return new AccountDto(
            nguoiDung.getId(),
            nguoiDung.getEmail(),
            nguoiDung.getVaiTro().name(),
            nguoiDung.getNgayTao()
        );
    }
}
