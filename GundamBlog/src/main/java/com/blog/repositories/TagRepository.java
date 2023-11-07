package com.blog.repositories;

import com.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Tag findByTagName(String tagName);

  @Query("FROM Tag b WHERE b.tagName LIKE %:tagName%")
  List<Tag> getTagsByTagName(String tagName);

}
