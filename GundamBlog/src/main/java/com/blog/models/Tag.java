package com.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

  @Id
  @Column(name = "tag_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tagId;

  @Column(name = "tag_name", unique = true, nullable = false, columnDefinition = "nvarchar(20)")
  private String tagName;

  @Column(name = "path", unique = true, nullable = false, columnDefinition = "nvarchar(40)")
  private String path;

  // to insert
  public Tag(String tagName, String path) {
    this.tagName = tagName;
    this.path = path;
  }

  @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Blog> blogs = new ArrayList<>();

  @Override
  public String toString() {
    return "Tag{" +
        "tagId=" + tagId +
        ", tagName='" + tagName + '\'' +
        ", path='" + path + '\'' +
        '}';
  }
}
