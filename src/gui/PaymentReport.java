package gui;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import logic.PaymentRow;

import java.io.*;

// CLass for generating payment report as a .txt file
public class PaymentReport {

    public static void generatePaymentReport(ObservableList<PaymentRow> paymentRows) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT file", "*.txt"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(String.format("%-10s %-15s %-10s %-10s %-20s%n", "Month", "Total Payment", "Principal", "Interest", "Remaining Balance"));
                writer.write("--------------------------------------------------------------------------\n");

                for (PaymentRow row : paymentRows) {
                    writer.write(String.format("%-10d %-15.2f %-10.2f %-10.2f %-20.2f%n",
                            row.getMonth(),
                            row.getTotalPayment(),
                            row.getPrincipal(),
                            row.getInterest(),
                            row.getRemainingBalance()));
                }
                new Alert(Alert.AlertType.INFORMATION, "Report saved successfully").showAndWait();
            }
            catch (IOException e) {
                new Alert(Alert.AlertType.INFORMATION, "Error saving report" + e.getMessage()).showAndWait();
            }
        }
    }
}
