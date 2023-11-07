package com.blog.mapper;

import com.blog.common.StringCommon;
import com.blog.dto.BlogDTO;
import com.blog.dto.BlogDTO;
import com.blog.models.Blog;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class BlogMapper {
  private static BlogMapper INSTANCE;

  public static BlogMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new BlogMapper();
    }
    return INSTANCE;
  }

  public BlogDTO toDTO(Blog blog) {
    BlogDTO dto = new BlogDTO();
    dto.setPath(blog.getPath());
    dto.setBackgroundImage(blog.getBackgroundImage());
    dto.setTitle(blog.getTitle());
    dto.setDate(blog.getDate());
    dto.setContent(blog.getContent());
    dto.setTags(blog.getTags());
    dto.setStatus(blog.getStatus());
    dto.setSeries(blog.getSeries());
    dto.setUser(blog.getUser());
    return dto;
  }

  public BlogDTO toDTOForAdmin(Blog blog) {
    BlogDTO dto = new BlogDTO();
    dto.setBlogId(blog.getBlogId());
    dto.setPath(blog.getPath());
    dto.setBackgroundImage(blog.getBackgroundImage());
    dto.setTitle(blog.getTitle());
    dto.setDate(blog.getDate());
    dto.setContent(blog.getContent());
    dto.setTags(blog.getTags());
    dto.setStatus(blog.getStatus());
    dto.setSeries(blog.getSeries());
    dto.setUser(blog.getUser());
    return dto;
  }

  public Blog toEntity(BlogDTO dto) {
    Blog blog = new Blog();
    blog.setPath(StringCommon.createPath(dto.getTitle()));
    blog.setBackgroundImage("BackGround Image");
    blog.setTitle(dto.getTitle().toUpperCase());
    blog.setDate(new Date());
    blog.setContent(new String(dto.getContent().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
    blog.setStatus(dto.getStatus());
    blog.setTags(dto.getTags());
    blog.setUser(dto.getUser());
    blog.setSeries(dto.getSeries());
    return blog;
  }

  public Blog toEntityForAdmin(BlogDTO dto) {
    Blog blog = new Blog();
    blog.setBlogId(dto.getBlogId());
    blog.setPath(StringCommon.createPath(dto.getTitle()));
    blog.setBackgroundImage("BackGround Image");
    blog.setTitle(dto.getTitle().toUpperCase());
    blog.setDate(new Date());
    blog.setContent(new String(dto.getContent().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
    blog.setStatus(dto.getStatus());
    blog.setTags(dto.getTags());
    blog.setUser(dto.getUser());
    blog.setSeries(dto.getSeries());
    return blog;
  }
}