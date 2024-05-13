package com.nhnacademy.shop.category.exception;
/**
 * 카테고리가 이미 존재할 때 발생하는 Exception 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
public class CategoryAlreadyExistsException extends RuntimeException{
    public CategoryAlreadyExistsException() {
        super("이미 존재하는 카테고리입니다.");
    }
}
