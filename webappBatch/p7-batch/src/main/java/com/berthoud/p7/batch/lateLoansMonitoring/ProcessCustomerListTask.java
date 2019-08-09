package com.berthoud.p7.batch.lateLoansMonitoring;

import com.berthoud.p7.batch.P7BatchApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import p7.webapp.model.beans.Customer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * This class is designed for a task, a task being part of a batch job.
 * The task designed here is about  processing a list of {@link Customer} objects by sending each of the customer a reminder email.
 */

@Component
public class ProcessCustomerListTask {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private ReminderEmailBuilder reminderEmailBuilder;

    public static Logger loggerEmail = LoggerFactory.getLogger(ProcessCustomerListTask.class);

    @Value("classpath:logoSmall.jpg")
    Resource smallLogo;


    /**
     * This method sends a html email to each {@link Customer} object of the input list
     *
     * @param customerList a list of {@link Customer} objects, all of them having late books to return.
     * @throws MessagingException
     */
    public void sendHtmlEmail(List<Customer> customerList) throws MessagingException {
        P7BatchApplication.logger.trace("Enter sendHtmlEmail(), Task 'ProcessCustomerList' is starting");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date timeStamp = new Date();
        String dateString = simpleDateFormat.format(timeStamp);


        loggerEmail.info("Liste des emails de relance envoyés le " + dateString + ":");

        for (Customer c : customerList) {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            reminderEmailBuilder.initReminderEmailBuilder(c);
            String htmlMsg = reminderEmailBuilder.buildEmailContentHtml();

            helper.setTo(c.getEmail());

            helper.setSubject("Rappel: retour d'ouvrages en retard");

            helper.setText(htmlMsg, true);

            // Absolute path
//            FileSystemResource res = new FileSystemResource(new File("/Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P7/Apps_P7/Webapp/p7-batch/src/main/resources/logoSmall.jpg"));


            // Relative path - this method doesnt work - TBC WHY
//            ClassPathResource res = new ClassPathResource("../../../../../../../resources/logoSmall.jpg", ProcessCustomerListTask.class);

            helper.addInline("smallLogo", smallLogo);

            this.emailSender.send(message);

            loggerEmail.info(c.getSurname() + " " + c.getFirstName() + " /// ID-usager:" + c.getId() + " /// niveau de rappel: " + reminderEmailBuilder.getDelayLevel());

        }

        loggerEmail.info("Fin de l'envoi \n\n\n");

    }

}
