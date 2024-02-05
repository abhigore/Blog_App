package com.blog.services;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.blog.payloads.CategoryDto;


public interface CategoryService {

	//create
	 CategoryDto createCategory(CategoryDto categorydto);
		
	
	
	//get
	CategoryDto getCategory(Integer categoryId);
	
	
	//get All
	
	List<CategoryDto> getAllCategory();
	
	//update
	
	CategoryDto updateCategory (CategoryDto categorydto,Integer categoryId);
	
	
	//delete
	void deleteCategory(Integer categoryId );
}
