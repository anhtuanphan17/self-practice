package com.traningsprint1.repository;

import com.traningsprint1.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {


}
