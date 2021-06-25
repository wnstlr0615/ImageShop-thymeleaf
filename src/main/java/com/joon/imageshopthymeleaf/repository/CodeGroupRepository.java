package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeGroupRepository  extends JpaRepository<CodeGroup, Long> {

    Optional<CodeGroup> findByGroupCode(String groupCode);

    void deleteByGroupCode(String groupCode);
}
