package com.nhnacademy.shop.category.repository;

import com.nhnacademy.shop.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 카테고리(Category) 테이블 레포지토리 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    /**
     * 카테고리 이름을 통한 단건 조회를 위한 메소드 입니다.
     * @param categoryName 조회할 카테고리 이름입니다.
     * @return 해당 카테고리 반환.
     */
    Category findByCategoryName(String categoryName);
}
