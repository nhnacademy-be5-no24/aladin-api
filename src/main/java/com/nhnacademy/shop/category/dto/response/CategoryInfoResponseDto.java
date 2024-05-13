package com.nhnacademy.shop.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 카테고리 기본정보를 반환하기 위한 dto 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoResponseDto {
    private Long categoryId;
    private String categoryName;
}
