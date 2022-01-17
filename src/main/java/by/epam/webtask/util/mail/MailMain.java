package by.epam.webtask.util.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MailMain{
    static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        Properties properties = new Properties();
        try(FileReader fileReader = new FileReader("src/main/resources/config/mail.properties"))
        {
            properties.load(fileReader);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Exception while reading " + e);
        }
        System.out.println(properties);
        String mailTo = "dayanfitnessCenter78t@gmail.com";
        String subject = "Sample Mail";
        String body = "Hello java mail";
        MailSender sender = new MailSender(mailTo, subject, body, properties);
        sender.send();
    }
}