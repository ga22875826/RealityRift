package com.teamsix.controller.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamsix.model.bean.item.Category;
import com.teamsix.model.bean.item.CategoryRequestDTO;
import com.teamsix.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping("/{id}")
	public Category getCategory(@PathVariable int id) {
		return categoryService.getCategoryById(id);
	}

	@PostMapping
	public Category createCategory(@RequestBody CategoryRequestDTO categoryRequest) {
		Category parentCategory = null;
		if (categoryRequest.getParentid() != null) {
			parentCategory = categoryService.getCategoryById(categoryRequest.getParentid());
		}

		Category category = new Category();
		category.setName(categoryRequest.getName());
		category.setParent(parentCategory);

		return categoryService.createCategory(category);
	}

	@PutMapping
	public Category updateCategory(@RequestBody CategoryRequestDTO categoryRequest) {
		Category parentCategory = null;
		if (categoryRequest.getParentid() != null) {
			parentCategory = categoryService.getCategoryById(categoryRequest.getParentid());
		}

		Category category = categoryService.getCategoryById(categoryRequest.getId());
		category.setName(categoryRequest.getName());
		category.setParent(parentCategory);

		return categoryService.updateCategory(category);
	}

	@DeleteMapping("/{id}")
	public String deleteCategory(@PathVariable int id) {
		categoryService.deleteCategory(id);
		return "Deleted category id - " + id;
	}
}
