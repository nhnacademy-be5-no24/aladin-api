package com.nhnacademy.shop.book_tag.domain;

import com.nhnacademy.shop.book.entity.Book;
import com.nhnacademy.shop.tag.domain.Tag;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookTag {
    @EmbeddedId
    private Pk pk;

    @MapsId(value = "bookIsbn")
    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private Book book;

    @MapsId(value = "tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "book_isbn")
        private String bookIsbn;
        @Column(name = "tag_id")
        private Long tagId;

    }
}
