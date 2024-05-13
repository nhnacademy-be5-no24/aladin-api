package com.nhnacademy.shop.book.repository.impl;

import com.nhnacademy.shop.book.dto.response.BookResponseDto;
import com.nhnacademy.shop.book.entity.Book;
import com.nhnacademy.shop.book.entity.QBook;
import com.nhnacademy.shop.book.exception.BookNotFoundException;
import com.nhnacademy.shop.book.repository.BookRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import java.util.Objects;


/**
 * 도서관리 repository에서 query dsl을 사용하기 위한 custom repository 구현체
 *
 * @author : 이재원
 * @date : 2024-03-11
 */
public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {

    private final EntityManager entityManager;

    public BookRepositoryImpl(EntityManager entityManager){
        super(Book.class);
        this.entityManager = entityManager;
    }

    @Override
    public Page<BookResponseDto> findAllBooks(Pageable pageable) {
        QBook book = QBook.book;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        JPAQuery<BookResponseDto> query = queryFactory
                .from(book)
                .select(Projections.constructor(BookResponseDto.class,
                        book.bookIsbn, book.bookTitle, book.bookDesc, book.bookPublisher, book.bookPublishedAt,
                        book.bookFixedPrice, book.bookSalePrice, book.bookIsPacking, book.bookViews,
                        book.bookStatus, book.bookQuantity, book.bookImage, book.tags, book.categories, book.author, book.likes
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Long count = queryFactory.select(book.count()).from(book).fetchOne();

        if(Objects.isNull(count)){
            throw new BookNotFoundException();
        }

        return new PageImpl<>(query.fetch(),pageable, count);
    }
    @Override
    public Page<BookResponseDto> findBooksByBookTitle(Pageable pageable, String bookTitle){
        QBook book = QBook.book;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        JPAQuery<BookResponseDto> query = queryFactory.from(book)
                .select(Projections.constructor(BookResponseDto.class,
                        book.bookIsbn, book.bookDesc, book.bookPublisher, book.bookPublishedAt,
                        book.bookIsPacking, book.bookImage, book.bookStatus, book.bookFixedPrice,
                        book.bookQuantity, book.bookSalePrice, book.bookTitle, book.bookViews))
                .where(book.bookTitle.like(bookTitle))
                .orderBy(book.bookIsbn.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Long count = queryFactory.select(book.count())
                .where(book.bookTitle.like(bookTitle))
                .from(book)
                .fetchOne();

        if(Objects.isNull(count)){
            throw new BookNotFoundException();
        }

        return new PageImpl<>(query.fetch(), pageable, count);
    }

    @Override
    public Page<BookResponseDto> findBooksByBookDesc(Pageable pageable, String desc){
        QBook book = QBook.book;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        JPAQuery<BookResponseDto> query = queryFactory.from(book)
                .select(Projections.constructor(BookResponseDto.class,
                        book.bookIsbn, book.bookDesc, book.bookPublisher, book.bookPublishedAt,
                        book.bookIsPacking, book.bookImage, book.bookStatus, book.bookFixedPrice,
                        book.bookQuantity, book.bookSalePrice, book.bookTitle, book.bookViews))
                .where(book.bookDesc.like(desc))
                .orderBy(book.bookIsbn.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Long count = queryFactory.select(book.count())
                .where(book.bookDesc.like(desc))
                .from(book)
                .fetchOne();

        if(Objects.isNull(count)){
            throw new BookNotFoundException();
        }


        return new PageImpl<>(query.fetch(), pageable, count);
    }


}
