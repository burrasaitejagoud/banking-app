package Hsbc.Lsk.Banking_app.Service;

import Hsbc.Lsk.Banking_app.Dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(long id, double amount);

    AccountDto withdraw(Long id, Double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);


}
