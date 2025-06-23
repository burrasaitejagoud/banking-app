package Hsbc.Lsk.Banking_app.Service.Impl;

import Hsbc.Lsk.Banking_app.Dto.AccountDto;
import Hsbc.Lsk.Banking_app.Entity.Account;
import Hsbc.Lsk.Banking_app.Exception.AccountException;
import Hsbc.Lsk.Banking_app.Mapper.AccountMapper;
import Hsbc.Lsk.Banking_app.Repository.AccountRepository;
import Hsbc.Lsk.Banking_app.Service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
       Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account doesnot exists"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account doesnot exists"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id , Double amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account doesnot exists"));
        if (account.getBalance()< amount){
            throw new RuntimeException("Insufficient Amount");
        }

        double total = account.getBalance()-amount;
        account.setBalance(total);
        Account savedamount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedamount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> all = accountRepository.findAll();
        return  all.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account doesnot exists"));

        accountRepository.deleteById(id);


    }
}
