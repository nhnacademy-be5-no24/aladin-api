package com.nhnacademy.shop.category.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 상위 카테고리 기본정보 반환을 위한 dto 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
@Data
@NoArgsConstructor
public class ParentCategoryResponseDto {
    private Long categoryId;
    private String categoryName;

    private List<ChildCategoryResponseDto> childCategories;

    public ParentCategoryResponseDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
