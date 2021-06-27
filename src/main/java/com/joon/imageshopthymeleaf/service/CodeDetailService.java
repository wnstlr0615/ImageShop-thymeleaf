package com.joon.imageshopthymeleaf.service;

import com.joon.imageshopthymeleaf.common.model.CodeLabelValue;
import com.joon.imageshopthymeleaf.dto.CodeDetailDto;
import com.joon.imageshopthymeleaf.entity.CodeDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CodeDetailService {
    void register(CodeDetailDto codeDetailDto);
    /**
     * 코드 목록
     * */
    List<CodeDetail> list();
    /**
     * 코드 조회 -> DTO로 변환
     * */
    CodeDetailDto findByGroupAndCodeValue(String groupCode, String codeValue);
    /**
     * 코드 삭제
     * */
    void remove(String groupCode, String codeValue);
    /**
     * 코드 정보 업데이트
     * */
    void update(CodeDetailDto codeDetailDto);
    /**
     * 코드 리스트 조회
    * */
    List<CodeLabelValue> getCodeList(String classCode);
}
