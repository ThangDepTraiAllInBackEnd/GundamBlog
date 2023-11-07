package com.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false, length = 64)
  private String password;

  @Column(nullable = false)
  private String role;

  @Column(nullable = false)
  private Boolean status;

  // to register
  public User(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  // to save with blogs
  public User(Long userId, String username, String password, String role) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @PrimaryKeyJoinColumn
  private UserInfo userInfo;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Blog> blogs;

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", role='" + role + '\'' +
        ", status=" + status +
        '}';
  }
}
