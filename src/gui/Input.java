package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


// Class for input
public class Input {
    private TextField loanAmountField;
    private TextField annualInterestField;
    private TextField termYearsField;
    private TextField termMonthsField;
    private TextField startMonthField;
    private TextField endMonthField;
    private ComboBox<String> mortgageTypeBox;
    private TextField deferralStartField;
    private TextField deferralDurationField;
    private TextField deferralInterestField;
    private VBox deferralFields;

    Input(){
        setUpInput();
    }

    public GridPane setUpInput() {
        GridPane gridPane = new GridPane();

        loanAmountField = new TextField();
        loanAmountField.setMaxWidth(150);

        annualInterestField = new TextField();
        annualInterestField.setMaxWidth(150);

        termYearsField = new TextField();
        termYearsField.setMaxWidth(150);

        termMonthsField = new TextField();
        termMonthsField.setMaxWidth(150);

        startMonthField = new TextField();
        startMonthField.setMaxWidth(150);

        endMonthField = new TextField();
        endMonthField.setMaxWidth(150);

        deferralStartField = new TextField();
        deferralStartField.setMaxWidth(150);

        deferralDurationField = new TextField();
        deferralDurationField.setMaxWidth(150);

        deferralInterestField = new TextField();
        deferralInterestField.setMaxWidth(150);

        mortgageTypeBox = new ComboBox<>();
        mortgageTypeBox.getItems().addAll("Annuity", "Linear");
        mortgageTypeBox.setValue("Annuity");
        mortgageTypeBox.setMaxWidth(150);

        Button addDeferralButton = new Button("Add deferral");

        deferralFields = new VBox(20);


        GridPane deferralGrid = new GridPane();
        deferralGrid.add(new Label("Deferral Start:"), 0, 0);
        deferralGrid.add(deferralStartField, 1, 0);

        deferralGrid.add(new Label("Deferral Duration:"), 0, 1);
        deferralGrid.add(deferralDurationField, 1, 1);

        deferralGrid.add(new Label("Deferral Interest:"), 0, 2);
        deferralGrid.add(deferralInterestField, 1, 2);

        deferralGrid.setHgap(10); // Horizontal gap
        deferralGrid.setVgap(10); // Vertical gap

        deferralFields.getChildren().add(deferralGrid);

        deferralFields.setVisible(false);
        deferralFields.setManaged(false);

        addDeferralButton.setOnAction(e -> {
            boolean isVisible = !deferralFields.isVisible();
            deferralFields.setVisible(isVisible);
            deferralFields.setManaged(isVisible);
        });

        // Add components to the GridPane
        gridPane.add(new Label("Loan Amount:"), 0, 0);
        gridPane.add(loanAmountField, 1, 0);

        gridPane.add(new Label("Annual Interest:"), 0, 1);
        gridPane.add(annualInterestField, 1, 1);

        gridPane.add(new Label("Term (Years):"), 0, 2);
        gridPane.add(termYearsField, 1, 2);

        gridPane.add(new Label("Term (Months):"), 2, 2);
        gridPane.add(termMonthsField, 3, 2);

        gridPane.add(new Label("Start Month:"), 0, 3);
        gridPane.add(startMonthField, 1, 3);

        gridPane.add(new Label("End Month:"), 2, 3);
        gridPane.add(endMonthField, 3, 3);

        gridPane.add(new Label("Mortgage Type:"), 0, 4);
        gridPane.add(mortgageTypeBox, 1, 4);


        gridPane.add(addDeferralButton, 0, 5);

        // Add the VBox with the deferral fields below the button
        gridPane.add(deferralFields, 0, 6, 2, 3);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }

    // Getter methods for the input fields
    public TextField getLoanAmountField() {
        return loanAmountField;
    }

    public TextField getAnnualInterestField() {
        return annualInterestField;
    }

    public TextField getTermYearsField() {
        return termYearsField;
    }

    public TextField getTermMonthsField() {
        return termMonthsField;
    }

    public TextField getStartMonthField() {
        return startMonthField;
    }

    public TextField getEndMonthField() {
        return endMonthField;
    }

    public ComboBox<String> getMortgageTypeBox() {
        return mortgageTypeBox;
    }

    public TextField getDeferralStartField() {
        return deferralStartField;
    }

    public TextField getDeferralDurationField() {
        return deferralDurationField;
    }

    public TextField getDeferralInterestField() {
        return deferralInterestField;
    }

}
