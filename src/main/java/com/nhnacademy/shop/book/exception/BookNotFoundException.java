package com.nhnacademy.shop.book.exception;

/**
 * 도서관리 Exception 
 * 해당 도서가 존재하지 않을 경우
 *
 * @author : 이재원
 * @date : 2024-03-30
 */
public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(){
        super("This book is not found");
    }
}
