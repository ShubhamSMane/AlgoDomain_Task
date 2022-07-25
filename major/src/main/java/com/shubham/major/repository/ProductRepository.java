package com.shubham.major.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubham.major.modle.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

	List<Product> findAllBycategory_Id(int id);

}
