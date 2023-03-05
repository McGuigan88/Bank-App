package com.bank.entities;

import java.util.Objects;

public class Checking {
    private int checkingAccountId;
    private double checkingBalance;

    private int userId;

    public Checking() {
    }

    public Checking(int checkingAccountId, double checkingBalance) {
        this.checkingAccountId = checkingAccountId;
        this.checkingBalance = checkingBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCheckingAccountId() {
        return checkingAccountId;
    }

    public void setCheckingAccountId(int checkingAccountId) {
        this.checkingAccountId = checkingAccountId;
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public void setCheckingBalance(double checkingBalance) {
        this.checkingBalance = checkingBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checking checking)) return false;
        return getCheckingAccountId() == checking.getCheckingAccountId() && Double.compare(checking.getCheckingBalance(), getCheckingBalance()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCheckingAccountId(), getCheckingBalance());
    }

    @Override
    public String toString() {
        return "Checking{" +
                "checkingAccountId=" + checkingAccountId +
                ", checkingBalance=" + checkingBalance +
                '}';
    }
}
