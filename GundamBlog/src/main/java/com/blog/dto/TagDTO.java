package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO implements Serializable {
  Long tagId;
  String tagName;
  String path;
  // status
  long numOfBlog;
}

