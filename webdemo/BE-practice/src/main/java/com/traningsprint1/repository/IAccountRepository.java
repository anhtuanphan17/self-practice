package com.traningsprint1.repository;

import com.traningsprint1.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * IAccountRepository
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
public interface IAccountRepository extends JpaRepository<Account, Long> {
    /**
     * this findByUserName is to find account by given param username
     * @Version: 20-sept-2022
     * @Author: TuanPA3
     * */
    @Query(value = "SELECT * FROM account WHERE account.user_name = :username",
            countQuery = "SELECT * FROM account WHERE account.user_name = :username", nativeQuery = true)
    Account findByUserName(@Param("username") String username);

    boolean existsAccountByEmail(String email);

    Account getAccountByEmail(String email);
}
