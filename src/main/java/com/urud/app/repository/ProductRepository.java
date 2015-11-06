package com.urud.app.repository;

import com.urud.app.domain.Product;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select product from Product product where product.user.login = ?#{principal.username}")
    List<Product> findByUserIsCurrentUser();

    @Query("select distinct product from Product product left join fetch product.cats left join fetch product.manufacturers")
    List<Product> findAllWithEagerRelationships();

    @Query("select product from Product product left join fetch product.cats left join fetch product.manufacturers where product.id =:id")
    Product findOneWithEagerRelationships(@Param("id") Long id);

}
