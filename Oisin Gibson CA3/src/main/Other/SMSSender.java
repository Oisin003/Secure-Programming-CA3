import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SMSSender {
    // Twilio credentials (replace with actual values)
    public static final String ACCOUNT_SID = " "; // Twilio Account SID
    public static final String AUTH_TOKEN = " "; // Twilio Authentication Token
    public static final String FROM_PHONE = " "; // Twilio phone number used to send SMS

    // Static block to initialize Twilio with authentication credentials
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendSMS(String to, String body) {
        // Create and send an SMS message using Twilio API
        Message.creator(
                new com.twilio.type.PhoneNumber(to), // Recipient's phone number
                new com.twilio.type.PhoneNumber(FROM_PHONE), // Sender's Twilio phone number
                body) // Message text
                .create(); // Send the message
    }
}
