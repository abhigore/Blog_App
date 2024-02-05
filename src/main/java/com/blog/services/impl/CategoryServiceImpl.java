package com.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repisotories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl  implements CategoryService{
	
	
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	
	//create- method
	@Override
	public CategoryDto createCategory(CategoryDto categorydto) {
		Category cat= this.modelMapper.map(categorydto, Category.class);
		Category addcat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addcat, CategoryDto.class);
		
		
	}
	
	
	
	
	//update -method
	@Override
	public CategoryDto updateCategory(CategoryDto categorydto, Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));
		cat.setCategorytitle(categorydto.getCategorytitle());
		cat.setCategorydescription(categorydto.getCategorydescription());
		Category updatedcat=this.categoryRepo.save(cat);
		CategoryDto updateddto=this.modelMapper.map(updatedcat, CategoryDto.class);
		return updateddto;
	}
	
	
	
	
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId", categoryId));
		CategoryDto catdto=this.modelMapper.map(cat, CategoryDto.class);
		return catdto;
	}
	
	
	
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> catgories=this.categoryRepo.findAll();
		List<CategoryDto>catDtos=catgories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}
	
	
	
    @Override
	public void deleteCategory(Integer categoryId) {
    Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryID", categoryId));
	this.categoryRepo.delete(cat);
		
	}
   
    
//    private CategoryDto categorytoDto(Category category) {
//    	CategoryDto categorydto= this.modelMapper.map(category,CategoryDto.class);
//    	return categorydto;
//    }
//    
//    private Category categoryDtoToCategory (CategoryDto categorydto) {
//    	Category category=this.modelMapper.map(categorydto,Category.class);
//    	return category;
//    }
	

}
