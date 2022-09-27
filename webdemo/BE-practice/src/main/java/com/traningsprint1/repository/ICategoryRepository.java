package com.traningsprint1.repository;

import com.traningsprint1.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ICategoryRepository
 * @Version: 20-sept-2022
 * @Author: TuanPA3
 * */
public interface ICategoryRepository extends JpaRepository<Category,Long> {

}
