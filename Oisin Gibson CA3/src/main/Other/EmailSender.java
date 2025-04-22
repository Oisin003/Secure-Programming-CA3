import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void sendEmail(String to, String subject, String content) {
        final String username = " "; // Set the sender's email address
        final String password = " "; // Use an app password for authentication

        // Configure SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS encryption
        props.put("mail.smtp.host", "smtp.gmail.com"); // Specify the SMTP server
        props.put("mail.smtp.port", "587"); // Use port 587 for TLS-based communication

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); // Authenticate using email and password
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(" ")); // Set the sender's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Set the recipient(s)
            message.setSubject(subject); // Set the email subject
            message.setText(content); // Set the email body

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace(); // Print any errors encountered
        }
    }
}

// https://javaee.github.io/javamail/
