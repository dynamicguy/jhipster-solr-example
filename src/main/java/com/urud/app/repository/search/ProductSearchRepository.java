package com.urud.app.repository.search;

import com.urud.app.domain.Product;




import org.springframework.data.solr.repository.SolrCrudRepository;



/**
 * Spring Data SOLR repository for the Product entity.
 */
public interface ProductSearchRepository extends SolrCrudRepository<Product, Long> {
}
