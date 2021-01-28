package com.binobank.digitalwallet.controller;

import com.binobank.digitalwallet.model.Statement;
import com.binobank.digitalwallet.model.User;
import com.binobank.digitalwallet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/user")
    public Map<String, String> user() {
        HashMap<String, String> response = new HashMap<>();
        response.put("balance", String.valueOf(User.getUser().getBalance()));
        return response;
    }

    @PostMapping("/user/statement")
    public void addStatement(@RequestBody @Valid Statement statement) {
        UserService.addUserStatement(statement);
    }

    @PostMapping("/user/recalculation")
    @ResponseStatus(HttpStatus.OK)
    public void recalculateBalance() {
        UserService.calculateBalance();
    }
}
