package com.nhnacademy.shop.book.exception;

/**
 * 도서관리 Exception
 * 해당 도서가 이미 존재하는 경우
 *
 * @author : 이재원
 * @date : 2024-03-30
 */
public class BookAlreadyExistsException extends RuntimeException{

    public BookAlreadyExistsException(){
        super("This book is already exist.");
    }
}
