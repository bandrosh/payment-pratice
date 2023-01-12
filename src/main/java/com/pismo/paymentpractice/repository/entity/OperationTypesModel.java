package com.pismo.paymentpractice.repository.entity;

import com.pismo.paymentpractice.domain.OperationsType;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "operations_type")
public class OperationTypesModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -36527966273728454L;

    @Id
    Integer id;
    @Enumerated(EnumType.STRING)
    OperationsType description;

    public OperationTypesModel() {
    }

    public OperationTypesModel(Integer id, OperationsType description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OperationsType getDescription() {
        return description;
    }

    public void setDescription(OperationsType description) {
        this.description = description;
    }
}
