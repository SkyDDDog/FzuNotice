package com.lyd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@EnableScheduling
@Configuration
@Service
public class CronService {

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 * * * *")
//    @Scheduled(cron = "0 55 21 ? * *")
    public void sendNoticeMailByWeek() {
//        mailService.sendMail("362664609@qq.com","2320747195@qq.com","1753570934@qq.com","1650574530@qq.com");
        mailService.sendMail("362664609@qq.com");
    }

    @Scheduled(cron = "")
    public void sendNoticeMailByDay () {

    }

}
