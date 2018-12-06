package com.guomin.service.user.provider.reponsitory.jpa;

import com.guomin.service.user.provider.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface UserRep extends JpaRepository<User,Long> {
    User findByUserId(Long userId);
    Long deleteByUserId(Long userId);
    User findByUserPhone(String userPhone);
    boolean existsByUserPhone(String userPhone);
    boolean existsByUserId(Long userId);
}
