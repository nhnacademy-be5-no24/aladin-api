package com.nhnacademy.shop.category.repository.impl;


import com.nhnacademy.shop.category.domain.Category;
import com.nhnacademy.shop.category.domain.QCategory;
import com.nhnacademy.shop.category.dto.response.CategoryInfoResponseDto;
import com.nhnacademy.shop.category.dto.response.CategoryResponseDto;
import com.nhnacademy.shop.category.dto.response.ChildCategoryResponseDto;
import com.nhnacademy.shop.category.dto.response.ParentCategoryResponseDto;
import com.nhnacademy.shop.category.repository.CategoryRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

/**
 * 카테고리 Custom Repository 구현체 입니다.
 *
 * @author : 강병구
 * @date : 2024-03-29
 **/
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom{
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    QCategory parentCategory = new QCategory("parentCategory");

    @Override
    public Page<CategoryResponseDto> findCategories(Pageable pageable) {
        QCategory category = QCategory.category;
        JPQLQuery<Long> count = from(category)
                .select(category.count());

        List<CategoryResponseDto> content = from(category)
                .select(Projections.constructor(CategoryResponseDto.class,
                        category.categoryId,
                        category.categoryName,
                        Projections.fields(CategoryResponseDto.class,
                                parentCategory.categoryId,
                                parentCategory.categoryName)))
                .leftJoin(category.parentCategory, parentCategory)
                .on(category.parentCategory.categoryId.eq(parentCategory.categoryId))
                .orderBy(category.categoryId.asc()).orderBy(category.categoryName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryInfoResponseDto> findCategoriesInfo() {
        return from(parentCategory)
                .select(Projections.constructor(CategoryInfoResponseDto.class,
                        parentCategory.categoryId,
                        parentCategory.categoryName))
                .orderBy(parentCategory.categoryName.asc())
                .distinct()
                .fetch();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParentCategoryResponseDto> findParentCategoriesWithChildCategories() {
        List<ParentCategoryResponseDto> parentList = from(parentCategory)
                .where(parentCategory.parentCategory.isNull())
                .select(Projections.constructor(ParentCategoryResponseDto.class,
                        parentCategory.categoryId,
                        parentCategory.categoryName))
                .orderBy(parentCategory.categoryName.asc())
                .fetch();

        QCategory childCategory = QCategory.category;

        parentList.forEach(p -> {
            List<ChildCategoryResponseDto> childList = from(childCategory)
                    .select(Projections.constructor(ChildCategoryResponseDto.class,
                            childCategory.categoryId,
                            childCategory.categoryName))
                    .where(childCategory.parentCategory.categoryId.eq(p.getCategoryId()))
                    .orderBy(childCategory.categoryId.asc(), childCategory.categoryName.asc())
                    .fetch();
            p.setChildCategories(childList);
        });
        return parentList;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ParentCategoryResponseDto findParentCategory(Long categoryId) {
        ParentCategoryResponseDto parentDto = from(parentCategory)
                .where(parentCategory.parentCategory.isNull().and(parentCategory.categoryId.eq(categoryId)))
                .select(Projections.constructor(ParentCategoryResponseDto.class,
                        parentCategory.categoryId,
                        parentCategory.categoryName))
                .orderBy(parentCategory.categoryName.asc())
                .fetchOne();

        QCategory childCategory = QCategory.category;

        parentDto.setChildCategories(from(childCategory)
                .select(Projections.constructor(ChildCategoryResponseDto.class,
                        childCategory.categoryId,
                        childCategory.categoryName))
                .where(childCategory.parentCategory.categoryId.eq(parentDto.getCategoryId()))
                .orderBy(childCategory.categoryId.asc(), childCategory.categoryName.asc())
                .fetch());
        return parentDto;
    }

}
