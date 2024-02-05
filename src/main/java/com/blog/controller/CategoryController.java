package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;
import com.blog.services.impl.CategoryServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name="scheme1")
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryServiceImpl  categoryService;

	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
	CategoryDto createCategory =this.categoryService.createCategory(categoryDto);
	return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
		
		
		
	}
	
	
	
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory (@Valid@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto updatedcategory=this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedcategory);
	}
	
	
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully",true),HttpStatus.OK);
	}
	
	
	
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable Integer categoryId){
		
		return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
	}
	
	
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}
	
	
	
}
