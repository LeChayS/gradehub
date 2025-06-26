package com.doanjava.gradehub.service.impl;

import com.doanjava.gradehub.dto.LoginRequest;
import com.doanjava.gradehub.dto.LoginResponse;
import com.doanjava.gradehub.dto.RegisterRequest;
import com.doanjava.gradehub.entity.*;
import com.doanjava.gradehub.repository.*;
import com.doanjava.gradehub.service.AuthService;
import com.doanjava.gradehub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private GiangVienRepository giangVienRepository;

    @Autowired
    private NganhRepository nganhRepository;

    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // Find user by email
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("Email hoặc mật khẩu không đúng"));

        // Check password đúng chuẩn: dùng passwordEncoder.matches
        if (!passwordEncoder.matches(loginRequest.password(), nguoiDung.getMatKhauHash())) {
            throw new IllegalArgumentException("Email hoặc mật khẩu không đúng");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(nguoiDung.getEmail(), nguoiDung.getVaiTro().name());

        // Get additional user info based on role
        String hoTen = "";
        String maSinhVien = null;
        String maGiangVien = null;
        String maNganh = null;
        String tenNganh = null;

        switch (nguoiDung.getVaiTro()) {
            case sinh_vien:
                SinhVien sinhVien = sinhVienRepository.findByNguoiDungId(nguoiDung.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Thông tin sinh viên không tìm thấy"));
                hoTen = sinhVien.getHoTen();
                maSinhVien = sinhVien.getMaSinhVien();
                maNganh = sinhVien.getNganh().getMaNganh();
                tenNganh = sinhVien.getNganh().getTenNganh();
                break;
            case giang_vien:
                GiangVien giangVien = giangVienRepository.findByNguoiDungId(nguoiDung.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Thông tin giảng viên không tìm thấy"));
                hoTen = giangVien.getHoTen();
                maGiangVien = giangVien.getMaGiangVien();
                maNganh = giangVien.getNganh().getMaNganh();
                tenNganh = giangVien.getNganh().getTenNganh();
                break;
            case quan_tri:
                hoTen = "Quản trị viên";
                break;
        }

        return new LoginResponse(token, nguoiDung.getEmail(), hoTen, nguoiDung.getVaiTro(),
                               maSinhVien, maGiangVien, maNganh, tenNganh);
    }

    @Override
    @Transactional
    public LoginResponse register(RegisterRequest registerRequest) {
        // Check if email already exists
        if (nguoiDungRepository.existsByEmail(registerRequest.email())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        // Create new user
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setEmail(registerRequest.email());
        nguoiDung.setMatKhauHash(passwordEncoder.encode(registerRequest.password()));
        nguoiDung.setVaiTro(registerRequest.vaiTro());

        nguoiDung = nguoiDungRepository.save(nguoiDung);

        // Create role-specific entity
        switch (registerRequest.vaiTro()) {
            case sinh_vien:
                if (registerRequest.maSinhVien() == null || registerRequest.maSinhVien().trim().isEmpty()) {
                    throw new IllegalArgumentException("Mã sinh viên không được để trống");
                }
                if (sinhVienRepository.existsByMaSinhVien(registerRequest.maSinhVien())) {
                    throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
                }

                Nganh nganh = nganhRepository.findByMaNganh(registerRequest.maNganh())
                        .orElseThrow(() -> new IllegalArgumentException("Ngành không tồn tại"));

                SinhVien sinhVien = new SinhVien();
                sinhVien.setMaSinhVien(registerRequest.maSinhVien());
                sinhVien.setNguoiDung(nguoiDung);
                sinhVien.setHoTen(registerRequest.hoTen());
                sinhVien.setNganh(nganh);

                if (registerRequest.maChuyenNganh() != null && !registerRequest.maChuyenNganh().trim().isEmpty()) {
                    ChuyenNganh chuyenNganh = chuyenNganhRepository.findByMaChuyenNganh(registerRequest.maChuyenNganh())
                            .orElseThrow(() -> new IllegalArgumentException("Chuyên ngành không tồn tại"));
                    sinhVien.setChuyenNganh(chuyenNganh);
                }

                sinhVienRepository.save(sinhVien);
                break;

            case giang_vien:
                if (registerRequest.maGiangVien() == null || registerRequest.maGiangVien().trim().isEmpty()) {
                    throw new IllegalArgumentException("Mã giảng viên không được để trống");
                }
                if (giangVienRepository.existsByMaGiangVien(registerRequest.maGiangVien())) {
                    throw new IllegalArgumentException("Mã giảng viên đã tồn tại");
                }

                Nganh nganhGV = nganhRepository.findByMaNganh(registerRequest.maNganh())
                        .orElseThrow(() -> new IllegalArgumentException("Ngành không tồn tại"));

                GiangVien giangVien = new GiangVien();
                giangVien.setMaGiangVien(registerRequest.maGiangVien());
                giangVien.setNguoiDung(nguoiDung);
                giangVien.setHoTen(registerRequest.hoTen());
                giangVien.setBoMon(registerRequest.boMon());
                giangVien.setNganh(nganhGV);

                giangVienRepository.save(giangVien);
                break;

            case quan_tri:
                // Admin registration logic can be added here if needed
                break;
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(nguoiDung.getEmail(), nguoiDung.getVaiTro().name());

        return new LoginResponse(token, nguoiDung.getEmail(), registerRequest.hoTen(),
                               nguoiDung.getVaiTro(), registerRequest.maSinhVien(),
                               registerRequest.maGiangVien(), registerRequest.maNganh(), null);
    }
}
