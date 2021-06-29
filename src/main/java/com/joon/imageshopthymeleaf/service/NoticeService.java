package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.entity.Notice;

import java.util.List;

public interface NoticeService {
    void register(Notice notice);

    List<Notice> list();

    Notice read(Long noticeId);

    void remove(Long noticeId);

    void modify(Notice notice);
}
