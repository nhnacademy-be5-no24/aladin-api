package com.nhnacademy.shop.book.entity;


import com.nhnacademy.shop.book_tag.domain.BookTag;
import com.nhnacademy.shop.bookcategory.domain.BookCategory;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


/**
 * 도서관리 Entity
 *
 * @author : 이재원
 * @date : 2024-03-27
 */
@Entity
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_isbn", nullable = false)
    private String bookIsbn;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(name = "book_desc", nullable = false)
    private String bookDesc;

    @Column(name = "book_publisher", nullable = false)
    private String bookPublisher;

    @Column(name = "book_published_at", nullable = false)
    private LocalDate bookPublishedAt;

    @Column(name = "book_fixed_price", nullable = false)
    private long bookFixedPrice;

    @Column(name = "book_sale_price", nullable = false)
    private long bookSalePrice;

    @Column(name = "book_is_packing", nullable = false)
    private boolean bookIsPacking;

    @Column(name = "book_views", nullable = false)
    private Long bookViews;

    /*
     * Express "book_status" to integer
     * 0 : 판매중
     * 1 : 수량부족
     * 2 : 판매종료
     * 3 : 삭제된 도서
     */
    @Column(name = "book_status", nullable = false)
    private int bookStatus;

    @Column(name = "book_quantity", nullable = false)
    private int bookQuantity;

    @Column(name = "book_image")
    private String bookImage;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookCategory> categories;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookTag> tags;

    @Column(name = "author")
    private String author;

    private Long likes;

}