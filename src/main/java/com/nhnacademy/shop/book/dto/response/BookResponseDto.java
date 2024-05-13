package com.nhnacademy.shop.book.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.shop.book_tag.domain.BookTag;
import com.nhnacademy.shop.bookcategory.domain.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 도서관리 Response DTO
 *
 * @author : 이재원
 * @date : 2024-03-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {

    @JsonProperty("book_isbn")
    private String bookIsbn;

    @JsonProperty("book_title")
    private String bookTitle;

    @JsonProperty("book_description")
    private String bookDescription;

    @JsonProperty("book_publisher")
    private String bookPublisher;

    @JsonProperty("book_published_at")
    private LocalDate publishedAt;

    @JsonProperty("book_fixed_price")
    private long bookFixedPrice;

    @JsonProperty("book_sale_price")
    private long bookSalePrice;

    @JsonProperty("book_is_packing")
    private boolean bookIsPacking;

    @JsonProperty("book_views")
    private Long bookViews;

    @JsonProperty("book_status")
    private int bookStatus;

    @JsonProperty("book_quantity")
    private int bookQuantity;

    @JsonProperty("book_image")
    private String bookImage;

    private List<BookTag> tags;

    private String author;

    private List<BookCategory> categories;

    private Long likes;

}
