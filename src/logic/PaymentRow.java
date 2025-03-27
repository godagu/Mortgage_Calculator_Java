package logic;

import javafx.beans.property.*;

// Class for handling all "row" information (related to monthly payments), later used to create table, graph and report
public class PaymentRow {
    private final IntegerProperty month;
    private final DoubleProperty totalPayment;
    private final DoubleProperty principal;
    private final DoubleProperty interest;
    private final DoubleProperty balance;

    public PaymentRow(int month, double totalPayment, double principal, double interest, double balance) {
        this.month = new SimpleIntegerProperty(month);
        this.totalPayment = new SimpleDoubleProperty(totalPayment);
        this.principal = new SimpleDoubleProperty(principal);
        this.interest = new SimpleDoubleProperty(interest);
        this.balance = new SimpleDoubleProperty(balance);
    }

    public IntegerProperty monthProperty() {
        return month;
    }

    public DoubleProperty totalPaymentProperty() {
        return totalPayment;
    }

    public DoubleProperty principalProperty() {
        return principal;
    }

    public DoubleProperty interestProperty() {
        return interest;
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public int getMonth() {
        return month.get();
    }

    public double getTotalPayment() {
        return totalPayment.get();
    }

    public double getPrincipal() {
        return principal.get();
    }

    public double getInterest() {
        return interest.get();
    }

    public double getRemainingBalance() {
        return balance.get();
    }


}
