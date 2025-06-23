package Hsbc.Lsk.Banking_app.ServiceTests;

import Hsbc.Lsk.Banking_app.Dto.AccountDto;
import Hsbc.Lsk.Banking_app.Entity.Account;
import Hsbc.Lsk.Banking_app.Exception.AccountException;
import Hsbc.Lsk.Banking_app.Repository.AccountRepository;
import Hsbc.Lsk.Banking_app.Service.AccountService;
import Hsbc.Lsk.Banking_app.Service.Impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void testCreateAccount() {
        AccountDto dto = new AccountDto(1L, "John", 1000.0);
        Account entity = new Account(1L, "John", 1000.0);

        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(entity);

        AccountDto created = accountService.createAccount(dto);

        assertEquals("John", created.getAccountHolderName());
        assertEquals(1000.0, created.getBalance());
    }

    @Test
    void testGetAccountById_Success() {
        Account account = new Account(1L, "Alice", 500.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        AccountDto dto = accountService.getAccountById(1L);
        assertEquals("Alice", dto.getAccountHolderName());
        assertEquals(500.0, dto.getBalance());
    }

    @Test
    void testGetAccountById_NotFound() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AccountException.class, () -> accountService.getAccountById(99L));
    }

    @Test
    void testDeposit_Success() {
        Account existing = new Account(1L, "John", 1000.0);
        Account updated = new Account(1L, "John", 1200.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(accountRepository.save(any(Account.class))).thenReturn(updated);

        AccountDto result = accountService.deposit(1L, 200.0);
        assertEquals(1200.0, result.getBalance());
    }

    @Test
    void testWithdraw_Success() {
        Account existing = new Account(1L, "John", 1000.0);
        Account updated = new Account(1L, "John", 700.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(accountRepository.save(any(Account.class))).thenReturn(updated);

        AccountDto result = accountService.withdraw(1L, 300.0);
        assertEquals(700.0, result.getBalance());
    }

    @Test
    void testWithdraw_InsufficientFunds() {
        Account existing = new Account(1L, "John", 200.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(RuntimeException.class, () -> accountService.withdraw(1L, 300.0));
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accountList = Arrays.asList(
                new Account(1L, "User1", 1000.0),
                new Account(2L, "User2", 2000.0)
        );

        when(accountRepository.findAll()).thenReturn(accountList);

        List<AccountDto> result = accountService.getAllAccounts();
        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getAccountHolderName());
        assertEquals(2000.0, result.get(1).getBalance());
    }

    @Test
    void testDeleteAccount_Success() {
        Account account = new Account(1L, "John", 1000.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.deleteAccount(1L);
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAccount_NotFound() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AccountException.class, () -> accountService.deleteAccount(99L));
    }
}
