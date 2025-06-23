package Hsbc.Lsk.Banking_app.RepositoryTests;

import Hsbc.Lsk.Banking_app.Entity.Account;
import Hsbc.Lsk.Banking_app.Repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Save Account and find by ID")
    void testSaveAndFindById() {
        // Given
        Account account = new Account();
        account.setAccountHolderName("John Doe");
        account.setBalance(1000.0);

        // When
        Account savedAccount = accountRepository.save(account);

        // Then
        Optional<Account> fetchedAccount = accountRepository.findById(savedAccount.getId());
        assertTrue(fetchedAccount.isPresent());
        assertEquals("John Doe", fetchedAccount.get().getAccountHolderName());
        assertEquals(1000.0, fetchedAccount.get().getBalance());
    }

    @Test
    @DisplayName("Delete Account by ID")
    void testDeleteById() {
        Account account = new Account();
        account.setAccountHolderName("Jane");
        account.setBalance(5000.0);
        Account saved = accountRepository.save(account);

        accountRepository.deleteById(saved.getId());

        Optional<Account> deleted = accountRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @DisplayName("Find All Accounts")
    void testFindAll() {
        Account acc1 = new Account();
        acc1.setAccountHolderName("User1");
        acc1.setBalance(1000.0);

        Account acc2 = new Account();
        acc2.setAccountHolderName("User2");
        acc2.setBalance(2000.0);

        accountRepository.save(acc1);
        accountRepository.save(acc2);

        assertEquals(2, accountRepository.findAll().size());
    }
}
