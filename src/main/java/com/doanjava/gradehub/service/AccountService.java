package com.doanjava.gradehub.service;

import com.doanjava.gradehub.dto.AccountDto;
import com.doanjava.gradehub.dto.CreateAccountRequest;
import com.doanjava.gradehub.dto.UpdateAccountRequest;
import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccounts(String search, String vaiTro);
    AccountDto getAccountById(Integer id);
    AccountDto createAccount(CreateAccountRequest request);
    AccountDto updateAccount(Integer id, UpdateAccountRequest request);
    void deleteAccount(Integer id);
    void resetPassword(Integer id, String newPassword);
}
