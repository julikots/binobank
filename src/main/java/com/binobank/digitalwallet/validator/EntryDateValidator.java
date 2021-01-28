package com.binobank.digitalwallet.validator;

import com.binobank.digitalwallet.annotation.EntryDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EntryDateValidator implements ConstraintValidator<EntryDate,String> {

    public boolean isValid(String entryDate, ConstraintValidatorContext cvc) {
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            sdfrmt.parse(entryDate);
        } catch (ParseException parseException) {
            return false;
        }

        return true;
    }
}
