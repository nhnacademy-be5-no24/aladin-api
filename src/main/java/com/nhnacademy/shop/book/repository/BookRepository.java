package com.nhnacademy.shop.book.repository;

import com.nhnacademy.shop.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 도서관리 repository
 *
 * @author : 이재원
 * @date : 2024-03-27
 */
public interface BookRepository extends JpaRepository<Book, String>, BookRepositoryCustom {

    Optional<Book> findByBookIsbn(String bookIsbn);

}
