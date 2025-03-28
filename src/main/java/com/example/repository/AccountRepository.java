package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

/**
 * AccountRepository manages the Account table in the database.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
    Account findByUsername(String username);
}
