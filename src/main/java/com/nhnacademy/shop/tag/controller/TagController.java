package com.nhnacademy.shop.tag.controller;

import com.nhnacademy.shop.tag.dto.CreateTagRequestDto;
import com.nhnacademy.shop.tag.dto.TagResponseDto;
import com.nhnacademy.shop.tag.exception.AlreadyExistTagNameException;
import com.nhnacademy.shop.tag.exception.NotFoundTagException;
import com.nhnacademy.shop.tag.exception.NotFoundTagNameException;
import com.nhnacademy.shop.tag.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Tag Controller
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */
@RestController
@RequestMapping("/shop/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/id/{tagId}")
    public ResponseEntity<TagResponseDto> getTagById(@PathVariable Long tagId) {
        try {
            TagResponseDto tagResponseDto = tagService.getTagById(tagId);

            return ResponseEntity.ok().body(tagResponseDto);
        } catch (NotFoundTagException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/name/{tagName}")
    public ResponseEntity<TagResponseDto> getTagByTagName(@PathVariable String tagName) {
        try {
            TagResponseDto tagResponseDto = tagService.getTagByName(tagName);

            return ResponseEntity.ok().body(tagResponseDto);
        } catch (NotFoundTagNameException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<TagResponseDto> saveTag(@RequestBody CreateTagRequestDto createTagRequestDto) {
        try {
            TagResponseDto responseDto = tagService.saveTag(createTagRequestDto);
            return ResponseEntity.ok().body(responseDto);
        } catch(AlreadyExistTagNameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/delete/id/{tagId}")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long tagId) {
        try {
            tagService.deleteTagById(tagId);
            return ResponseEntity.ok().build();
        } catch(NotFoundTagException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/name/{tagName}")
    public ResponseEntity<Void> deleteTagByTagName(@PathVariable String tagName) {
        try {
            tagService.deleteTagByName(tagName);
            return ResponseEntity.ok().build();
        } catch(NotFoundTagNameException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

