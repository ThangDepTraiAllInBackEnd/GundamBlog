package com.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blogs")
public class Blog {

  @Id
  @Column(name = "blog_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long blogId;

  @Column(name = "path", unique = true, nullable = false, columnDefinition = "nvarchar(400)")
  private String path;

  @Column(name = "background_image", nullable = false, columnDefinition = "nvarchar(400)")
  private String backgroundImage;

  @Column(unique = true, nullable = false, columnDefinition = "nvarchar(200)")
  private String title;

  @Column(nullable = false)
  private Date date;

  @Column(name = "content", nullable = false, columnDefinition = "nvarchar(max)")
  private String content;

  @Column(nullable = false)
  private Boolean status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "series_id", referencedColumnName = "series_id")
  private Series series;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "blogs_tags",
      joinColumns = @JoinColumn(name = "blog_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private List<Tag> tags;

  @Override
  public String toString() {
    return "Blog{" +
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
