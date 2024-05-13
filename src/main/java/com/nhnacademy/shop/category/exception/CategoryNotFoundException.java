package com.nhnacademy.shop.category.exception;
/**
 * 카테고리를 찾지 못 했을 때 발생하는 Exception 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException() {
        super("해당 카테고리를 찾을 수 없습니다.");
    }
}
