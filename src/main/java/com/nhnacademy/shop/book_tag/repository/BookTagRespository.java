package com.nhnacademy.shop.book_tag.repository;

import com.nhnacademy.shop.book.entity.Book;
import com.nhnacademy.shop.book_tag.domain.BookTag;
import com.nhnacademy.shop.tag.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookTagRespository extends JpaRepository<BookTag, BookTag.Pk> {
    Page<Book> findByTag(Pageable pageable, Tag tag);

}
