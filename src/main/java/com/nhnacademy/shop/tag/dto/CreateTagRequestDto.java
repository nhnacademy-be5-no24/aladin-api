package com.nhnacademy.shop.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateTagRequestDto
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTagRequestDto {
    private String tagName;
}
