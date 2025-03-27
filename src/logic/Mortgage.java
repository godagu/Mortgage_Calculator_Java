package logic;

// Abstract class for mortgage logic
public abstract class Mortgage {
    protected double mortgageAmount;
    protected int totalTermMonths;
    protected double monthlyInterestRate;
    protected double[] monthlyPayments;

    public Mortgage(double mortgageAmount, int termYears, int termMonths, double annualInterestRate) {
        this.mortgageAmount = mortgageAmount;
        this.monthlyInterestRate = calcMonthlyInterestRate(annualInterestRate);
        this.totalTermMonths = convertToMonths(termYears, termMonths);
    }

    private int convertToMonths(int years, int months) {
        return (years * 12) + months;
    }


    private double calcMonthlyInterestRate(double annualInterestRate) {
        return (annualInterestRate / 100) / 12;
    }

    public abstract void calcMonthlyPayments();

    public double getMonthlyPayment(int month) {
        if (month < 1 || month - 1 >= monthlyPayments.length) {
            throw new IllegalArgumentException("Month out of range");
        }

        return roundToTwoDecimals(monthlyPayments[month - 1]);


    }

    public double calcRemainingBalance(int month) {
        if (month < 1 || month - 1 >= monthlyPayments.length) {
            throw new IllegalArgumentException("Month out of range");
        }

        double toBePaid = 0;

        for(int i = month + 1; i <= totalTermMonths; ++i) {
            toBePaid += monthlyPayments[i - 1];
        }



        return roundToTwoDecimals(toBePaid);

    }

    public abstract double getPrincipal(int month);
    public abstract double getMonthlyInterestRate(int month);

    protected double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    public double[] getMonthlyPayments() {
        return this.monthlyPayments;
    }

}
