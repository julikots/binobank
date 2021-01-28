package com.binobank.digitalwallet.model;

import com.binobank.digitalwallet.annotation.EntryDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Statement {

    public enum TransactionType {
        PIX,
        CARD
    };

    public enum Type {
        CREDIT,
        DEBIT
    };

    @NotEmpty
    @NotNull
    private String transactionId;

    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    private TransactionType transactionType;

    @NotEmpty
    @NotNull
    @EntryDate
    private String entryDate;

    @NotNull
    private float amount;

    @NotNull
    private Type type;

    private boolean visited;

    public Statement(String transactionId, String description, TransactionType transactionType, String entryDate, float amount, Type type) {
        this.transactionId = transactionId;
        this.description = description;
        this.transactionType = transactionType;
        this.entryDate = entryDate;
        this.amount = amount;
        this.type = type;
        this.visited = false;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
