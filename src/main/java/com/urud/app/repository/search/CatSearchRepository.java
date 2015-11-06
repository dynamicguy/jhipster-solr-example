package com.urud.app.repository.search;

import com.urud.app.domain.Cat;




import org.springframework.data.solr.repository.SolrCrudRepository;



/**
 * Spring Data SOLR repository for the Cat entity.
 */
public interface CatSearchRepository extends SolrCrudRepository<Cat, Long> {
}
