package com.nhnacademy.shop.tag.service;

import com.nhnacademy.shop.tag.dto.CreateTagRequestDto;
import com.nhnacademy.shop.tag.dto.TagResponseDto;

/**
 * TagService
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
public interface TagService {
    TagResponseDto getTagById(Long tagId);
    TagResponseDto getTagByName(String tagName);
    TagResponseDto saveTag(CreateTagRequestDto createTagRequestDto);

    void deleteTagByName(String tagName);

    void deleteTagById(Long tagId);
}
