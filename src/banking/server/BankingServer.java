package banking.server;

import banking.core.BankAccount;
import banking.exceptions.InsufficientFundsException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class BankingServer {
    // One static account for this demo (The User)
    private static BankAccount myAccount = new BankAccount("VIT Student", 5000.00);

    public static void main(String[] args) throws IOException {
        // Start server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Route 1: Show the HTML Page
        server.createContext("/", new PageHandler());
        
        // Route 2: Process Transactions
        server.createContext("/process", new TransactionHandler());
        
        // Route 3: Serve CSS
        server.createContext("/style.css", new CSSHandler());

        server.setExecutor(null); // Default executor
        System.out.println("Server started! Open http://localhost:8000 in your browser.");
        server.start();
    }

    // Handles displaying the HTML page
    static class PageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String html = new String(Files.readAllBytes(Paths.get("web/index.html")));
            
            // Dynamic Data Injection: Replace placeholders in HTML with real Java data
            html = html.replace("{{BALANCE}}", String.format("%.2f", myAccount.getBalance()));
            html = html.replace("{{MESSAGE}}", ""); // Clear message on fresh load

            exchange.sendResponseHeaders(200, html.length());
            OutputStream os = exchange.getResponseBody();
            os.write(html.getBytes());
            os.close();
        }
    }

    // Handles Form Submissions (Deposit/Withdraw)
    static class TransactionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Read form data
                String formData = new String(exchange.getRequestBody().readAllBytes());
                Map<String, String> params = parseQuery(formData);

                String type = params.get("type");
                double amount = Double.parseDouble(params.get("amount"));
                String message = "";

                try {
                    if (type.equals("deposit")) {
                        myAccount.deposit(amount);
                        message = "Success: Deposited $" + amount;
                    } else if (type.equals("withdraw")) {
                        myAccount.withdraw(amount);
                        message = "Success: Withdrawn $" + amount;
                    }
                } catch (InsufficientFundsException e) {
                    message = "Error: " + e.getMessage();
                }

                // Reload the page with the new data and message
                String html = new String(Files.readAllBytes(Paths.get("web/index.html")));
                html = html.replace("{{BALANCE}}", String.format("%.2f", myAccount.getBalance()));
                html = html.replace("{{MESSAGE}}", message);

                exchange.sendResponseHeaders(200, html.length());
                OutputStream os = exchange.getResponseBody();
                os.write(html.getBytes());
                os.close();
            }
        }
    }

    // Helper to serve CSS
    static class CSSHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] css = Files.readAllBytes(Paths.get("web/style.css"));
            exchange.sendResponseHeaders(200, css.length);
            OutputStream os = exchange.getResponseBody();
            os.write(css);
            os.close();
        }
    }

    // Helper to parse "amount=500&type=deposit"
    private static Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }
        }
        return result;
    }
}