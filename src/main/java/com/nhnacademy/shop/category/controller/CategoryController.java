package com.nhnacademy.shop.category.controller;

import com.nhnacademy.shop.category.dto.request.CreateCategoryRequestDto;
import com.nhnacademy.shop.category.dto.request.ModifyCategoryRequestDto;
import com.nhnacademy.shop.category.dto.response.CategoryInfoResponseDto;
import com.nhnacademy.shop.category.dto.response.CategoryResponseDto;
import com.nhnacademy.shop.category.dto.response.ParentCategoryResponseDto;
import com.nhnacademy.shop.category.exception.CategoryAlreadyExistsException;
import com.nhnacademy.shop.category.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
/**
 * 카테고리(Category) RestController 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
@RestController
@RequestMapping("/shop")
public class CategoryController {
    private final CategoryService categoryService;

    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 카테고리 전체 조회 요청 시 사용되는 메소드입니다.
     *
     * @param pageSize 페이지 사이즈 입니다.
     * @param offset 페이지 오프셋 입니다.
     * @throws ResponseStatusException 카테고리를 찾을 수 없을 때 응답코드 404 NOT_FOUND 반환합니다.
     * @return 성공했을 때 응답코드 200 OK 반환합니다.
     */
    @GetMapping("/categories/page")
    public ResponseEntity<Page<CategoryResponseDto>> getCategories(@RequestParam Integer pageSize,
                                                                   @RequestParam Integer offset) {
        Page<CategoryResponseDto> dtoList = categoryService.getCategories(pageSize, offset);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtoList);
    }

    /**
     * 카테고리 단건 조회 요청 시 사용되는 메소드입니다.
     *
     * @param categoryId 조회를 위한 해당 카테고리 아이디 입니다.
     * @throws ResponseStatusException 카테고리를 찾을 수 없을 때 응답코드 404 NOT_FOUND 반환합니다.
     * @return 성공했을 때 응답코드 200 OK 반환합니다.
     */
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategory(categoryId);
        if (Objects.isNull(categoryResponseDto)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category Not Found : %d", categoryId));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryResponseDto);
    }

    /**
     * 카테고리 단건 조회 요청 시 사용되는 메소드입니다.
     *
     * @param categoryName 조회를 위한 해당 카테고리 이름 입니다.
     * @throws ResponseStatusException 카테고리를 찾을 수 없을 때 응답코드 404 NOT_FOUND 반환합니다.
     * @return 성공했을 때 응답코드 200 OK 반환합니다.
     */
    @GetMapping("/categories/name/{categoryName}")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@PathVariable String categoryName) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryByCategoryName(categoryName);
        if (Objects.isNull(categoryResponseDto)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category Not Found : %s", categoryName));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryResponseDto);
    }

    /**
     * 상위 카테고리 및 해당 하위 카테고리 목록 조회 요청 시 사용되는 메소드입니다.
     *
     * @throws ResponseStatusException 카테고리를 찾을 수 없을 때 응답코드 404 NOT_FOUND 반환합니다.
     * @return 성공했을 때 응답코드 200 OK 반환합니다.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<ParentCategoryResponseDto>> getParentWithChildCategories() {
        List<ParentCategoryResponseDto> categoryResponseDtoList = categoryService.getParentWithChildCategories();
        if (Objects.isNull(categoryResponseDtoList)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categories Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryResponseDtoList);
    }

    /**
     * 카테고리 기본 정보 목록 조회 요청 시 사용되는 메소드입니다.
     *
     * @throws ResponseStatusException 카테고리를 찾을 수 없을 때 응답코드 404 NOT_FOUND 반환합니다.
     * @return 성공했을 때 응답코드 200 OK 반환합니다.
     */
    @GetMapping("/categories/parents")
    public ResponseEntity<List<CategoryInfoResponseDto>> getCategoriesInfo() {
        List<CategoryInfoResponseDto> categoryResponseDtoList = categoryService.getCategoriesInfo();
        if (Objects.isNull(categoryResponseDtoList)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categories Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryResponseDtoList);
    }

    /**
     * 상위 카테고리 및 해당 하위 카테고리 단건 조회 요청 시 사용되는 메소드입니다.
     *
     * @param categoryId 조회를 위한 해당 카테고리 아이디 입니다.
     * @throws ResponseStatusException 카테고리를 찾을 수 없을 때 응답코드 404 NOT_FOUND 반환합니다.
     * @return 성공했을 때 응답코드 200 OK 반환합니다.
     */
    @GetMapping("/categories/parents/{categoryId}")
    public ResponseEntity<ParentCategoryResponseDto> getParentWithChildCategory(@PathVariable Long categoryId) {
        ParentCategoryResponseDto parentCategory = categoryService.getParentWithChildCategoryByParentCategoryId(categoryId);
        if (Objects.isNull(parentCategory)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Category Not Found : %d", categoryId));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(parentCategory);
    }

    /**
     * 카테고리 생성 요청 시 사용되는 메소드입니다.
     *
     * @param createCategoryRequestDto 생성할 카테고리 정보를 담고 있는 dto 입니다.
     * @throws ResponseStatusException 중복된 카테고리 이름으로 생성 요청했을 때, 409 CONFLICT 반환합니다.
     * @return 성공했을 때 응답코드 201 CREATED 반환합니다.
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        try {
            CategoryResponseDto dto = categoryService.createCategory(createCategoryRequestDto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto);
        } catch (CategoryAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    /**
     * 카테고리 수정 시 사용되는 메소드입니다.
     *
     * @param modifyCategoryRequestDto 수정할 카테고리 정보를 담고 있는 dto 입니다.
     * @throws ResponseStatusException 중복된 카테고리 이름으로 수정 요청 시 409 CONFLICT 반환합니다.
     * @return 성공했을 때 응답코드 201 CREATED 반환합니다.
     */
    @PutMapping("/categories")
    public ResponseEntity<CategoryResponseDto> modifyCategory(@RequestBody ModifyCategoryRequestDto modifyCategoryRequestDto) {
        try {
            CategoryResponseDto dto = categoryService.modifyCategory(modifyCategoryRequestDto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto);
        } catch (CategoryAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    /**
     * 상위 카테고리 수정 시 사용되는 메소드입니다.
     *
     * @param modifyCategoryRequestDto 수정할 카테고리 정보를 담고 있는 dto 입니다.
     * @throws ResponseStatusException 중복된 카테고리 이름으로 수정 요청 시 409 CONFLICT 반환합니다.
     * @return 성공했을 때 응답코드 201 CREATED 반환합니다.
     */
    @PutMapping("/categories/parents")
    public ResponseEntity<ParentCategoryResponseDto> modifyParentCategory(@RequestBody ModifyCategoryRequestDto modifyCategoryRequestDto) {
        try {
            ParentCategoryResponseDto dto = categoryService.modifyParentCategory(modifyCategoryRequestDto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto);
        } catch (CategoryAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    /**
     * 카테고리 삭제 요청 시 사용되는 메소드입니다.
     *
     * @param categoryId 삭제를 위한 해당 카테고리 아이디 입니다.
     * @return 성공했을 때 응답코드 204 NO_CONTENT 반환합니다.
     */
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
