package com.binobank.digitalwallet.model;

import java.util.ArrayList;

public class User {

    private static User INSTANCE;
    private float balance;
    private final ArrayList<Statement> userStatements;

    private User() {
        this.balance = 0;
        this.userStatements = new ArrayList<Statement>();
    }

    public static User getUser() {
        if (INSTANCE == null) {
            INSTANCE = new User();
        }

        return INSTANCE;
    }

    public float getBalance() {
        return this.balance;
    }

    public ArrayList<Statement> getUserStatements() {
        return this.userStatements;
    }

    public void updateBalance(float amount) {
        this.balance += amount;
    }

    public void addStatement(Statement statement) {
        this.userStatements.add(statement);
    }

    public void clearStatements() {
        this.userStatements.clear();
    }
}
