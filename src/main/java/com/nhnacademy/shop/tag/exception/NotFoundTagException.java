package com.nhnacademy.shop.tag.exception;

/**
 * TagId Exception
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
public class NotFoundTagException extends RuntimeException  {
    public NotFoundTagException(Long tagId) {
        super(tagId + "가 존재하지 않습니다.");
    }
}
