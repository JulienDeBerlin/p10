package com.berthoud.p7.webserviceapp.business.batch.reservation;

import com.berthoud.p7.webserviceapp.business.BusinessLogger;
import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@PropertySources({
        @PropertySource(value = "classpath:notificationEmail.properties", encoding = "UTF-8")
})
public class SendNotificationTask {


    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private NotificationEMailBuilder notificationEMailBuilder;

    @Value(value = "file:/Users/admin/Documents/PROGRAMMING/OPENCLASSROOMS/P10/P10_repoMerged/ws/p7-webservice-app-business/src/main/resources/logoSmall.jpg")
    Resource smallLogo;

    @Value("${reservationDelayInSecond}")
    int reservationDelayInSecond;

    public static Logger loggerEmail = LoggerFactory.getLogger(SendNotificationTask.class);


    public void sendNotification(Customer customer, Reservation reservation) throws MessagingException {
        BusinessLogger.logger.trace("Enter sendHtmlEmail(), Task 'SendNotificationTask' is starting");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatedDateNow = now.format(formatter);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        notificationEMailBuilder.initNotificationEMailBuilder(customer, reservation);
        String HtmlNotification = notificationEMailBuilder.buildEmailContentHtml();

        helper.setTo(customer.getEmail());

        helper.setSubject("Votre réservation est disponible pour " + reservationDelayInSecond + "secondes !");

        helper.setText(HtmlNotification, true);

        helper.addInline("smallLogo", smallLogo);


        this.emailSender.send(message);

        loggerEmail.info("Notification (réservation disponible) envoyée le " + formatedDateNow + ":");
        loggerEmail.info("Usager: " + customer.getFirstName() + " " + customer.getSurname() +", ID " + customer.getId());
        loggerEmail.info("Réservation ID:" + reservation.getId());
        loggerEmail.info("Ouvrage:" + reservation.getBookReference().getTitle() + ", " + reservation.getBookReference().getAuthorFirstName() +
                " " + reservation.getBookReference().getAuthorSurname());
        loggerEmail.info("FIN \n\n" );



    }


}
