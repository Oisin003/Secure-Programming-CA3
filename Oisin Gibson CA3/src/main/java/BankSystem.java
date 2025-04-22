
// Required imports for database and utilities
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt; // Import BCrypt for password hashing

public class BankSystem {

    // Database connection configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("---- OG Bank System ----");

        // Main menu loop
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Create Account");
                    createAccount();
                    break;
                case 2:
                    System.out.println("Login Account");
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using OG Banking.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Check if a password meets the security criteria
    public static boolean isStrongPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{6,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    // Create a new customer account and save to database
    public static void createAccount() {
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Phone Number: ");
        String phone = scanner.next();

        // Loop until strong password is entered
        String password;
        while (true) {
            System.out.print("Password: ");
            password = scanner.next();
            if (isStrongPassword(password))
                break;
            System.out.println("Weak password! Please try again.");
        }

        // Hash the password before storing in database
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.print("Initial Deposit: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Insert new customer into the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO customers (fullName, emailAddress, phoneNumber, password, balance) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, hashedPassword);
            pstmt.setDouble(5, balance);
            pstmt.executeUpdate();

            // Fetch and display the auto-generated account number
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("Account created successfully! Your account number is: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Authenticate user and perform OTP verification
    public static void login() {
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Account Number: ");
        int accountNo = scanner.nextInt();
        System.out.print("Password: ");
        String password = scanner.next();

        // Query the user’s details based on provided credentials
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT emailAddress, phoneNumber, password FROM customers WHERE accountNo = ? AND fullName = ?")) {

            pstmt.setInt(1, accountNo);
            pstmt.setString(2, name.trim());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve hashed password from DB
                String storedHashedPassword = rs.getString("password");

                // Verify input password with stored hash
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    System.out.println("Login successful!");

                    // Generate OTPs for phone and email
                    String email = rs.getString("emailAddress");
                    String phone = rs.getString("phoneNumber");

                    String emailOTP = OTPService.generateOTP(name + accountNo + "EMAIL");
                    String phoneOTP = OTPService.generateOTP(name + accountNo + "PHONE");

                    // Simulate sending OTPs
                    System.out.println("Sending OTP to phone -> " + phone + ": " + phoneOTP);
                    System.out.println("Sending OTP to email -> " + email + ": " + emailOTP);

                    // Prompt user to enter received OTPs
                    System.out.print("Enter the OTP from your email: ");
                    String enteredEmailOTP = scanner.next();

                    System.out.print("Enter the OTP from your phone: ");
                    String enteredPhoneOTP = scanner.next();
                    System.out.println(" ");

                    // Verify both OTPs
                    if (OTPService.verifyOTP(name + accountNo + "EMAIL", enteredEmailOTP) &&
                            OTPService.verifyOTP(name + accountNo + "PHONE", enteredPhoneOTP)) {
                        System.out.println("---- Welcome to OG Banking ----");
                        customerMenu(String.valueOf(accountNo));
                    } else {
                        System.out.println("Invalid OTP(s). Access denied.");
                    }
                } else {
                    System.out.println("Incorrect password. Login failed.");
                }
            } else {
                System.out.println("Invalid login credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show post-login customer operations
    public static void customerMenu(String accountNo) {
        while (true) {
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewBalance(accountNo);
                    break;
                case 2:
                    updateBalance(accountNo, true); // true indicates deposit
                    break;
                case 3:
                    updateBalance(accountNo, false); // false indicates withdrawal
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // Display user's current balance
    public static void viewBalance(String accountNo) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement("SELECT balance FROM customers WHERE accountNo = ?")) {

            pstmt.setString(1, accountNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Your current balance is: $" + rs.getDouble("balance"));
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Handle deposit or withdrawal based on flag
    public static void updateBalance(String accountNo, boolean isDeposit) {
        System.out.print(isDeposit ? "Deposit Amount: " : "Withdraw Amount: ");
        double amount = scanner.nextDouble();

        // Build SQL for deposit or withdrawal
        String sql = "UPDATE customers SET balance = balance " + (isDeposit ? "+ ?" : "- ?")
                + " WHERE accountNo = ? AND balance >= ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNo);
            pstmt.setDouble(3, isDeposit ? 0 : amount); // Ensure sufficient funds on withdrawal
            int updated = pstmt.executeUpdate();

            if (updated > 0) {
                System.out.println("Transaction successful.");
                viewBalance(accountNo);
            } else {
                System.out.println("Insufficient funds.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
