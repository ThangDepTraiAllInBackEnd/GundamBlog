package com.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "series")
public class Series {

  @Id
  @Column(name = "series_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seriesId;

  @Column(name = "series_name", unique = true, nullable = false, columnDefinition = "nvarchar(30)")
  private String seriesName;

  @Column(name = "path", unique = true, nullable = false, columnDefinition = "nvarchar(20)")
  private String path;

  @OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
  private List<Blog> blogs;

  @Override
  public String toString() {
    return "Series{" +
        "seriesId=" + seriesId +
        ", seriesName='" + seriesName + '\'' +
        ", path='" + path + '\'' +
        '}';
  }
}
