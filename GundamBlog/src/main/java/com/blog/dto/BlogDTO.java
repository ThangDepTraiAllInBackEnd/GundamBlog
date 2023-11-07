package com.blog.dto;

import com.blog.models.Series;
import com.blog.models.Tag;

import com.blog.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
  private Long blogId;
  private String path;
  private String backgroundImage;
  private String title;
  private Date date;
  private String content;
  private Boolean status;
  private List<Tag> tags = new ArrayList<>();
  private User user;
  private Series series;

  @Override
  public String toString() {
    return "BlogDTO{" +
        "blogId=" + blogId +
        ", path='" + path + '\'' +
        ", backgroundImage='" + backgroundImage + '\'' +
        ", title='" + title + '\'' +
        ", date=" + date +
        ", content='" + content + '\'' +
        ", status=" + status +
        '}';
  }
}
