package com.shubham.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubham.major.modle.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
