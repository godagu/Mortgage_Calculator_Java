package logic;


// Child class of Mortgage, handles Linear Mortgage logic
public class Linear extends Mortgage{

    private double fixedPrincipalPayment;
    private double[] monthlyInterestRates;

    public Linear(double mortgageAmount, int termYears, int termMonths, double annualInterestRate) {
        super(mortgageAmount, termYears, termMonths, annualInterestRate);
        this.calcMonthlyPayments();
    }

    public void calcMonthlyPayments() {
        // Fixed principal payment + varying monthly interest rate
        monthlyPayments = new double[totalTermMonths];
        monthlyInterestRates = new double[totalTermMonths];

        fixedPrincipalPayment = mortgageAmount / totalTermMonths;

        for (int i = 1; i <= totalTermMonths; ++i) {
            double remainingPrincipal = mortgageAmount - (fixedPrincipalPayment * i);
            monthlyInterestRates[i - 1] = remainingPrincipal * monthlyInterestRate;

            monthlyPayments[i - 1] = fixedPrincipalPayment + monthlyInterestRates[i - 1];
        }
    }

    @Override
    public double getPrincipal(int month) {
        return roundToTwoDecimals(fixedPrincipalPayment);
    }

    @Override
    public double getMonthlyInterestRate(int month) {
        return roundToTwoDecimals(monthlyInterestRates[month - 1]);
    }


}
