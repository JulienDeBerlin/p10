package com.berthoud.p7.webserviceapp.business.batch.reservation;

import com.berthoud.p7.webserviceapp.model.entities.Customer;
import com.berthoud.p7.webserviceapp.model.entities.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;


@Component
@PropertySources({
        @PropertySource(value = "classpath:notificationEmail.properties", encoding = "UTF-8")
})
public class NotificationEMailBuilder {

    private Customer customer;

    private Reservation reservation;

    @Value("${salutations}")
    private String salutations;
    @Value("${greetings}")
    private String greetings;
    @Value("${signature}")
    private String signature;
    @Value("${adresseLine1}")
    private String adresseLine1;
    @Value("${adresseLine2}")
    private String adresseLine2;
    @Value("${adresseLine3}")
    private String adresseLine3;
    @Value("${coreMessage1}")
    private String coreMessage1;
    @Value("${coreMessage2}")
    private String coreMessage2;
    @Value("${coreMessage3}")
    private String coreMessage3;


    public void initNotificationEMailBuilder(Customer customer, Reservation reservation) {
        this.customer = customer;
        this.reservation = reservation;
    }

    /**
     * This method create a html template for the reminder email.
     *
     * @return a personalized html reminder email
     */
    public String buildEmailContentHtml() {

        String htmlMsg;

        htmlMsg = "<p> " + salutations + " " + customer.getFirstName() + ", " + "</p>"
                + "<p>" + coreMessage1 + "</p>"
                + getHtmlFormatedReservation()
                + "<p>" + coreMessage2 + reservation.getLibrairy().getName() + ". </p>"
                + "<p>" + coreMessage3 + "</p>"
                + "<br>"
                + "<p>" + greetings + "<br>" + signature + "</p>"
                + "<br>"
                + "<br>"
                + "<img src='cid:smallLogo'>"
                + "<p>" + adresseLine1 + "<br>" + adresseLine2 + "<br>" + adresseLine3 + "</p>";

        return htmlMsg;
    }


    /**
     * This method is used for the creation of the part of the html notification email with the details about the reserved book that is available.
     *
     * @return a html fragment as String that can be integrated in the email template.
     */
    private String getHtmlFormatedReservation() {
        String htmlFormatedLateLoans = "<table>\n" +
                "  <tr>" +
                "    <th>Titre</th>" +
                "    <th>Auteur</th>" +
                "  </tr>";


            htmlFormatedLateLoans += " <tr>" +
                    "    <td> " + reservation.getBookReference().getTitle() + "</td>" +
                    "    <td> " + reservation.getBookReference().getAuthorFirstName() + " " + reservation.getBookReference().getAuthorSurname() + "</td>\n" +
                    "  </tr>";


        htmlFormatedLateLoans += "</table>";

        return htmlFormatedLateLoans;
    }



}