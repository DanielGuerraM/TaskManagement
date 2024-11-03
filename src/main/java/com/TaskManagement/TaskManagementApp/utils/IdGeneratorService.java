package com.TaskManagement.TaskManagementApp.utils;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class IdGeneratorService {
    //yyyymmdd-consecutive-type
    public Long generateId(IdTypes type) {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateFormated = format.format(currentDate);
        Random random = new Random();

        int secuentialNumber = 1000 + random.nextInt(90000);

        String formattedSequentialNumber = String.format("%05d", secuentialNumber);

        return Long.parseLong(dateFormated + formattedSequentialNumber + setType(type));
    }

    private String setType(IdTypes type) {
        return switch (type) {
            case CATEGORY -> "10";
            case TASK -> "20";
            case USER -> "30";
        };
    }
}