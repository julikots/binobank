package com.binobank.digitalwallet.service;

import com.binobank.digitalwallet.model.Statement;
import com.binobank.digitalwallet.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class UserService {

    @Scheduled(fixedDelay = 3600000)
    public static void expireStatements() {
        User user = User.getUser();
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (Statement currentStatement : user.getUserStatements()) {
            if (currentStatement.isVisited()) {
                try {
                    Date entryDate = sdfrmt.parse(currentStatement.getEntryDate());

                    long millisIn48Hours = 1000 * 60 * 60 * 48;
                    Date date48HoursAgo = new Date(new Date().getTime() - millisIn48Hours);

                    if (entryDate.before(date48HoursAgo)) {
                        user.getUserStatements().remove(currentStatement);
                    }
                } catch (ParseException parseException) {
                    System.out.println(parseException.getMessage());
                }
            }
        }
    }

    public static void addUserStatement(Statement statement) {
        User user = User.getUser();
        if (user.getUserStatements().stream().noneMatch(userStatement ->
                userStatement.getTransactionId().equals(statement.getTransactionId()))) {
            User.getUser().addStatement(statement);
        }
    }

    public static void calculateBalance() {
        User user = User.getUser();

        synchronized (user.getUserStatements()) {
            for (Statement currentStatement : user.getUserStatements()) {
                if (!currentStatement.isVisited()) {
                    float amount = currentStatement.getDescription().contains("CASHOUT") ?
                            -currentStatement.getAmount() : currentStatement.getAmount();

                    user.updateBalance(amount);
                    currentStatement.setVisited(true);
                }
            }
        }
    }
}
