package com.joon.imageshopthymeleaf.repository;

import com.joon.imageshopthymeleaf.entity.Board;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.joon.imageshopthymeleaf.entity.QBoard.board;
@RequiredArgsConstructor
@Repository
public class BorderCustomRepositoryImpl implements CustomBoardRepository {
     private final JPAQueryFactory query;
    private final EntityManager em;


    @Override
    public Page<Board> getSearchPage(String type, String keyword, Pageable pageable) {

        JPAQuery<Board> boardJPAQuery = query.selectFrom(board);
        BooleanBuilder builder = new BooleanBuilder();
       if (type!=null){
           switch (type) {
               case "t":
                   boardJPAQuery.where(board.title.contains(keyword));
                   break;
               case "w":
                   boardJPAQuery.where(board.writer.contains(keyword));
                   break;
               case "c":
                   boardJPAQuery.where(board.content.contains(keyword));
                   break;
               case "tc":
                   builder.and(board.title.contains(keyword))
                           .and(board.content.contains(keyword));
                   boardJPAQuery.where(builder);
                   break;
               case "cw":
                   builder.and(board.writer.contains(keyword))
                           .and(board.content.contains(keyword));
                   boardJPAQuery.where(builder);
                   break;
               case "tcw":
                   builder.and(board.title.contains(keyword))
                           .or(board.writer.contains(keyword))
                           .or(board.content.contains(keyword));
                   boardJPAQuery.where(builder);
                   break;
           }
       }
        boardJPAQuery.offset(pageable.getOffset());
        boardJPAQuery.limit(pageable.getPageSize());
        List<Board> boardList = boardJPAQuery.fetch();
        long total = boardList.size();
        return new PageImpl<>(boardList, pageable, total);
    }


}
