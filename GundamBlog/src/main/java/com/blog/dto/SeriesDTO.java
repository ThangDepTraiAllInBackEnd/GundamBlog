package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDTO {
  Long seriesId;
  private String seriesName;
  private String path;
  long numOfBlog;
}