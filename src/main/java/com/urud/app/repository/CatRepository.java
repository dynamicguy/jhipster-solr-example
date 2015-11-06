package com.urud.app.repository;

import com.urud.app.domain.Cat;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cat entity.
 */
public interface CatRepository extends JpaRepository<Cat,Long> {

}
