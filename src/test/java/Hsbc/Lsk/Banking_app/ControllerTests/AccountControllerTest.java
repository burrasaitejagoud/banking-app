package Hsbc.Lsk.Banking_app.ControllerTests;

import Hsbc.Lsk.Banking_app.Controller.AccountController;
import Hsbc.Lsk.Banking_app.Dto.AccountDto;
import Hsbc.Lsk.Banking_app.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAccount() {
        AccountDto input = new AccountDto(1L, "John", 100.0);
        when(accountService.createAccount(any())).thenReturn(input);

        ResponseEntity<AccountDto> response = accountController.addAccount(input);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(input, response.getBody());
    }

    @Test
    void testGetAccountById() {
        AccountDto dto = new AccountDto(1L, "John", 100.0);
        when(accountService.getAccountById(1L)).thenReturn(dto);

        ResponseEntity<AccountDto> response = accountController.getAccountById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testDeposit() {
        AccountDto updated = new AccountDto(1L, "John", 150.0);
        when(accountService.deposit(1L, 50.0)).thenReturn(updated);

        Map<String, Double> request = new HashMap<>();
        request.put("amount", 50.0);

        ResponseEntity<AccountDto> response = accountController.deposit(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testWithdraw() {
        AccountDto updated = new AccountDto(1L, "John", 50.0);
        when(accountService.withdraw(1L, 50.0)).thenReturn(updated);

        Map<String, Double> request = new HashMap<>();
        request.put("amount", 50.0);

        ResponseEntity<AccountDto> response = accountController.withdraw(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testGetAllAccounts() {
        List<AccountDto> accounts = List.of(
                new AccountDto(1L, "John", 100.0),
                new AccountDto(2L, "Jane", 200.0)
        );
        when(accountService.getAllAccounts()).thenReturn(accounts);

        ResponseEntity<List<AccountDto>> response = accountController.getAllAccounts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(accounts, response.getBody());
    }

    @Test
    void testDeleteAccount() {
        doNothing().when(accountService).deleteAccount(1L);

        ResponseEntity<String> response = accountController.deleteAccount(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Account deleted Succsessfully", response.getBody());
    }
}
