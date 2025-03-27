package logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

// Class that handles calculations
public class Calculation {
    private Mortgage mortgage;
    private int startMonth;
    private int endMonth;


    public ObservableList<PaymentRow> calculatePayments(String loanAmountS, String annualInterestS, String termYearsS,
                                                         String termMonthsS, String mortgageTypeS, String startMonthS, String endMonthS,
                                                         String deferralStartS, String deferralDurationS, String deferralInterestS){
        try{
            ObservableList<PaymentRow> paymentRows;
            double loanAmount = Double.parseDouble(loanAmountS);
            double annualInterest = Double.parseDouble(annualInterestS);
            int termYears = Integer.parseInt(termYearsS);

            int termMonths;
            if(termMonthsS.isEmpty()){
                termMonths = 0;
            }
            else {
                termMonths = Integer.parseInt(termMonthsS);
            }


            if(mortgageTypeS.equals("Linear")){
                mortgage = new Linear(loanAmount, termYears, termMonths, annualInterest);
            }
            else{
                mortgage = new Annuity(loanAmount, termYears, termMonths, annualInterest);
            }

            mortgage.calcMonthlyPayments();

            if(!startMonthS.isEmpty() && !endMonthS.isEmpty()){
                startMonth = Integer.parseInt(startMonthS);
                endMonth = Integer.parseInt(endMonthS);

                if(startMonth > mortgage.getMonthlyPayments().length || endMonth > mortgage.getMonthlyPayments().length) {
                    new Alert(Alert.AlertType.ERROR, "Start and end months must be within range.").showAndWait();
                    return null;
                }
            }
            else{
                startMonth = 1;
                endMonth = mortgage.getMonthlyPayments().length;
            }

            paymentRows = FXCollections.observableArrayList();

            //double remainingBalance = loanAmount;

            // if deferral is added
            if(!deferralStartS.isEmpty() && !deferralDurationS.isEmpty() && !deferralInterestS.isEmpty()){
                int deferralStart = Integer.parseInt(deferralStartS);
                int deferralDuration = Integer.parseInt(deferralDurationS);
                double deferralInterestRate = Double.parseDouble(deferralInterestS);

                if(deferralStart > mortgage.getMonthlyPayments().length){
                    new Alert(Alert.AlertType.ERROR, "Deferral start date cannot be later than the end of term").showAndWait();
                    return null;
                }

                return calcDeferral(deferralStart, deferralDuration, deferralInterestRate, loanAmount, paymentRows);
            }
            // no deferral
            else{
                return calcRegular(paymentRows);
            }

        } catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
            return null; // ??
        }

    }


    // If no deferral was added
    private ObservableList<PaymentRow> calcRegular(ObservableList<PaymentRow> paymentRows){
        for(int month = startMonth; month <= endMonth; ++month){
            double totalPayment = mortgage.getMonthlyPayment(month);
            double principal = mortgage.getPrincipal(month);
            double interest = mortgage.getMonthlyInterestRate(month);
            double remainingBalance = mortgage.calcRemainingBalance(month);

            paymentRows.add(new PaymentRow(month, totalPayment, principal, interest, remainingBalance));
        }

        return paymentRows;
    }

    // If deferral was added
    private ObservableList<PaymentRow> calcDeferral(int deferralStart, int deferralDuration, double deferralInterestRate, double remainingBalance, ObservableList<PaymentRow> paymentRows){
        for (int month = startMonth; month < deferralStart; month++) {
            double totalPayment = mortgage.getMonthlyPayment(month);
            double principal = mortgage.getPrincipal(month);
            double interest = mortgage.getMonthlyInterestRate(month);
            remainingBalance = mortgage.calcRemainingBalance(month);

            paymentRows.add(new PaymentRow(month, totalPayment, principal, interest, remainingBalance));
        }

        // During deferral
        for(int month = deferralStart; month < deferralStart + deferralDuration; ++month){
            double deferralInterest = remainingBalance * (deferralInterestRate / 100 / 12);
            deferralInterest = Math.round(deferralInterest * 100.0) / 100.0;
            remainingBalance += deferralInterest;
            paymentRows.add(new PaymentRow(month, deferralInterest, 0, deferralInterest, remainingBalance));

        }

        // After deferral
        for(int month = deferralStart + deferralDuration; month <= deferralDuration + endMonth; ++month){
            double totalPayment = mortgage.getMonthlyPayment(month - deferralDuration);
            double principal = mortgage.getPrincipal(month - deferralDuration);
            double interest = mortgage.getMonthlyInterestRate(month - deferralDuration);
            remainingBalance = mortgage.calcRemainingBalance(month - deferralDuration);

            paymentRows.add(new PaymentRow(month, totalPayment, principal, interest, remainingBalance));
        }

        return paymentRows;
    }

    public int getStartMonth(){
        return startMonth;
    }

    public int getEndMonth(){
        return endMonth;
    }

}
