package com.nhnacademy.shop.book_tag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookTag_Pk is a Querydsl query type for Pk
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBookTag_Pk extends BeanPath<BookTag.Pk> {

    private static final long serialVersionUID = -191961177L;

    public static final QBookTag_Pk pk = new QBookTag_Pk("pk");

    public final StringPath bookIsbn = createString("bookIsbn");

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public QBookTag_Pk(String variable) {
        super(BookTag.Pk.class, forVariable(variable));
    }

    public QBookTag_Pk(Path<? extends BookTag.Pk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookTag_Pk(PathMetadata metadata) {
        super(BookTag.Pk.class, metadata);
    }

}

