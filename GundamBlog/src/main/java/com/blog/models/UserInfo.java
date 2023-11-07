package com.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_info")
public class UserInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long userId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String phone;

  // to insert
  public UserInfo(String name, String email, String phone) {
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  @MapsId
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
  private User user;

  @Override
  public String toString() {
    return "UserInfo{" +
        "userId=" + userId +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        '}';
  }
}
