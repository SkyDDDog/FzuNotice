package com.lyd;

import com.lyd.entity.Notice;
import com.lyd.service.CrawlerService;
import com.lyd.service.CronService;
import com.lyd.service.MailService;
import com.lyd.service.NoticeSerivce;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@SpringBootTest
class FzuCrawlerApplicationTests {

    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CronService cronService;
    @Autowired
    private NoticeSerivce noticeSerivce;

    @Test
    void contextLoads() throws IOException, ParseException {
//        List<Notice> byFzu = crawlerService.getNoticeByFzu();
//        crawlerService.getNoticeHTML();
//        log.info(byFzu.toString());
//        mailService.sendMail("362664609@qq.com");
//        cronService.sendNoticeMail();
//        crawlerService.getNoticeObj();
//        noticeSerivce.getThisWeekNotice();
        List<Notice> noticeObj = crawlerService.getNoticeObj();
        noticeSerivce.insertList(noticeObj);
    }

}
