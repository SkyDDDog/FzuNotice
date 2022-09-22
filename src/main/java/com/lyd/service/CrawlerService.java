package com.lyd.service;

import com.lyd.entity.Notice;
import com.lyd.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CrawlerService {

    public String getNoticeHTML() throws IOException, ParseException {
        Integer cnt = 0;
        String pre = "<ul>";
        String suf = "</ul>";
        StringBuilder html = new StringBuilder(pre);
        String prefix = "https://jwch.fzu.edu.cn/";
        Document document = Jsoup.parse(new URL("https://jwch.fzu.edu.cn/jxtz.htm"), 30000);
        Elements box = document.getElementsByClass("box-gl clearfix");
        Elements li = box.get(0).getElementsByTag("li");
        for (Element el : li) {
            Elements span = el.getElementsByTag("span");
            String date = span.get(0).getElementsByClass("doclist_time").get(0).wholeText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(date);
            Date weekBeg = DateUtils.getWeekBeg();
            Date weekEnd = DateUtils.getWeekEnd();
            if (parse.after(weekBeg) && parse.before(weekEnd)) {
                cnt++;
                Elements a = el.getElementsByTag("a");
                String href = a.get(0).attr("href");
                String infoUrl = prefix + href;
                el.getElementsByTag("a").get(0).attr("href",infoUrl);
                html.append(el.outerHtml());
            } else {
                if (cnt==0) {
                    String noNotice = "  <h2 style=\"margin:auto\">\n" +
                            "    \t<span style=\"font-size:15px;text-align:center\">本周没有通知</span>\n" +
                            "  </h2>";
                    html.append(noNotice);
                }
                break;
            }
        }
        html.append(suf);
        return html.toString();
    }

    public List<Notice> getNoticeObj() throws IOException, ParseException {
        ArrayList<Notice> result = new ArrayList<>();
        String prefix = "https://jwch.fzu.edu.cn/";
        Document document = Jsoup.parse(new URL("https://jwch.fzu.edu.cn/jxtz.htm"), 30000);
        Elements box = document.getElementsByClass("box-gl clearfix");
        Elements li = box.get(0).getElementsByTag("li");
        for (Element el : li) {
            Notice notice = new Notice();

            Elements a = el.getElementsByTag("a");
            String href = a.get(0).attr("href");
            String infoUrl = prefix + href;
            String substring = infoUrl.substring(0, infoUrl.length()-4);
            String[] split = substring.split(String.valueOf('/'));

            Elements span = el.getElementsByTag("span");
            String date = span.get(0).getElementsByClass("doclist_time").get(0).wholeText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sdf.parse(date);
            String content = el.text().substring(11);


            notice.setDate(parse);
            notice.setContent(content);
            notice.setNotice_id(split[5]);
            notice.setSection_id(split[4]);
            notice.setLink(infoUrl);
            result.add(notice);
        }
//        log.info(result.toString());
        return result;
    }

}
