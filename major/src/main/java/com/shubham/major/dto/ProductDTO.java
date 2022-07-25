package com.shubham.major.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.shubham.major.modle.Category;

import lombok.Data;

@Data

public class ProductDTO {
	private Long id;
	private String name;
	private int categoryId;
	private double price;
	private double weight;
	private String description;
	private String imageName;
}
