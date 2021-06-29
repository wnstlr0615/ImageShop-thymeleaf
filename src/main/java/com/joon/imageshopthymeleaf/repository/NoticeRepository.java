package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
