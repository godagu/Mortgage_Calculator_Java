package gui;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logic.PaymentRow;

// GUI class for creating the table
public class PaymentTable {
    private final TableView<PaymentRow> paymentTable;

    PaymentTable() {
        paymentTable = new TableView<>();
        setUpPaymentTable();
    }


    public void setUpPaymentTable() {
        TableColumn<PaymentRow, Integer> monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty().asObject());

        TableColumn<PaymentRow, Double> totalPaymentColumn = new TableColumn<>("Total Payment");
        totalPaymentColumn.setCellValueFactory(cellData -> cellData.getValue().totalPaymentProperty().asObject());

        TableColumn<PaymentRow, Double> principalColumn = new TableColumn<>("Principal");
        principalColumn.setCellValueFactory(cellData -> cellData.getValue().principalProperty().asObject());

        TableColumn<PaymentRow, Double> interestColumn = new TableColumn<>("Interest");
        interestColumn.setCellValueFactory(cellData -> cellData.getValue().interestProperty().asObject());

        TableColumn<PaymentRow, Double> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());

        paymentTable.getColumns().addAll(monthColumn, totalPaymentColumn, principalColumn, interestColumn, balanceColumn);
    }

    public void updateTable(ObservableList<PaymentRow> paymentRows) {
        this.paymentTable.setItems(paymentRows);
    }

    public TableView<PaymentRow> getPaymentTable() {
        return paymentTable;
    }


}
