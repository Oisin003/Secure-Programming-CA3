public class BankAccount {
    // Instance variables
    private final String accountNumber; // Unique identifier for the bank account
    private String accountHolder; // Name of the account holder
    private double balance; // Current account balance

    // Constructor to initialize a new BankAccount
    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative"); // Ensure starting balance is valid
        }
        this.balance = balance;
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for account holder's name
    public String getAccountHolder() {
        return accountHolder;
    }

    // Getter for current balance
    public double getBalance() {
        return balance;
    }

    // Setter for account holder's name
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    // Method to deposit money into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Method to withdraw money from the account
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
    }

    // Method to return a string representation of the account
    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolder='" + accountHolder + '\'' +
                ", balance=" + balance +
                '}';
    }
}
