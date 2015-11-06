package com.urud.app.repository.search;

import com.urud.app.domain.Manufacturer;




import org.springframework.data.solr.repository.SolrCrudRepository;



/**
 * Spring Data SOLR repository for the Manufacturer entity.
 */
public interface ManufacturerSearchRepository extends SolrCrudRepository<Manufacturer, Long> {
}
