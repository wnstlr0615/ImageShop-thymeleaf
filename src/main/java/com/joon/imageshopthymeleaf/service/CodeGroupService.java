package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.common.model.CodeLabelValue;
import com.joon.imageshopthymeleaf.entity.CodeGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CodeGroupService {
    /**
     * 코드 그룹 등록
     * */
    void register(CodeGroup codeGroup);

    /**
     * 코드 목록 조회
     * */
    List<CodeGroup> list();

    /**
     * 코드 그룹 조회
     * */
    CodeGroup getGroupCode(String groupCode);

    /**
     * 코드 그룹 삭제
     * */
    void remove(String groupCode);

    /**
     * 코드 그룹 수정
     * */
    void update(CodeGroup codeGroup);
     List<CodeLabelValue> getCodeGroupList();

}
