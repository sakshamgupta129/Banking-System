
package banking.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileManager {
    
    // Appends transaction details to a text file
    public static void logTransaction(String accountName, String type, double amount) {
        try (FileWriter writer = new FileWriter("BankStatement.txt", true)) {
            String timestamp = LocalDateTime.now().toString();
            String record = String.format("[%s] Account: %s | %s: $%.2f%n", timestamp, accountName, type, amount);
            writer.write(record);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}