package com.pismo.paymentpractice.repository.entity;

import com.pismo.paymentpractice.domain.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "accounts")
public class AccountModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 8753769176608953659L;

    @Id
    String id;
    String documentNumber;


    public AccountModel() {
    }

    public AccountModel(String id, String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
    }

    public static AccountModel toAccountModel(Account account) {
        return new AccountModel(account.id(), account.documentNumber());
    }

    public Account toAccount() {
        return new Account(this.getId(), this.getDocumentNumber());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "id='" + id + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                '}';
    }
}