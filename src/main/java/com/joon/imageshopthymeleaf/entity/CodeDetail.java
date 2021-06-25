package com.joon.imageshopthymeleaf.entity;
import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import com.joon.imageshopthymeleaf.dto.CodeDetailDto;
import lombok.*;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDetail extends BaseTimeEntity  {
    @Id
    @GeneratedValue
    private Long codeDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codeGroupId")
    CodeGroup codeGroup;

    @Column(length = 3)
    private String codeValue;

    @Column(nullable = false, length = 30)
    private String codeName;

    private int sortSeq;

    @Column(length = 1)
    private String useYn="Y";

    public static CodeDetail of(CodeDetailDto codeDetailDto, CodeGroup codeGroup, int seqByGroup) {
        return CodeDetail.builder()
                .codeName(codeDetailDto.getCodeName())
                .codeGroup(codeGroup)
                .codeValue(codeDetailDto.getCodeValue())
                .useYn("Y")
                .sortSeq(seqByGroup+1)
                .build();
    }

    public void update(CodeGroup codeGroup, String codeName, String codeValue) {
        this.codeGroup=codeGroup;
        this.codeName=codeName;
        this.codeValue=codeValue;
    }
}
