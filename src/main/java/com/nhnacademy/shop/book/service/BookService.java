package com.nhnacademy.shop.book.service;

import com.nhnacademy.shop.book.dto.request.BookCreateRequestDto;
import com.nhnacademy.shop.book.dto.request.BookRequestDto;
import com.nhnacademy.shop.book.dto.response.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/*
 * TagRepository 에 접근하기 위한 service interface
 *
 * @Author : 이재원
 * @Date : 28/03/2024
 *
 */
@Service
public interface BookService {

    BookResponseDto createBook(BookCreateRequestDto request);

    BookResponseDto deleteBook(String bookIsbn);

    Page<BookResponseDto> findAllBooks(Pageable pageable);

    Page<BookResponseDto> findByCategoryId(Pageable pageable,Long categoryId);

    Page<BookResponseDto> findByTitle(Pageable pageable, String bookName);

    BookResponseDto findByIsbn(String bookIsbn);

    Page<BookResponseDto> findByDescription(Pageable pageable, String bookDescription);

    Page<BookResponseDto> findByTag(Pageable pageable, Long tagId);

    BookResponseDto modifyBook(BookRequestDto bookRequestDto);

    BookResponseDto modifyBookStatus(String bookIsbn, int bookStatus);





}