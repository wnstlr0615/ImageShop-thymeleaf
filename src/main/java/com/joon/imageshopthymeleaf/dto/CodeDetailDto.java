package com.joon.imageshopthymeleaf.dto;

import com.joon.imageshopthymeleaf.entity.CodeDetail;
import com.joon.imageshopthymeleaf.entity.CodeGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDetailDto {
    private String groupCode;
    @Column(length = 3)
    private String codeValue;

    @Column(nullable = false, length = 30)
    private String codeName;
    private int sortSeq;

    @Column(length = 1)
    private String useYn="Y";

    public static CodeDetailDto of(CodeDetail codeDetail) {
        return CodeDetailDto.builder()
                .codeName(codeDetail.getCodeName())
                .codeValue(codeDetail.getCodeValue())
                .groupCode(codeDetail.getCodeGroup().getGroupCode())
                .build();
    }
}
