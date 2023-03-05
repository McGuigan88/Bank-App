package com.bank.entities;

import java.util.Objects;

public class Savings {
    private int savingsAccountId;
    private double savingsBalance;

    private int userId;

    public Savings() {
    }

    public Savings(int savingsAccountId, double savingsBalance) {
        this.savingsAccountId = savingsAccountId;
        this.savingsBalance = savingsBalance;
    }

    public int getSavingsAccountId() {
        return savingsAccountId;
    }

    public void setSavingsAccountId(int savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsBalance(double savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Savings savings)) return false;
        return getSavingsAccountId() == savings.getSavingsAccountId() && Double.compare(savings.getSavingsBalance(), getSavingsBalance()) == 0 && getUserId() == savings.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSavingsAccountId(), getSavingsBalance(), getUserId());
    }

    @Override
    public String toString() {
        return "Savings{" +
                "savingsAccountId=" + savingsAccountId +
                ", savingsBalance=" + savingsBalance +
                ", userId=" + userId +
                '}';
    }
}
