package com.nhnacademy.shop.book.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = 1883086462L;

    public static final QBook book = new QBook("book");

    public final StringPath author = createString("author");

    public final StringPath bookDesc = createString("bookDesc");

    public final NumberPath<Long> bookFixedPrice = createNumber("bookFixedPrice", Long.class);

    public final StringPath bookImage = createString("bookImage");

    public final StringPath bookIsbn = createString("bookIsbn");

    public final BooleanPath bookIsPacking = createBoolean("bookIsPacking");

    public final DatePath<java.time.LocalDate> bookPublishedAt = createDate("bookPublishedAt", java.time.LocalDate.class);

    public final StringPath bookPublisher = createString("bookPublisher");

    public final NumberPath<Integer> bookQuantity = createNumber("bookQuantity", Integer.class);

    public final NumberPath<Long> bookSalePrice = createNumber("bookSalePrice", Long.class);

    public final NumberPath<Integer> bookStatus = createNumber("bookStatus", Integer.class);

    public final StringPath bookTitle = createString("bookTitle");

    public final NumberPath<Long> bookViews = createNumber("bookViews", Long.class);

    public final ListPath<com.nhnacademy.shop.bookcategory.domain.BookCategory, com.nhnacademy.shop.bookcategory.domain.QBookCategory> categories = this.<com.nhnacademy.shop.bookcategory.domain.BookCategory, com.nhnacademy.shop.bookcategory.domain.QBookCategory>createList("categories", com.nhnacademy.shop.bookcategory.domain.BookCategory.class, com.nhnacademy.shop.bookcategory.domain.QBookCategory.class, PathInits.DIRECT2);

    public final NumberPath<Long> likes = createNumber("likes", Long.class);

    public final ListPath<com.nhnacademy.shop.book_tag.domain.BookTag, com.nhnacademy.shop.book_tag.domain.QBookTag> tags = this.<com.nhnacademy.shop.book_tag.domain.BookTag, com.nhnacademy.shop.book_tag.domain.QBookTag>createList("tags", com.nhnacademy.shop.book_tag.domain.BookTag.class, com.nhnacademy.shop.book_tag.domain.QBookTag.class, PathInits.DIRECT2);

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

