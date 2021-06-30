package com.joon.imageshopthymeleaf.entity;

import com.joon.imageshopthymeleaf.common.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long itemId;

    @Column(length = 50, nullable = false)
    private String itemName;

    private Integer price;

    private String description;

    @Transient
    MultipartFile picture;

    private String pictureUrl;

    @Transient
    private MultipartFile preview;

    @Column
    private String previewUrl;

    public void update(String itemName, Integer price, String description, String pictureUrl, String previewUrl) {
        this.itemName=itemName;
        this.price=price;
        this.description=description;
        this.pictureUrl=pictureUrl;
        this.previewUrl=previewUrl;
    }
}
