package com.doanjava.gradehub.controller;

import com.doanjava.gradehub.dto.AccountDto;
import com.doanjava.gradehub.dto.ApiResponse;
import com.doanjava.gradehub.dto.CreateAccountRequest;
import com.doanjava.gradehub.dto.UpdateAccountRequest;
import com.doanjava.gradehub.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDto>>> getAllAccounts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String vaiTro) {
        try {
            List<AccountDto> accounts = accountService.getAllAccounts(search, vaiTro);
            ApiResponse<List<AccountDto>> response = new ApiResponse<>("SUCCESS", "Lấy danh sách tài khoản thành công", accounts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<AccountDto>> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> getAccountById(@PathVariable Integer id) {
        try {
            AccountDto account = accountService.getAccountById(id);
            ApiResponse<AccountDto> response = new ApiResponse<>("SUCCESS", "Lấy thông tin tài khoản thành công", account);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<AccountDto> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<AccountDto> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDto>> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        try {
            AccountDto createdAccount = accountService.createAccount(request);
            ApiResponse<AccountDto> response = new ApiResponse<>("SUCCESS", "Tạo tài khoản thành công", createdAccount);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<AccountDto> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<AccountDto> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> updateAccount(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateAccountRequest request) {
        try {
            AccountDto updatedAccount = accountService.updateAccount(id, request);
            ApiResponse<AccountDto> response = new ApiResponse<>("SUCCESS", "Cập nhật tài khoản thành công", updatedAccount);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<AccountDto> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<AccountDto> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Integer id) {
        try {
            accountService.deleteAccount(id);
            ApiResponse<Void> response = new ApiResponse<>("SUCCESS", "Xóa tài khoản thành công", null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{id}/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @PathVariable Integer id,
            @RequestBody ResetPasswordRequest request) {
        try {
            accountService.resetPassword(id, request.getPassword());
            ApiResponse<Void> response = new ApiResponse<>("SUCCESS", "Đặt lại mật khẩu thành công", null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>("ERROR", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public static class ResetPasswordRequest {
        public String password;
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
