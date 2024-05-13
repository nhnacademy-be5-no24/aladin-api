package com.nhnacademy.shop.bookcategory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookCategory_Pk is a Querydsl query type for Pk
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBookCategory_Pk extends BeanPath<BookCategory.Pk> {

    private static final long serialVersionUID = -434902064L;

    public static final QBookCategory_Pk pk = new QBookCategory_Pk("pk");

    public final StringPath bookIsbn = createString("bookIsbn");

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public QBookCategory_Pk(String variable) {
        super(BookCategory.Pk.class, forVariable(variable));
    }

    public QBookCategory_Pk(Path<? extends BookCategory.Pk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookCategory_Pk(PathMetadata metadata) {
        super(BookCategory.Pk.class, metadata);
    }

}

