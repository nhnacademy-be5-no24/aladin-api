package com.nhnacademy.shop.tag.service.impl;

import com.nhnacademy.shop.tag.domain.Tag;
import com.nhnacademy.shop.tag.dto.CreateTagRequestDto;
import com.nhnacademy.shop.tag.dto.TagResponseDto;
import com.nhnacademy.shop.tag.exception.AlreadyExistTagNameException;
import com.nhnacademy.shop.tag.exception.NotFoundTagException;
import com.nhnacademy.shop.tag.exception.NotFoundTagNameException;
import com.nhnacademy.shop.tag.repository.TagRepository;
import com.nhnacademy.shop.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * TagService 구현체.
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public TagResponseDto getTagById(Long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);

        if(optionalTag.isEmpty()) {
            throw new NotFoundTagException(tagId);
        }

        return new TagResponseDto(optionalTag.get());
    }

    @Override
    public TagResponseDto getTagByName(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findTagByTagName(tagName);

        if(optionalTag.isEmpty()) {
            throw new NotFoundTagNameException(tagName);
        }

        return new TagResponseDto(optionalTag.get());
    }

    @Override
    public TagResponseDto saveTag(CreateTagRequestDto createTagRequestDto) {
        String tagName = createTagRequestDto.getTagName();

        if(tagRepository.findTagByTagName(tagName).isPresent()) {
            throw new AlreadyExistTagNameException(tagName);
        }

        Tag tag = new Tag(null, tagName);

        return new TagResponseDto(tagRepository.save(tag));
    }

    @Override
    public void deleteTagByName(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findTagByTagName(tagName);
        if(optionalTag.isEmpty()) {
            throw new NotFoundTagNameException(tagName);
        }

        tagRepository.delete(optionalTag.get());
    }

    @Override
    public void deleteTagById(Long tagId) {
        if(tagRepository.findById(tagId).isEmpty()) {
            throw new NotFoundTagException(tagId);
        }

        tagRepository.deleteById(tagId);
    }
}
