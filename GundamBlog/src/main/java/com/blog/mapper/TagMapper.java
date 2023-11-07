package com.blog.mapper;

import com.blog.common.StringCommon;
import com.blog.dto.TagDTO;
import com.blog.models.Tag;

public class TagMapper {
  private static TagMapper INSTANCE;

  public static TagMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TagMapper();
    }
    return INSTANCE;
  }

  public TagDTO toDTO(Tag tag) {
    TagDTO dto = new TagDTO();
    dto.setTagName(tag.getTagName());
    dto.setPath(tag.getPath());
    return dto;
  }

  public TagDTO toDTOForAdmin(Tag tag) {
    TagDTO dto = new TagDTO();
    dto.setTagId(tag.getTagId());
    dto.setTagName(tag.getTagName());
    dto.setPath(tag.getPath());
    return dto;
  }

  public Tag toEntity(TagDTO dto) {
    Tag tag = new Tag();
    tag.setTagName(dto.getTagName());
    tag.setPath(StringCommon.createPath(dto.getTagName()));
    return tag;
  }
}
