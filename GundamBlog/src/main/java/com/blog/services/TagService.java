package com.blog.services;

import com.blog.common.RoleCommon;
import com.blog.dto.BlogDTO;
import com.blog.dto.TagDTO;
import com.blog.models.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {

  boolean isTagExist(String tagName);

  Tag createTag(TagDTO tagDTO);

  List<TagDTO> findAllTag();

  Page<TagDTO> findTagByTitleForAdmin(String tagName, int pageNo, int pageSize);

  Tag renameTag(Long tagId, String newTagName);
}
