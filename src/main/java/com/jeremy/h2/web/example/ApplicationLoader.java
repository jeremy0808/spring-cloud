package com.jeremy.h2.web.example;

import com.jeremy.h2.web.service.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
@Component
public class ApplicationLoader implements CommandLineRunner {

    @Autowired
    private TimeService timeService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public void run(String... args) throws Exception {

        for(int i = 0 ; i < 30 ; i++){
            System.out.println(sdf.format(timeService.getCurrentTimeMillis()));
            Thread.sleep(1000);
        }
    }
}
