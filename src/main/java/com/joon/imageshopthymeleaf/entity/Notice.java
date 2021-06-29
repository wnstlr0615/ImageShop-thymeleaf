package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@ToString
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long noticeId;

    @Column(length = 200,nullable = false)
    private String title;

    @Lob
    private String content;

    public void update(String title, String content) {
        this.title=title;
        this.content=content;
    }
}
