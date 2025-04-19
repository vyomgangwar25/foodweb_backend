package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
