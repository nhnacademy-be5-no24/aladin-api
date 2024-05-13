package com.nhnacademy.shop.category.service.impl;

import com.nhnacademy.shop.category.domain.Category;
import com.nhnacademy.shop.category.dto.request.CreateCategoryRequestDto;
import com.nhnacademy.shop.category.dto.request.ModifyCategoryRequestDto;
import com.nhnacademy.shop.category.dto.response.CategoryInfoResponseDto;
import com.nhnacademy.shop.category.dto.response.CategoryResponseDto;
import com.nhnacademy.shop.category.dto.response.ParentCategoryResponseDto;
import com.nhnacademy.shop.category.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shop.category.exception.CategoryNotFoundException;
import com.nhnacademy.shop.category.repository.CategoryRepository;
import com.nhnacademy.shop.category.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 카테고리 서비스 구현체 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getCategories(Integer pageSize, Integer offset) {
        Pageable pageable = PageRequest.of(pageSize, offset);
        return categoryRepository.findCategories(pageable);
    }

    /**
     * {@inheritDoc}
     * @throws CategoryNotFoundException 카테고리가 존재 하지 않을 때 발생하는 Exception 입니다.
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        return new CategoryResponseDto(category.getCategoryId(), category.getCategoryName(), null);
    }

    /**
     * {@inheritDoc}
     * @throws CategoryNotFoundException 카테고리가 존재 하지 않을 때 발생하는 Exception 입니다.
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryByCategoryName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if(Objects.isNull(category)) {
            throw new CategoryNotFoundException();
        }
        Category parent = category.getParentCategory();
        if(Objects.isNull(parent)) {
            return new CategoryResponseDto(category.getCategoryId(), category.getCategoryName(), null);
        }
        CategoryResponseDto parentDto = new CategoryResponseDto(parent.getCategoryId(), parent.getCategoryName(), null);
        return new CategoryResponseDto(category.getCategoryId(), category.getCategoryName(), parentDto);
    }

    /**
     * {@inheritDoc}
     * @throws CategoryAlreadyExistsException 이미 존재하는 카테고리 생성 혹은 수정 요청 시 발생하는 Exception 입니다.
     */
    @Override
    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        Category parentCategory = getParentCategory(createCategoryRequestDto.getParentCategoryId());
        if (Objects.nonNull(categoryRepository.findByCategoryName(createCategoryRequestDto.getCategoryName()))) {
            throw new CategoryAlreadyExistsException();
        }
        Category category = Category.builder()
                .categoryName(createCategoryRequestDto.getCategoryName())
                .parentCategory(parentCategory)
                .build();
        Category savedCategory = categoryRepository.save(category);

        Category parent = savedCategory.getParentCategory();
        if (Objects.nonNull(parent)) {
            CategoryResponseDto parentDto = new CategoryResponseDto(parent.getCategoryId(), parent.getCategoryName(), null);
            return new CategoryResponseDto(savedCategory.getCategoryId(), savedCategory.getCategoryName(), parentDto);
        }
        return new CategoryResponseDto(savedCategory.getCategoryId(), savedCategory.getCategoryName(), null);
    }

    /**
     * {@inheritDoc}
     * @throws CategoryAlreadyExistsException 이미 존재하는 카테고리 생성 혹은 수정 요청 시 발생하는 Exception 입니다.
     */
    @Override
    @Transactional
    public CategoryResponseDto modifyCategory(ModifyCategoryRequestDto modifyCategoryRequestDto) {
        Category parentCategory = getParentCategory(modifyCategoryRequestDto.getParentCategoryId());
        if (Objects.nonNull(categoryRepository.findByCategoryName(modifyCategoryRequestDto.getCategoryName()))) {
            throw new CategoryAlreadyExistsException();
        }
        Category category = Category.builder()
                .categoryId(modifyCategoryRequestDto.getCategoryId())
                .categoryName(modifyCategoryRequestDto.getCategoryName())
                .parentCategory(parentCategory)
                .build();
        Category savedCategory = categoryRepository.save(category);
        Category parent = savedCategory.getParentCategory();
        if (Objects.nonNull(parent)) {
            CategoryResponseDto parentDto = new CategoryResponseDto(parent.getCategoryId(), parent.getCategoryName(), null);
            return new CategoryResponseDto(savedCategory.getCategoryId(), savedCategory.getCategoryName(), parentDto);
        }
        return new CategoryResponseDto(savedCategory.getCategoryId(), savedCategory.getCategoryName(), null);
    }

    /**
     * {@inheritDoc}
     * @throws CategoryAlreadyExistsException 이미 존재하는 카테고리 생성 혹은 수정 요청 시 발생하는 Exception 입니다.
     */
    @Override
    @Transactional
    public ParentCategoryResponseDto modifyParentCategory(ModifyCategoryRequestDto modifyCategoryRequestDto) {
        if (Objects.nonNull(categoryRepository.findByCategoryName(modifyCategoryRequestDto.getCategoryName()))) {
            throw new CategoryAlreadyExistsException();
        }
        Category category = Category.builder()
                .categoryId(modifyCategoryRequestDto.getCategoryId())
                .categoryName(modifyCategoryRequestDto.getCategoryName())
                .build();
        Category savedCategory = categoryRepository.save(category);

        return new ParentCategoryResponseDto(savedCategory.getCategoryId(), savedCategory.getCategoryName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    /**
     * {@inheritDoc}
     * @throws CategoryNotFoundException 카테고리가 존재 하지 않을 때 발생하는 Exception 입니다.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParentCategoryResponseDto> getParentWithChildCategories() {
        List<ParentCategoryResponseDto> parentCategoryResponseDtoList = categoryRepository.findParentCategoriesWithChildCategories();
        if(Objects.isNull(parentCategoryResponseDtoList)) {
            throw new CategoryNotFoundException();
        }
        return parentCategoryResponseDtoList;
    }

    /**
     * {@inheritDoc}
     * @throws CategoryNotFoundException 카테고리가 존재 하지 않을 때 발생하는 Exception 입니다.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryInfoResponseDto> getCategoriesInfo() {
        List<CategoryInfoResponseDto> categoryInfoResponseDtoList = categoryRepository.findCategoriesInfo();
        if(Objects.isNull(categoryInfoResponseDtoList)) {
            throw new CategoryNotFoundException();
        }
        return categoryInfoResponseDtoList;
    }

    /**
     * {@inheritDoc}
     * @throws CategoryNotFoundException 카테고리가 존재 하지 않을 때 발생하는 Exception 입니다.
     */
    @Override
    @Transactional(readOnly = true)
    public ParentCategoryResponseDto getParentWithChildCategoryByParentCategoryId(Long categoryId) {
        ParentCategoryResponseDto parentCategoryResponseDto = categoryRepository.findParentCategory(categoryId);
        if (Objects.isNull(parentCategoryResponseDto)) {
            throw new CategoryNotFoundException();
        }
        return parentCategoryResponseDto;
    }

    /**
     * 상위 카테고리 존재 확인을 위한 메소드 입니다.
     *
     * @param categoryId 상위 카테고리 확인을 위한 카테고리 아이디 입니다.
     * @throws CategoryNotFoundException 카테고리가 존재 하지 않을 때 발생하는 Exception 입니다.
     */
    private Category getParentCategory(Long categoryId) {
        if(Objects.isNull(categoryId)) {
            return null;
        }
        return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    }

}
