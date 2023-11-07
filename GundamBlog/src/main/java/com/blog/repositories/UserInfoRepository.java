package com.blog.repositories;

import com.blog.models.Tag;
import com.blog.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
  UserInfo findByPhone(String phone);

  UserInfo findByEmail(String email);
}
