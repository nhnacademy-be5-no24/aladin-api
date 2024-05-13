package com.nhnacademy.shop.category.service;

import com.nhnacademy.shop.category.dto.request.CreateCategoryRequestDto;
import com.nhnacademy.shop.category.dto.request.ModifyCategoryRequestDto;
import com.nhnacademy.shop.category.dto.response.CategoryInfoResponseDto;
import com.nhnacademy.shop.category.dto.response.CategoryResponseDto;
import com.nhnacademy.shop.category.dto.response.ParentCategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 카테고리 서비스입니다.
*
* @author : 강병구
* @date : 2024-03-29
 */
@Service
public interface CategoryService {

    /**
     * 카테고리 전체 조회를 위한 메소드입니다.
     *
     * @return CategoryResponseDto 카테고리 정보 페이지가 반환됩니다.
     */
    Page<CategoryResponseDto> getCategories(Integer pageSize, Integer offset);

    /**
     * 카테고리 단건 조회를 위한 메소드입니다.
     *
     * @param categoryId 조회할 카테고리 아이디 입니다.
     * @return CategoryResponseDto 카테고리 정보가 반환됩니다.
     */
    CategoryResponseDto getCategory(Long categoryId);

    /**
     * 카테고리 단건 조회를 위한 메소드입니다.
     *
     * @param categoryName 조회할 카테고리 이름 입니다.
     * @return CategoryResponseDto 카테고리 정보가 반환됩니다.
     */
    CategoryResponseDto getCategoryByCategoryName(String categoryName);

    /**
     * 카테고리를 생성하기 위한 메소드입니다.
     *
     * @param createCategoryRequestDto 카테고리 생성을 위한 정보를 담은 dto 입니다.
     * @return CategoryResponseDto 생성된 카테고리 정보가 반환됩니다.
     */
    CategoryResponseDto createCategory(CreateCategoryRequestDto createCategoryRequestDto);

    /**
     * 카테고리를 수정하기 위한 메소드입니다.
     *
     * @param modifyCategoryRequestDto 카테고리 수정을 위한 정보를 담은 dto 입니다.
     * @return CategoryResponseDto 수정된 카테고리 정보가 반환됩니다.
     */
    CategoryResponseDto modifyCategory(ModifyCategoryRequestDto modifyCategoryRequestDto);

    /**
     * 상위 카테고리를 생성하기 위한 메소드입니다.
     *
     * @param modifyCategoryRequestDto 카테고리 수정을 위한 정보를 담은 dto 입니다.
     * @return ParentCategoryResponseDto 수정된 상위 카테고리 정보가 반환됩니다.
     */
    ParentCategoryResponseDto modifyParentCategory(ModifyCategoryRequestDto modifyCategoryRequestDto);

    /**
     * 카테고리를 삭제하기 위한 메소드입니다.
     *
     * @param categoryId 해당 카테고리 아이디 입니다.
     */
    void deleteCategory(Long categoryId);

    /**
     * 상위 카테고리 및 해당 하위 카테고리 조회를 위한 메소드입니다.
     *
     * @return ParentCategoryResponseDto 상위 카테고리 정보 리스트가 반환됩니다.
     */
    List<ParentCategoryResponseDto> getParentWithChildCategories();

    /**
     * 카테고리 기본 정보 조회를 위한 메소드입니다.
     *
     * @return CategoryInfoResponseDto 카테고리 정보 리스트가 반환됩니다.
     */
    List<CategoryInfoResponseDto> getCategoriesInfo();

    /**
     * 상위 카테고리 단건 조회를 위한 메소드입니다.
     *
     * @param categoryId 해당 상위 카테고리 아이디 입니다.
     * @return ParentCategoryResponseDto
     */
    ParentCategoryResponseDto getParentWithChildCategoryByParentCategoryId(Long categoryId);
}
