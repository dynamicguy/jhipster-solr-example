package com.urud.app.repository;

import com.urud.app.domain.Manufacturer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Manufacturer entity.
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {

}
