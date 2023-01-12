package com.pismo.paymentpractice.repository;

import com.pismo.paymentpractice.repository.entity.OperationTypesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsTypeRepository extends JpaRepository<OperationTypesModel, Integer> {
}
