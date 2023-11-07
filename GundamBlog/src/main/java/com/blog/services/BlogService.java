package com.blog.services;

import com.blog.common.RoleCommon;
import com.blog.dto.BlogDTO;
import com.blog.dto.SeriesDTO;
import com.blog.models.Blog;
import com.blog.wrapper.TagDTOWrapper;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {
  Blog createBlog(BlogDTO blogDTO, TagDTOWrapper tagDTOWrapper, SeriesDTO seriesDTO);

  Blog updateBlog(BlogDTO blogDTO, TagDTOWrapper tagDTOWrapper, SeriesDTO seriesDTO);

  BlogDTO findBlogByPath(String path, RoleCommon role);

  List<BlogDTO> findAllBlog(RoleCommon role);

  Page<BlogDTO> pagingAllBlog(int pageNo, int pageSize, List<BlogDTO> blogCreationDTOList);

  Page<BlogDTO> findBlogByTitle(String title, int pageNo, int pageSize, RoleCommon role);

  Page<BlogDTO> getBlogByTagId(Long tagId, int pageNo, int pageSize, RoleCommon role);

  Page<BlogDTO> getBlogBySeriesId(Long tagId, int pageNo, int pageSize, RoleCommon role);

}
