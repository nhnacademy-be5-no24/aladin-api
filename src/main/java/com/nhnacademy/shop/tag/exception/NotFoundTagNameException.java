package com.nhnacademy.shop.tag.exception;

/**
 * TagName Exception
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
public class NotFoundTagNameException extends RuntimeException  {
    public NotFoundTagNameException(String tagName) {
        super(tagName + "가 존재하지 않습니다.");
    }
}
