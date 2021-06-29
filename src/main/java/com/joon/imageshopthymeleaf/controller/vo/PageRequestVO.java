package com.joon.imageshopthymeleaf.controller.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
@Getter
@Setter
public class PageRequestVO {
    private int page;
    private int sizePerPage;

    private String searchType;
    private String keyword;
    public PageRequestVO() {
        page=1;
        sizePerPage=10;
    }
    public void setPage(int page){
        if(page<=0){
            this.page=1;
            return;
        }
        this.page=page;
    }
    public void setSizePerPage(int size){
        if(size<=0 || size>100){
            this.sizePerPage=10;
            return;
        }
        this.sizePerPage=size;
    }

    public int getPageStart(){
        return (this.page-1)* sizePerPage;
    }
    public String toUriStringByPage(int page){
        UriComponents uriComponents= UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("size", sizePerPage)
                .queryParam("searchType", searchType)
                .queryParam("keyword", keyword)
                .build();
        return  uriComponents.toUriString();
    }
    public String toUriString(){
        return toUriStringByPage(this.page);
    }
    public String toUriStringForSearchAction(int page){
        UriComponents uriComponents=UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .build();
        return uriComponents.toUriString();

    }

}
