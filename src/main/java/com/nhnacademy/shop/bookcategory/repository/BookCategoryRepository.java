package com.nhnacademy.shop.bookcategory.repository;

import com.nhnacademy.shop.book.entity.Book;
import com.nhnacademy.shop.bookcategory.domain.BookCategory;
import com.nhnacademy.shop.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategory.Pk> {
    Page<Book> findByCategory(Pageable pageable, Category category);
}
