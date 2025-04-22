import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the BankAccount and
 * PasswordEncryptionServiceAns classes.
 * It verifies key functionalities such as balance management and password
 * encryption/authentication.
 */
public class MyBankTest {

    // Instance of BankAccount used across tests.
    private BankAccount account;

    /**
     * This setup method runs before each test to ensure a fresh account instance.
     * It initializes a BankAccount with a specific account number, name, and
     * initial balance.
     */
    @BeforeEach
    public void setUp() {
        account = new BankAccount("123456", "Alice Smith", 1000.00);
    }

    /**
     * Verifies that the BankAccount is created with the correct initial balance.
     */
    @Test
    public void testInitialBalance() {
        // Check if the initial balance is exactly 1000.00 with a tolerance of 0.001.
        assertEquals(1000.00, account.getBalance(), 0.001);
    }

    /**
     * Tests the deposit functionality by depositing an amount and checking if the
     * balance updates correctly.
     */
    @Test
    public void testDeposit() {
        // Deposit 500.00 to the account
        account.deposit(500.00);
        // After depositing, the expected balance is 1500.00.
        assertEquals(1500.00, account.getBalance(), 0.001);
    }

    /**
     * Tests the withdrawal functionality by withdrawing an amount within the
     * balance limit.
     * It checks if the withdrawal is successful and the balance is properly
     * reduced.
     */
    @Test
    public void testWithdraw() {
        // Attempt to withdraw 300.00 from the account.
        boolean success = account.withdraw(300.00);
        // Assert that the withdrawal operation succeeded.
        assertTrue(success);
        // Verify the new account balance after withdrawal should be (1000.00 - 300.00 =
        // 700.00).
        assertEquals(700.00, account.getBalance(), 0.001);
    }

    /**
     * Tests the condition where the withdrawal amount exceeds the available
     * balance.
     * It ensures that the operation fails and the account balance remains
     * unchanged.
     */
    @Test
    public void testWithdrawTooMuch() {
        // Attempt to withdraw 5000.00, which is more than the current balance.
        boolean success = account.withdraw(5000.00);
        // Assert that the withdrawal operation fails.
        assertFalse(success);
        // Confirm that the account balance remains unchanged at 1000.00.
        assertEquals(1000.00, account.getBalance(), 0.001);
    }

    /**
     * Tests updating the account holder's name functionality.
     * It sets a new account holder name and confirms that the change took effect.
     */
    @Test
    public void testSetAccountHolder() {
        // Update the account holder name to a new value.
        account.setAccountHolder("Alice B. Smith");
        // Confirm that the new account holder is reflected in the account.
        assertEquals("Alice B. Smith", account.getAccountHolder());
    }

    @Test
    public void testPasswordEncryptionCorrectPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Define a strong password to be encrypted.
        String password = "Str0ng!Pass";
        // Generate a unique salt for the encryption process.
        byte[] salt = PasswordEncryptionServiceAns.generateSalt();
        // Encrypt the given password using the generated salt.
        byte[] encrypted = PasswordEncryptionServiceAns.getEncryptedPassword(password, salt);

        // Validate that authentication succeeds when using the correct password.
        assertTrue(PasswordEncryptionServiceAns.authenticate("Str0ng!Pass", encrypted, salt));
    }

    @Test
    public void testPasswordEncryptionIncorrectPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Define the original strong password.
        String password = "Str0ng!Pass";
        // Generate a unique salt for the encryption process.
        byte[] salt = PasswordEncryptionServiceAns.generateSalt();
        // Encrypt the provided strong password using the generated salt.
        byte[] encrypted = PasswordEncryptionServiceAns.getEncryptedPassword(password, salt);

        // Validate that authentication fails when an incorrect password ("wrongpass")
        // is used.
        assertFalse(PasswordEncryptionServiceAns.authenticate("wrongpass", encrypted, salt));
    }
}
