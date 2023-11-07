package com.blog.repositories;

import com.blog.models.Blog;
import com.blog.models.Series;
import com.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

  /**
   * Get all blog with status = true for user
   *
   * @return blog list with status = true
   */
  @Query("FROM Blog b WHERE b.status = true")
  List<Blog> findAllBlog();

  @Query("FROM Blog b WHERE b.status = true AND b.path = :path")
  Blog findByPath(String path);

  @Query("FROM Blog b WHERE b.path = :path")
  Blog findByPathForAdmin(String path);

  @Query("FROM Blog b WHERE b.title LIKE %:title% AND b.status = true")
  List<Blog> findBlogByTitle(String title);

  @Query("FROM Blog b WHERE b.title LIKE %:title%")
  List<Blog> findBlogByTitleForAdmin(String title);

  long countByTags(Tag tag);

  long countBySeries(Series series);

  List<Blog> findByTags(Tag tag);

  List<Blog> findBySeries(Series series);

  List<Blog> findByTagsAndStatus(Tag tag, boolean status);

  List<Blog> findBySeriesAndStatus(Series series, boolean status);
}
