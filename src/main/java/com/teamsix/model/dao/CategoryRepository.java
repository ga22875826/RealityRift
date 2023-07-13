package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.item.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
