package com.binobank.digitalwallet;

import com.binobank.digitalwallet.model.Statement;
import com.binobank.digitalwallet.model.User;
import com.binobank.digitalwallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

class UserServiceTests {

	@Test
	void recalculateBalance() {
		User user = User.getUser();
		user.clearStatements();

		Statement statement0 = new Statement("fa4b8041-81e8-47aa-9713-e3d634e5336b", "CASHIN VIA PIX", Statement.TransactionType.PIX, "2021-01-26T08:10:12", 50.0f, Statement.Type.CREDIT);
		Statement statement1 = new Statement("fa4b8041-81e8-47aa-9713-e3d634e533ty", "CASHIN VIA PIX", Statement.TransactionType.PIX, "2021-01-27T08:10:12", 120.32f, Statement.Type.CREDIT);
		Statement statement2 = new Statement("fa4b8041-81e8-47aa-9713-e3d634e533aa", "CASHOUT VIA PIX", Statement.TransactionType.CARD, "2021-01-27T08:15:12", 80.10f, Statement.Type.DEBIT);

		statement0.setVisited(true);

		user.addStatement(statement0);
		user.addStatement(statement1);
		user.addStatement(statement2);

		final CompletableFuture<Void> webhook1 = CompletableFuture.runAsync(UserService::calculateBalance);
		final CompletableFuture<Void> webhook2 = CompletableFuture.runAsync(UserService::calculateBalance);
		final CompletableFuture<Void> cron = CompletableFuture.runAsync(UserService::calculateBalance);
		final CompletableFuture<Void> allCompleted = CompletableFuture.allOf(webhook1, webhook2, cron);
		allCompleted.thenRun(() -> {
			Assertions.assertEquals(user.getBalance(), 40.22f);
		});
	}

	@Test
	void addNewUserStatement() {
		User user = User.getUser();
		user.clearStatements();

		Statement statement = new Statement("fa4b8041-81e8-47aa-9713-e3d634e5336b", "CASHIN VIA PIX", Statement.TransactionType.PIX, "2021-01-27T08:10:12", 120.32f, Statement.Type.CREDIT);
		UserService.addUserStatement(statement);

		Assertions.assertFalse(user.getUserStatements().isEmpty());
	}

	@Test
	void addRepeatedUserStatement() {
		User user = User.getUser();
		user.clearStatements();

		Statement statement0 = new Statement("fa4b8041-81e8-47aa-9713-e3d634e5336b", "CASHIN VIA PIX", Statement.TransactionType.PIX, "2021-01-27T08:10:12", 120.32f, Statement.Type.CREDIT);
		UserService.addUserStatement(statement0);

		Statement statement1 = new Statement("fa4b8041-81e8-47aa-9713-e3d634e5336b", "CASHIN VIA PIX", Statement.TransactionType.PIX, "2021-01-27T08:10:12", 120.32f, Statement.Type.CREDIT);
		UserService.addUserStatement(statement1);

		Assertions.assertEquals(user.getUserStatements().size(), 1);
	}

	@Test
	void expireUserStatements() {
		User user = User.getUser();
		user.clearStatements();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String today = sdf.format(new Date());

		Statement statement0 = new Statement("fa4b8041-81e8-47aa-9713-e3d634e5336b", "CASHIN VIA PIX", Statement.TransactionType.PIX, "2021-01-20T08:10:12", 50.0f, Statement.Type.CREDIT);
		Statement statement1 = new Statement("fa4b8041-81e8-47aa-9713-e3d634e53aaa", "CASHIN VIA PIX", Statement.TransactionType.PIX, today, 120.32f, Statement.Type.CREDIT);

		statement0.setVisited(true);
		statement1.setVisited(true);

		UserService.addUserStatement(statement0);
		UserService.addUserStatement(statement1);

		UserService.expireStatements();

		Assertions.assertEquals(user.getUserStatements().size(), 1);
	}

}
