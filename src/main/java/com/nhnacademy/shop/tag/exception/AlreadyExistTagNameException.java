package com.nhnacademy.shop.tag.exception;

/**
 * AlreadyExistTagName Exception
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
public class AlreadyExistTagNameException extends RuntimeException {
    public AlreadyExistTagNameException(String tagName) {
        super(tagName + "이 이미 존재합니다.");
    }
}
