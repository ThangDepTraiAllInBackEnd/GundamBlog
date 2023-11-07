package com.blog.services.impl;

import com.blog.common.Message;
import com.blog.dto.TagDTO;
import com.blog.mapper.TagMapper;
import com.blog.models.Tag;
import com.blog.repositories.BlogRepository;
import com.blog.repositories.TagRepository;
import com.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TagServiceImpl implements TagService {
  @Autowired
  TagRepository tagRepository;
  @Autowired
  BlogRepository blogRepository;

  @Override
  public boolean isTagExist(String tagName) {
    Tag tagInDb = tagRepository.findByTagName(tagName);
    return tagInDb != null;
  }

  @Override
  public Tag createTag(TagDTO tagDTO) {
    Tag tag = TagMapper.getInstance().toEntity(tagDTO);
    // tag is exits
    if (isTagExist(tagDTO.getTagName())) {
      System.out.println(Message.TAG_EXIST);
      return null;
    }
    return tagRepository.save(tag);
  }

  @Override
  public List<TagDTO> findAllTag() {
    List<TagDTO> tagsDTO = new ArrayList<>();
    List<Tag> tags = tagRepository.findAll();
    // map from tag to tagDTO
    for (Tag tag : tags) {
      TagDTO tagDTO = TagMapper.getInstance().toDTO(tag);
      tagsDTO.add(tagDTO);
    }
    return tagsDTO;
  }

  @Override
  public Page<TagDTO> findTagByTitleForAdmin(String tagName, int pageNo, int pageSize) {
    List<Tag> tagList = tagRepository.getTagsByTagName(tagName);
    int totalElement = tagList.size();
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
    int start = (int) pageable.getOffset();
    int end = (int) ((pageable.getOffset() + pageable.getPageSize()) > tagList.size() ? tagList.size() : pageable.getOffset() + pageable.getPageSize());
    tagList = tagList.subList(start, end);

    List<TagDTO> tagDTOList = new ArrayList<>();
    for (Tag tag : tagList) {
      TagDTO tagDTO = TagMapper.getInstance().toDTOForAdmin(tag);
      tagDTO.setNumOfBlog(blogRepository.countByTags(tagRepository.findById(tag.getTagId()).get()));
      tagDTOList.add(tagDTO);
    }
    return new PageImpl<>(tagDTOList, pageable, totalElement);
  }

  @Override
  public Tag renameTag(Long tagId, String newTagName) {
    // tag is exist
    if (tagRepository.findByTagName(newTagName) != null) {
      System.out.println(Message.TAG_EXIST);
      return null;
    }
    Optional<Tag> tag = tagRepository.findById(tagId);
    tag.get().setTagName(newTagName);
    return tagRepository.save(tag.get());
  }
}
