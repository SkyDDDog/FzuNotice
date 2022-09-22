package com.lyd.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyd.entity.Notice;
import com.lyd.mapper.NoticeMapper;
import com.lyd.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class NoticeSerivce {

    @Resource
    private NoticeMapper noticeMapper;

    public void insertNotice(Notice notice) {
        noticeMapper.insert(notice);
    }

    public void insertList(List<Notice> notices) {
        for (Notice notice : notices) {
            noticeMapper.insert(notice);
        }
    }

    public List<Notice> getNoticeByIds(String noticeId,String sectionId) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("notice_id",noticeId);
        wrapper.eq("section_id",sectionId);
        List<Notice> notices = noticeMapper.selectList(wrapper);
        return notices;
    }

    public boolean isExist(String noticeId,String sectionId) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("notice_id",noticeId);
        wrapper.eq("section_id",sectionId);
        List<Notice> notices = noticeMapper.selectList(wrapper);
        if (notices.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<Notice> getThisWeekNotice() {
        Date weekBeg = DateUtils.getWeekBeg();
        Date weekEnd = DateUtils.getWeekEnd();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String beg = sdf.format(weekBeg);
        String end = sdf.format(weekEnd);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.between("date",beg,end);
        List<Notice> notices = noticeMapper.selectList(wrapper);
        return notices;
    }

}
