package com.scriptora;

import static spark.Spark.*;
import com.google.gson.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Main {
    public static void main(String[] args) {
        port(4567);
        staticFiles.externalLocation("static");

        post("/contact", (req, res) -> {
            res.type("text/plain");
            try {
                JsonObject json = JsonParser.parseString(req.body()).getAsJsonObject();
                String name = json.get("name").getAsString();
                String email = json.get("email").getAsString();
                String message = json.get("message").getAsString();

                String to = "your-email@example.com"; // Replace with your email
                String from = "your-email@example.com"; // Replace with your SMTP sender email
                String host = "smtp.example.com"; // Replace with your SMTP host

                Properties props = new Properties();
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("your-email@example.com", "your-password"); // replace
                    }
                });

                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                mimeMessage.setSubject("New Contact Message from " + name);
                mimeMessage.setText("From: " + name + " (" + email + ")

" + message);

                Transport.send(mimeMessage);
                return "Message sent successfully!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error sending message: " + e.getMessage();
            }
        });
    }
}
