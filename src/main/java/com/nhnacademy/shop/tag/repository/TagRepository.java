package com.nhnacademy.shop.tag.repository;

import com.nhnacademy.shop.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Tag Repository
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByTagName(String tagName);
    Tag findByTagId(Long id);

}
