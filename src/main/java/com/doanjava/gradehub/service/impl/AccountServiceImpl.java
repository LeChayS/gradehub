package com.doanjava.gradehub.service.impl;

import com.doanjava.gradehub.dto.AccountDto;
import com.doanjava.gradehub.dto.CreateAccountRequest;
import com.doanjava.gradehub.dto.UpdateAccountRequest;
import com.doanjava.gradehub.entity.NguoiDung;
import com.doanjava.gradehub.repository.NguoiDungRepository;
import com.doanjava.gradehub.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public List<AccountDto> getAllAccounts(String search, String vaiTro) {
        NguoiDung.VaiTro vaiTroEnum = null;
        if (vaiTro != null && !vaiTro.trim().isEmpty()) {
            try {
                vaiTroEnum = NguoiDung.VaiTro.valueOf(vaiTro.trim());
            } catch (IllegalArgumentException e) {
                // Nếu vai trò không hợp lệ, trả về tất cả
            }
        }

        String searchTerm = (search != null && !search.trim().isEmpty()) ? search.trim() : null;

        List<NguoiDung> nguoiDungs = nguoiDungRepository.findBySearchAndVaiTro(searchTerm, vaiTroEnum);

        return nguoiDungs.stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(Integer id) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản với ID: " + id));
        return AccountDto.fromEntity(nguoiDung);
    }

    @Override
    public AccountDto createAccount(CreateAccountRequest request) {
        // Kiểm tra email đã tồn tại chưa
        if (nguoiDungRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email đã tồn tại: " + request.email());
        }

        // Kiểm tra vai trò hợp lệ
        NguoiDung.VaiTro vaiTro;
        try {
            vaiTro = NguoiDung.VaiTro.valueOf(request.vaiTro());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + request.vaiTro() + ". Các vai trò hợp lệ: sinh_vien, giang_vien, quan_tri");
        }

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setEmail(request.email());
        nguoiDung.setMatKhauHash(request.password()); // Trong thực tế nên hash password
        nguoiDung.setVaiTro(vaiTro);

        try {
            NguoiDung savedNguoiDung = nguoiDungRepository.save(nguoiDung);
            return AccountDto.fromEntity(savedNguoiDung);
        } catch (Exception e) {
            throw new IllegalArgumentException("Lỗi khi lưu tài khoản: " + e.getMessage());
        }
    }

    @Override
    public AccountDto updateAccount(Integer id, UpdateAccountRequest request) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản với ID: " + id));

        // Kiểm tra email đã tồn tại chưa (nếu thay đổi email)
        if (!nguoiDung.getEmail().equals(request.email()) &&
            nguoiDungRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email đã tồn tại: " + request.email());
        }

        // Kiểm tra vai trò hợp lệ
        NguoiDung.VaiTro vaiTro;
        try {
            vaiTro = NguoiDung.VaiTro.valueOf(request.vaiTro());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + request.vaiTro());
        }

        nguoiDung.setEmail(request.email());
        nguoiDung.setVaiTro(vaiTro);

        NguoiDung updatedNguoiDung = nguoiDungRepository.save(nguoiDung);
        return AccountDto.fromEntity(updatedNguoiDung);
    }

    @Override
    public void deleteAccount(Integer id) {
        if (!nguoiDungRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản với ID: " + id);
        }
        nguoiDungRepository.deleteById(id);
    }

    @Override
    public void resetPassword(Integer id, String newPassword) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản với ID: " + id));

        if (newPassword == null || newPassword.trim().length() < 6) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
        }

        nguoiDung.setMatKhauHash(newPassword.trim()); // Trong thực tế nên hash password
        nguoiDungRepository.save(nguoiDung);
    }
}
