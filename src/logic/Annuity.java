package logic;

import java.util.Arrays;

// Child class of Mortgage for Annuity mortgage logic
public class Annuity extends Mortgage{
    private double[] monthlyInterestRates;

    public Annuity(double mortgageAmount, int termYears, int termMonths, double annualInterestRate) {
        super(mortgageAmount, termYears, termMonths, annualInterestRate);
        this.calcMonthlyPayments();
        this.calcMonthlyInterestRates();
    }


    public void calcMonthlyPayments() {
        monthlyPayments = new double[totalTermMonths];
        double payment = this.mortgageAmount * (this.monthlyInterestRate / (1 - Math.pow(1 + this.monthlyInterestRate, - this.totalTermMonths)));
        Arrays.fill(monthlyPayments, payment);
    }

    public void calcMonthlyInterestRates(){
        monthlyInterestRates = new double[totalTermMonths];
        double remainingBalance = this.mortgageAmount;
        for (int i = 1; i < totalTermMonths; ++ i) {
            double interest = remainingBalance * this.monthlyInterestRate;
            monthlyInterestRates[i - 1] = interest;

            double principal = this.monthlyPayments[0] - interest;
            remainingBalance -= principal;
        }
    }

    @Override
    public double getPrincipal(int month) {
        return roundToTwoDecimals(monthlyPayments[0] - monthlyInterestRates[month - 1]);
    }

    @Override
    public double getMonthlyInterestRate(int month) {
        return roundToTwoDecimals(monthlyInterestRates[month - 1]);
    }



}
