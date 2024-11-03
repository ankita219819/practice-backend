package com.complete.cure.app.repository;


import com.complete.cure.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}