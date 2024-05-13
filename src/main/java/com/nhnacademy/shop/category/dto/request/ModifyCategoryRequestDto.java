package com.nhnacademy.shop.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 카테고리 수정을 위한 dto 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyCategoryRequestDto {
    private Long categoryId;
    private String categoryName;
    private Long parentCategoryId;
}
