package com.pismo.paymentpractice.repository;

import com.pismo.paymentpractice.repository.entity.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
}
