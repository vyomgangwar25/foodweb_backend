package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Items;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {
	@Query("SELECT i FROM Items i WHERE i.name LIKE %:text% OR str(i.rating) LIKE %:text%")
    List<Items> findByNameOrRatingContaining(@Param("text") String text);

}
