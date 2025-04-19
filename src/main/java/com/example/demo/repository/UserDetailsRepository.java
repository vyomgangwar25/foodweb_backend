package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserDetail;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetail, Integer> {

}
