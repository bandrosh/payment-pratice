package com.pismo.paymentpractice.repository.entity;

import com.pismo.paymentpractice.domain.Transaction;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transactions_id_seq",
            allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountModel account;

    private Integer operationTypeId;

    private Double amount;

    @CreationTimestamp
    @Column
    private Timestamp eventDate;

    public TransactionModel() {
    }

    public TransactionModel(Integer operationTypeId, Double amount) {
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    public static TransactionModel toTransactionModel(Transaction transaction) {
        return new TransactionModel(transaction.operationTypeId(), transaction.amount());
    }

    public Transaction toTransaction() {
        return new Transaction(this.getAccount().getId(), this.getOperationTypeId(), this.getAmount());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getEventDate() {
        return eventDate;
    }

    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "account=" + account +
                ", operationTypeId=" + operationTypeId +
                ", amount=" + amount +
                ", eventDate=" + eventDate +
                '}';
    }
}
