package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.entity.Notice;
import com.joon.imageshopthymeleaf.repository.NoticeRepository;
import com.joon.imageshopthymeleaf.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public void register(Notice notice) {
        noticeRepository.save(notice);
    }

    @Override
    public List<Notice> list() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "noticeId"));
    }

    @Override
    public Notice read(Long noticeId) {
        return noticeRepository.findById(noticeId).get();   //예외 처리 생략
    }

    @Override
    @Transactional
    public void remove(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    @Override
    @Transactional
    public void modify(Notice notice) {
        Notice noticeEntity = noticeRepository.findById(notice.getNoticeId()).get();
        noticeEntity.update(notice.getTitle(), notice.getContent());
    }
}
