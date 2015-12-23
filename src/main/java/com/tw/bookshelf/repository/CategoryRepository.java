package com.tw.bookshelf.repository;

import com.tw.bookshelf.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

    Category findByName(String name);

}
