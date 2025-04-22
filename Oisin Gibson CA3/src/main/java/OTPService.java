import java.security.SecureRandom;
import java.util.HashMap;

public class OTPService {
    // Create a secure random number generator for generating OTPs
    private static final SecureRandom random = new SecureRandom();

    // Store OTPs using a HashMap, where the key is an identifier
    private static final HashMap<String, String> otpStore = new HashMap<>();

    // Method to generate a 6-digit OTP and associate it with the given identifier
    public static String generateOTP(String identifier) {
        // Generate a random number between 000000 and 999999
        String otp = String.format("%06d", random.nextInt(999999));

        // Store the OTP in the HashMap
        otpStore.put(identifier, otp);

        // Return the generated OTP
        return otp;
    }

    // Method to verify whether the input OTP matches the stored OTP for the given
    // identifier
    public static boolean verifyOTP(String identifier, String inputOTP) {
        // Retrieve the correct OTP from the HashMap
        String correctOTP = otpStore.get(identifier);

        // Check if the correct OTP exists and matches the user-provided OTP
        return correctOTP != null && correctOTP.equals(inputOTP);
    }
}
