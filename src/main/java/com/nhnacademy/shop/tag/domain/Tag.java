package com.nhnacademy.shop.tag.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Tag Domain
 *
 * @Author : 박병휘
 * @Date : 2024/03/28
 */

@Entity
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(name = "tag_name", unique = true)
    private String tagName;
}
