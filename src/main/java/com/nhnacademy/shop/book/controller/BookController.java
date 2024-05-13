package com.nhnacademy.shop.book.controller;

import com.nhnacademy.shop.book.dto.request.BookCreateRequestDto;
import com.nhnacademy.shop.book.dto.request.BookRequestDto;
import com.nhnacademy.shop.book.dto.response.BookResponseDto;
import com.nhnacademy.shop.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


/**
 * 도서관리 RestController
 *
 * @author : 이재원
 * @date : 2024-03-31
 */
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     *  새로운 book을 생성합니다.
     *
     * @param request
     * @throws ResponseStatusException 해당 DTO를 통해 이미 존재하는 경우 응답코드 208 ALREADY_REPORTED
     * @return 성공했을 때 응답코드 201 CREATED
     */
    @PostMapping("/books/page")
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookCreateRequestDto request){
        BookResponseDto book = bookService.createBook(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    /**
     * book isbn을 통해서 book을 삭제된 도서로 만듭니다.
     *
     * @param bookIsbn
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @DeleteMapping("/books/{bookIsbn}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable String bookIsbn){
        BookResponseDto book = bookService.deleteBook(bookIsbn);
        if(Objects.isNull(book)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    /**
     * 페이징 처리 된
     * 모든 book을 반환합니다.
     *
     * @param pageable
     * @return all books
     */
    @GetMapping("/books")
    public ResponseEntity<Page<BookResponseDto>> getAllBooks(Pageable pageable){
        Page<BookResponseDto> books = bookService.findAllBooks(pageable);
        if(Objects.isNull(books)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * book isbn을 통해서 book을 기존의 book 정보를 가져오는 method
     *
     * @param  bookIsbn
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @GetMapping("/books/{bookIsbn}")
    public ResponseEntity<BookResponseDto> getByIsbn(@PathVariable String bookIsbn){
        BookResponseDto book = bookService.findByIsbn(bookIsbn);
        if(Objects.isNull(book)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    /**
     * book category을 통해서 book의 정보를 가져옵니다.
     *
     * @param categoryId
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @GetMapping("/books/{categoryId}")
    public ResponseEntity<Page<BookResponseDto>> getBookByCategory(@PathVariable Long categoryId, Pageable pageable){
        Page<BookResponseDto> books = bookService.findByCategoryId(pageable, categoryId);
        if(Objects.isNull(books)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * book title을 통해 해당 book을 찾습니다.
     *
     * @param bookName
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @GetMapping("/books/{bookName}")
    public ResponseEntity<Page<BookResponseDto>> getBookByName(@PathVariable String bookName, Pageable pageable){
        Page<BookResponseDto> books = bookService.findByTitle(pageable, bookName);
        if(Objects.isNull(books)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * book description을 통해서 book을 찾습니다.
     *
     * @param bookDescription
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @GetMapping("/books/{bookDescription}")
    public ResponseEntity<Page<BookResponseDto>> getByDesc(@PathVariable String bookDescription, Pageable pageable){
        Page<BookResponseDto> books = bookService.findByDescription(pageable, bookDescription);
        if(Objects.isNull(books)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * Tag을 통해서 book을 찾습니다.
     *
     * @param tagId
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @GetMapping("/books/{tagId}")
    public ResponseEntity<Page<BookResponseDto>> getByTag(@PathVariable Long tagId, Pageable pageable){
        Page<BookResponseDto> books = bookService.findByTag(pageable, tagId);
        if(Objects.isNull(books)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    /**
     * book 의 정보를 수정합니다.
     *
     * @param bookRequestDto
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @PutMapping("/books")
    public ResponseEntity<BookResponseDto> modifyBook(@RequestBody BookRequestDto bookRequestDto){
        BookResponseDto book = bookService.modifyBook(bookRequestDto);
        if(Objects.isNull(book)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GET : Any Book NOT FOUND");
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    /**
     * book ISBN을 통해서 book의 status를 변경합니다.
     *
     * @param bookIsbn, bookStatus
     * @throws ResponseStatusException book ISBN으로 조회했을 때 찾을 수 없을 경우 응답코드 404 NOT_FOUND
     * @return 성공했을 때 응답코드 200 OK
     */
    @PutMapping("/books/status")
    public ResponseEntity<BookResponseDto> modifyBookStatus(String bookIsbn, int bookStatus){
        BookResponseDto book = bookService.modifyBookStatus(bookIsbn, bookStatus);
        if(Objects.isNull(book)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GET : Any Book NOT FOUND");
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

}
