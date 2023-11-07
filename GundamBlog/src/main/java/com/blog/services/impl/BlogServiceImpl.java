package com.blog.services.impl;

import com.blog.common.Message;
import com.blog.common.RoleCommon;
import com.blog.dto.BlogDTO;
import com.blog.dto.SeriesDTO;
import com.blog.dto.TagDTO;
import com.blog.mapper.BlogMapper;
import com.blog.mapper.SeriesMapper;
import com.blog.mapper.TagDTOMapper;
import com.blog.models.Blog;
import com.blog.models.Series;
import com.blog.models.Tag;
import com.blog.models.User;
import com.blog.repositories.BlogRepository;
import com.blog.repositories.SeriesRepository;
import com.blog.repositories.TagRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.BlogService;
import com.blog.services.UserService;
import com.blog.wrapper.TagDTOWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class BlogServiceImpl implements BlogService {
  @Autowired
  BlogRepository blogRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  TagRepository tagRepository;
  @Autowired
  SeriesRepository seriesRepository;
  @Autowired
  UserService userService;

  /**
   * Create new Blog and save into database
   *
   * @param blogCreationDTO To map to Blog
   * @param tagDTOWrapper   Map to Tag and set for Blog
   * @param seriesDTO       Map to series and set for Blog
   * @return new Blog just save
   */
  @Override
  public Blog createBlog(BlogDTO blogCreationDTO, TagDTOWrapper tagDTOWrapper, SeriesDTO seriesDTO) {
    // map tagDTOWrapper -> tagDTO
    List<TagDTO> tagsDTO = TagDTOMapper.getInstance().toDTO(tagDTOWrapper);
    // tagDTO -> tag
    List<Tag> tags = new ArrayList<>();
    for (TagDTO tagDTO : tagsDTO) {
      Tag tag = tagRepository.findByTagName(tagDTO.getTagName());
      //  tag doesn't exist
      if (tag == null) {
        System.out.println(Message.TAG_DOES_NOT_EXIST);
        return null;
      }
      tags.add(tag);
    }
    Series series = SeriesMapper.getInstance().toEntity(seriesDTO);
    Series mainSeries = seriesRepository.findBySeriesName(series.getSeriesName());
    // series doesn't exist
    if (mainSeries == null) {
      System.out.println(Message.SERIES_DOES_NOT_EXIST);
      return null;
    }
    // get user's data to save into the blog
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username);
    User mainUser = new User(user.getUserId(), username, user.getPassword(), user.getRole());
    if (userService.isUserNameExist(username)) {
      // blogCreationDTO -> blog to insert
      Blog blog = BlogMapper.getInstance().toEntity(blogCreationDTO);
      blog.setUser(mainUser);
      blog.setSeries(mainSeries);
      blog.setTags(tags);
      blog.setStatus(true);
      return blogRepository.save(blog);
    }
    return null;
  }

  /**
   * Find blog by id -> update Blog and save into database
   *
   * @param blogDTO       To map to Blog
   * @param tagDTOWrapper Map to Tag and set for Blog
   * @param seriesDTO     Map to series and set for Blog
   * @return new Blog just update
   */
  @Override
  public Blog updateBlog(BlogDTO blogDTO, TagDTOWrapper tagDTOWrapper, SeriesDTO seriesDTO) {
    Optional<Blog> blogInDB = blogRepository.findById(blogDTO.getBlogId());
    // map tagDTOWrapper -> tagDTO
    List<TagDTO> tagsDTO = TagDTOMapper.getInstance().toDTO(tagDTOWrapper);
    // tagDTO -> tag
    List<Tag> tags = new ArrayList<>();
    for (TagDTO tagDTO : tagsDTO) {
      Tag tag = tagRepository.findByTagName(tagDTO.getTagName());
      //  tag doesn't exist
      if (tag == null) {
        System.out.println(Message.TAG_DOES_NOT_EXIST);
        return null;
      }
      tags.add(tag);
    }
    Series series = SeriesMapper.getInstance().toEntity(seriesDTO);
    Series mainSeries = seriesRepository.findBySeriesName(series.getSeriesName());
    // series doesn't exist
    if (mainSeries == null) {
      System.out.println(Message.SERIES_DOES_NOT_EXIST);
      return null;
    }
    // get user's data to save into the blog
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username);
    User mainUser = new User(user.getUserId(), username, user.getPassword(), user.getRole());

    if (userService.isUserNameExist(username)) {
      // update blog
      Blog blog = BlogMapper.getInstance().toEntityForAdmin(blogDTO);
      blog.setUser(mainUser);
      blog.setSeries(mainSeries);
      blog.setTags(tags);
      return blogRepository.save(blog);
    }
    return null;
  }

  /**
   * To get Blog with status = true by path
   *
   * @param path Used for searching
   * @return A BlogDTO has been mapped from the newly found Blog
   */
  @Override
  public BlogDTO findBlogByPath(String path, RoleCommon role) {
    Blog blog = null;
    BlogDTO blogDTO;
    if (role.equals(RoleCommon.UN_KNOW)) {
      blog = blogRepository.findByPath(path);
    } else if (role.equals(RoleCommon.ADMIN)) {
      blog = blogRepository.findByPathForAdmin(path);
      if (blog != null) {
        blogDTO = BlogMapper.getInstance().toDTOForAdmin(blog);
        return blogDTO;
      }
    }
    if (blog == null) {
      throw new EntityNotFoundException(Message.BLOG_NOT_FOUND);
    }
    blogDTO = BlogMapper.getInstance().toDTO(blog);
    return blogDTO;
  }

  /**
   * Get all Blog with status = true in Database
   *
   * @return A List<BlogCreationDTO> has mapped from Blog
   */
  @Override
  public List<BlogDTO> findAllBlog(RoleCommon role) {
    List<BlogDTO> blogCreationDTOList = null;
    if (role.equals(RoleCommon.UN_KNOW)) {
      blogCreationDTOList = blogRepository.findAllBlog()
          .stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    } else if (role.equals(RoleCommon.ADMIN)) {
      blogCreationDTOList = blogRepository.findAll()
          .stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    }
    return blogCreationDTOList;
  }

  /**
   * Paging when get a Blog list
   *
   * @param pageNo      Current page
   * @param pageSize    Number of element each page
   * @param blogDTOList List to paging
   * @return A Page<BlogDTO> has been paged
   */
  @Override
  public Page<BlogDTO> pagingAllBlog(int pageNo, int pageSize, List<BlogDTO> blogDTOList) {
    int totalElement = blogDTOList.size();
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
    int start = (int) pageable.getOffset();
    int end = (int) ((pageable.getOffset() + pageable.getPageSize()) > blogDTOList.size() ? blogDTOList.size() : pageable.getOffset() + pageable.getPageSize());
    blogDTOList = blogDTOList.subList(start, end);
    return new PageImpl<>(blogDTOList, pageable, totalElement);
  }

  /**
   * Find blog by title's keyword
   *
   * @param title    Used for searching
   * @param pageNo   Current page
   * @param pageSize Number of element each page
   * @return A Page<BlogCreationDTO> has been paged
   */
  @Override
  public Page<BlogDTO> findBlogByTitle(String title, int pageNo, int pageSize, RoleCommon role) {
    List<BlogDTO> blogDTOList = null;
    if (role.equals(RoleCommon.UN_KNOW)) {
      blogDTOList = blogRepository.findBlogByTitle(title).stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    } else if (role.equals(RoleCommon.ADMIN)) {
      blogDTOList = blogRepository.findBlogByTitleForAdmin(title).stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    }
    return pagingAllBlog(pageNo, pageSize, blogDTOList);
  }

  /**
   * Find a list of blog by tagId
   *
   * @param tagId    Used for searching
   * @param pageNo   Current page
   * @param pageSize Number of element each page
   * @return A Page<BlogDTO> has been paged
   */
  @Override
  public Page<BlogDTO> getBlogByTagId(Long tagId, int pageNo, int pageSize, RoleCommon role) {
    Optional<Tag> tag = tagRepository.findById(tagId);
    List<BlogDTO> blogDTOList = null;
    if (role.equals(RoleCommon.UN_KNOW)) {
      blogDTOList = blogRepository.findByTagsAndStatus(tag.get(), true).stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    } else if (role.equals(RoleCommon.ADMIN)) {
      blogDTOList = blogRepository.findByTags(tag.get()).stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    }
    return pagingAllBlog(pageNo, pageSize, blogDTOList);
  }

  @Override
  public Page<BlogDTO> getBlogBySeriesId(Long seriesId, int pageNo, int pageSize, RoleCommon role) {
    Optional<Series> series = seriesRepository.findById(seriesId);
    List<BlogDTO> blogDTOList = null;
    if (role.equals(RoleCommon.UN_KNOW)) {
      blogDTOList = blogRepository.findBySeriesAndStatus(series.get(), true).stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    } else if (role.equals(RoleCommon.ADMIN)) {
      blogDTOList = blogRepository.findBySeries(series.get()).stream()
          .map(blog -> BlogMapper.getInstance().toDTO(blog))
          .collect(Collectors.toList());
    }
    return pagingAllBlog(pageNo, pageSize, blogDTOList);
  }
}