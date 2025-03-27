package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import logic.*;

import java.io.IOException;
import java.util.Objects;

// Main class with some helper methods and validation

public class Calculator extends Application {
    private PaymentTable paymentTable;
    private Tab graphTab;
    private ObservableList<PaymentRow> paymentRows;
    private Input layoutInput;
    private Calculation calculator;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        primaryStage.setTitle("Mortgage Calculator");
        layoutInput = new Input();
        calculator = new Calculation();
        paymentTable = new PaymentTable();

        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(setUpInputGrid(), setUpCalculateButton(), setUpTabPane());


        Scene scene = new Scene(mainLayout, 600, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    // Set up inout grid
    private GridPane setUpInputGrid() {
        GridPane gridPane = layoutInput.setUpInput();
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }



    // Set up and handle calculations
    private HBox setUpCalculateButton() {
        Button calculateButton = new Button("Calculate");
        calculateButton.getStyleClass().add("calculate-button");

        calculateButton.setOnAction(e -> handleCalculate());

        HBox buttonContainer = new HBox(calculateButton);
        buttonContainer.setAlignment(Pos.CENTER);

        return buttonContainer;
    }

    private void handleCalculate() {
        if (validateInputs()) {
            paymentRows = calculator.calculatePayments(
                    layoutInput.getLoanAmountField().getText(),
                    layoutInput.getAnnualInterestField().getText(),
                    layoutInput.getTermYearsField().getText(),
                    layoutInput.getTermMonthsField().getText(),
                    layoutInput.getMortgageTypeBox().getValue(),
                    layoutInput.getStartMonthField().getText(),
                    layoutInput.getEndMonthField().getText(),
                    layoutInput.getDeferralStartField().getText(),
                    layoutInput.getDeferralDurationField().getText(),
                    layoutInput.getDeferralInterestField().getText()
            );

            if (paymentRows != null) {
                paymentTable.updateTable(paymentRows);
                PaymentChart paymentChart = new PaymentChart(paymentRows, calculator.getStartMonth(), calculator.getEndMonth());
                graphTab.setContent(paymentChart);
            }
        }
    }


    // Set up different tabs
    private TabPane setUpTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(100); // Set the minimum width for tabs
        tabPane.setTabMaxWidth(100);

        tabPane.getTabs().addAll(setUpPaymentTableTab(), setUpGraphTab(), setUpReportTab());
        return tabPane;
    }

    private Tab setUpPaymentTableTab() {
        Tab tableTab = new Tab("Payment Table");
        tableTab.setContent(paymentTable.getPaymentTable());
        tableTab.setClosable(false);
        return tableTab;
    }

    private Tab setUpGraphTab() {
        graphTab = new Tab("Graphs");
        graphTab.setClosable(false);
        return graphTab;
    }

    private Tab setUpReportTab() {
        Tab reportTab = new Tab("Report");
        reportTab.setClosable(false);

        Button reportButton = new Button("Generate Report");
        reportButton.getStyleClass().add("report-button");
        reportButton.setOnAction(e -> handleReport());

        VBox reportLayout = new VBox(20, reportButton);
        reportTab.setContent(reportLayout);
        return reportTab;
    }



    // Handling the report
    private void handleReport() {
        try {
            PaymentReport.generatePaymentReport(paymentRows);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



    // Validating all inputs
    private boolean validateInputs() {
        StringBuilder errorMessage = new StringBuilder();

        // Validate loan amount
        if (layoutInput.getLoanAmountField().getText().isEmpty()) {
            errorMessage.append("Loan amount cannot be empty.\n");
        } else {
            try {
                double loanAmount = Double.parseDouble(layoutInput.getLoanAmountField().getText());
                if (loanAmount <= 0) {
                    errorMessage.append("Loan amount must be greater than 0.\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Invalid loan amount format.\n");
            }
        }

        // Validate annual interest rate
        if (layoutInput.getAnnualInterestField().getText().isEmpty()) {
            errorMessage.append("Annual interest rate cannot be empty.\n");
        } else {
            try {
                double interestRate = Double.parseDouble(layoutInput.getAnnualInterestField().getText());
                if (interestRate <= 0) {
                    errorMessage.append("Annual interest rate must be more than 0\n"); // can it be above 100 tho?.....
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Invalid annual interest rate format.\n");
            }
        }

        // Validate term years
        if (layoutInput.getTermYearsField().getText().isEmpty()) {
            errorMessage.append("Term years cannot be empty.\n");
        } else {
            try {
                int termYears = Integer.parseInt(layoutInput.getTermYearsField().getText());
                if (termYears < 0) {
                    errorMessage.append("Term years must be greater than 0.\n");
                }
                if(termYears == 0 && Integer.parseInt(layoutInput.getTermMonthsField().getText()) <= 0){
                    errorMessage.append("Term length cannot be 0");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Invalid term years format.\n");
            }
        }

        // Validate optional term months (if filled)
        if (!layoutInput.getTermMonthsField().getText().isEmpty()) {
            try {
                int termMonths = Integer.parseInt(layoutInput.getTermMonthsField().getText());
                if (termMonths < 0 || termMonths > 11) {
                    errorMessage.append("Term months must be between 0 and 11.\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Invalid term months format.\n");
            }
        }
        else{
            layoutInput.getTermMonthsField().setText("0");
        }

        if(!layoutInput.getStartMonthField().getText().isEmpty()){
            try{
                int startMonth = Integer.parseInt(layoutInput.getStartMonthField().getText());
                if (startMonth < 0) {
                    errorMessage.append("Start month must be bigger than 0.\n");
                }
            } catch (NumberFormatException e){
                errorMessage.append("Invalid start month format.\n");
            }
        }

        if(!layoutInput.getEndMonthField().getText().isEmpty()){
            try{
                int endMonth = Integer.parseInt(layoutInput.getEndMonthField().getText());
                if(endMonth < 0){
                    errorMessage.append("End month must be bigger than 0.\n");
                }
            } catch(NumberFormatException e){
                errorMessage.append("Invalid end month format.\n");
            }
        }

        // Additional checks for deferral fields
        if (!layoutInput.getDeferralStartField().getText().isEmpty() || !layoutInput.getDeferralDurationField().getText().isEmpty()
                || !layoutInput.getDeferralInterestField().getText().isEmpty()) {
            if (layoutInput.getDeferralInterestField().getText().isEmpty()
                    || layoutInput.getDeferralDurationField().getText().isEmpty()
                    || layoutInput.getDeferralStartField().getText().isEmpty()) {
                errorMessage.append("All deferral fields must be filled.\n");
            } else {
                try {
                    int deferralStart = Integer.parseInt(layoutInput.getDeferralStartField().getText());
                    int deferralDuration = Integer.parseInt(layoutInput.getDeferralDurationField().getText());
                    double deferralInterest = Double.parseDouble(layoutInput.getDeferralInterestField().getText());
                    if (deferralStart <= 0 || deferralDuration <= 0 || deferralInterest < 0) {
                        errorMessage.append("Deferral fields must contain valid positive values.\n");
                    }
                } catch (NumberFormatException e) {
                    errorMessage.append("Invalid deferral field format.\n");
                }
            }
        }

        // Display error message if there are any issues
        if (errorMessage.length() > 0) {
            new Alert(Alert.AlertType.ERROR, errorMessage.toString()).showAndWait();
            return false;
        }

        return true;
    }


}
