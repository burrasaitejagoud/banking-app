package Hsbc.Lsk.Banking_app.Repository;

import Hsbc.Lsk.Banking_app.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
