package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CodeGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long codeGroupId;

    @Column(length = 3, nullable = false, unique = true)
    private String groupCode;

    @Column(length = 30, nullable = false)
    private String groupName;

    @Column(length = 1)
    private String useYn="Y";

    public void updateGroupName(CodeGroup codeGroup) {
        this.groupName=codeGroup.groupName;
    }
}
