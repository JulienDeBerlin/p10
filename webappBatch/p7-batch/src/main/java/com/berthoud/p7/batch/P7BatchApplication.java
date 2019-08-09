package com.berthoud.p7.batch;

import com.berthoud.p7.batch.lateLoansMonitoring.SendReminderEmailJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.berthoud.p7"})

public class P7BatchApplication implements CommandLineRunner{

    public static Logger logger = LoggerFactory.getLogger(P7BatchApplication.class);


    SendReminderEmailJob sendReminderEmailJob;

    @Autowired
    public P7BatchApplication(SendReminderEmailJob sendReminderEmailJob) {
        this.sendReminderEmailJob = sendReminderEmailJob;
    }


    public static void main(String[] args) {
        SpringApplication.run(P7BatchApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        sendReminderEmailJob.launchJob();
    }


}


