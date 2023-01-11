package com.pismo.paymentpractice.repository;

import com.pismo.paymentpractice.repository.entity.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, String> {
}
