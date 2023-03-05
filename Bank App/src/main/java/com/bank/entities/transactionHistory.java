package com.bank.entities;

import java.util.Objects;

public class transactionHistory {
    private int transactionId;
    private double newCheckingBalance;
    private double newSavingsBalance;
    private double transferValue;
    private Long userId;

    public transactionHistory() {
    }

    public transactionHistory(int transactionId, double newCheckingBalance, double newSavingsBalance, double transferValue, Long userId) {
        this.transactionId = transactionId;
        this.newCheckingBalance = newCheckingBalance;
        this.newSavingsBalance = newSavingsBalance;
        this.transferValue = transferValue;
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getNewCheckingBalance() {
        return newCheckingBalance;
    }

    public void setNewCheckingBalance(double newCheckingBalance) {
        this.newCheckingBalance = newCheckingBalance;
    }

    public double getNewSavingsBalance() {
        return newSavingsBalance;
    }

    public void setNewSavingsBalance(double newSavingsBalance) {
        this.newSavingsBalance = newSavingsBalance;
    }

    public double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(double transferValue) {
        this.transferValue = transferValue;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof transactionHistory that)) return false;
        return getTransactionId() == that.getTransactionId() && Double.compare(that.getNewCheckingBalance(), getNewCheckingBalance()) == 0 && Double.compare(that.getNewSavingsBalance(), getNewSavingsBalance()) == 0 && Double.compare(that.getTransferValue(), getTransferValue()) == 0 && getUserId() == that.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getNewCheckingBalance(), getNewSavingsBalance(), getTransferValue(), getUserId());
    }

    @Override
    public String toString() {
        return "transactionHistory{" +
                "transactionId=" + transactionId +
                ", newCheckingBalance=" + newCheckingBalance +
                ", newSavingsBalance=" + newSavingsBalance +
                ", transferValue=" + transferValue +
                ", userId=" + userId +
                '}';
    }
}
