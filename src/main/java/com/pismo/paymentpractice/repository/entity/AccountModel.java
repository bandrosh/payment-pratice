package com.pismo.paymentpractice.repository.entity;

import com.pismo.paymentpractice.domain.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountModel {
    @Id
    String id;
    String documentNumber;


    public AccountModel() {
    }

    public AccountModel(String id, String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
    }

    public AccountModel toAccountModel(Account account) {
        return new AccountModel(account.id(), account.documentNumber());
    }

    public Account toAccount(AccountModel accountModel) {
        return new Account(accountModel.getId(), accountModel.getDocumentNumber());
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