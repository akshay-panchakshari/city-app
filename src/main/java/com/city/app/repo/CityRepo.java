package com.city.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.city.app.model.City;
@Repository
public interface CityRepo extends JpaRepository<City, Long>{
	Page<City>  findByNameContaining(String name, Pageable page);
}
