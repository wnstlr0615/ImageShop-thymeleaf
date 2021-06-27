package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
