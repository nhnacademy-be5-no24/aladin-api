package com.nhnacademy.shop.book.exception;

/**
 * 도서관리 Exception
 * 해당 도서가 삭제된 상태일 경우
 *
 * @author : 이재원
 * @date : 2024-04-11
 */
public class BookIsDeletedException extends RuntimeException{
    public BookIsDeletedException(){
        super("This book is deleted");
    }
}
