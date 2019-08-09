package com.berthoud.p7.batch.lateLoansMonitoring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import p7.webapp.model.beans.Customer;
import p7.webapp.model.beans.Loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.berthoud.p7.webapp.business.utils.Utils.localDateFormater;


/**
 * This class is dedicated to the build of the reminder email.
 */

@Component
@PropertySources({
        @PropertySource(value = "classpath:messages.properties", encoding = "UTF-8"),
        @PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
})

public class ReminderEmailBuilder {

    private Customer customer;

    private int delayLevel;

    private String coreMessage;

    @Value("${salutations}")
    private String salutations;
    @Value("${coreMessagelevel1}")
    private String coreMessagelevel1;
    @Value("${coreMessagelevel2}")
    private String coreMessagelevel2;
    @Value("${coreMessagelevel3}")
    private String coreMessagelevel3;
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
    @Value("${level1}")
    private String delayLevel1;
    @Value("${level2}")
    private String delayLevel2;
    @Value("${level3}")
    private String delayLevel3;

    /**
     * This method is used to set {@link ReminderEmailBuilder#customer} and the corresponding fields
     * ( {@link ReminderEmailBuilder#coreMessage} and {@link ReminderEmailBuilder#delayLevel}) after default initialization.
     *
     * @param customer the customer the reminder email should be sent to.
     */
    public void initReminderEmailBuilder(Customer customer) {
        this.customer = customer;
        setDelayLevel(customer);
        setCoreMessage();
        Collections.sort(customer.getLoans());
    }


    private void setDelayLevel(Customer c) {

        List<Loan> loans = c.getLoans();

        int maxDelayDuration = (int) loans.get(0).getDateEnd().until(LocalDate.now(), ChronoUnit.DAYS);
        String test1 = delayLevel2;
        int test = Integer.parseInt(test1);
        if (maxDelayDuration >= Integer.parseInt(this.delayLevel3)) {
            this.delayLevel = 3;

        } else if (maxDelayDuration >= Integer.parseInt(this.delayLevel2)) {
            this.delayLevel = 2;

        } else {
            this.delayLevel = 1;

        }
    }

    private void setCoreMessage() {

        switch (this.delayLevel) {
            case 1:
                this.coreMessage = this.coreMessagelevel1;
                break;
            case 2:
                this.coreMessage = this.coreMessagelevel2;
                break;
            case 3:
                this.coreMessage = this.coreMessagelevel3;
                break;
        }
    }


    /**
     * This method create a html template for the reminder email.
     *
     * @return a personalized html reminder email
     */
    public String buildEmailContentHtml() {

        String htmlMsg;

        htmlMsg = "<p> " + salutations + " " + customer.getFirstName() + ", " + "</p>"
                + "<p>" + coreMessage + "</p>"
                + getHtmlFormatedLateLoans()
                + "<p>" + greetings + "<br>" + signature + "</p>"
                + "<br>"
                + "<br>"
                + "<img src='cid:smallLogo'>"
                + "<p>" + adresseLine1 + "<br>" + adresseLine2 + "<br>" + adresseLine3 + "</p>";

        return htmlMsg;
    }


    /**
     * This method is used for the creation of the part of the html reminder email with the details about the books that should be returned.
     *
     * @return a html fragment as String that can be integrated in the email template.
     */
    private String getHtmlFormatedLateLoans() {
        String htmlFormatedLateLoans = "<table>\n" +
                "  <tr>" +
                "    <th>Titre</th>" +
                "    <th>Auteur</th>" +
                "    <th>Emprunté le</th>" +
                "    <th>Retour dû</th>" +
                "    <th>Jours de retard</th>" +
                "  </tr>";

        for (Loan l : customer.getLoans()) {
            htmlFormatedLateLoans += " <tr>" +
                    "    <td> " + l.getBook().getBookReference().getTitle() + "</td>" +
                    "    <td> " + l.getBook().getBookReference().getAuthorFirstName() + " " + l.getBook().getBookReference().getAuthorSurname() + "</td>\n" +
                    "    <td>" + localDateFormater(l.getDateBegin()) + "</td>" +
                    "    <td>" + localDateFormater(l.getDateEnd()) + "</td>" +
                    "    <td>" + l.getDateEnd().until(LocalDate.now(), ChronoUnit.DAYS) + "</td>" +
                    "  </tr>";
        }

        htmlFormatedLateLoans += "</table>";

        return htmlFormatedLateLoans;
    }

    public int getDelayLevel() {
        return delayLevel;
    }
}
