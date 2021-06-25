package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.CodeDetail;
import com.joon.imageshopthymeleaf.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, Long> {
    int countByCodeGroup(CodeGroup codeGroup);

    Optional<CodeDetail> findByCodeGroupAndCodeValue(CodeGroup codeGroup, String codeValue);

    void deleteByCodeGroupAndCodeValue(CodeGroup codeGroup, String codeValue);
}
