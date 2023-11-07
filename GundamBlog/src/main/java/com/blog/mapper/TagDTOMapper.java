package com.blog.mapper;

import com.blog.common.StringCommon;
import com.blog.dto.TagDTO;
import com.blog.wrapper.TagDTOWrapper;

import java.util.ArrayList;
import java.util.List;

public class TagDTOMapper {
  private static TagDTOMapper INSTANCE;

  public static TagDTOMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TagDTOMapper();
    }
    return INSTANCE;
  }

  public List<TagDTO> toDTO(TagDTOWrapper tagDTOWrapper) {
    List<TagDTO> tagsDTO = new ArrayList<>();
    for (String path : tagDTOWrapper.getTagsName()) {
      tagsDTO.add(new TagDTO(null, path, StringCommon.createPath(path), 0));
    }
    return tagsDTO;
  }
}
