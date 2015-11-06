package com.urud.app.repository.search;

import com.urud.app.domain.Tweet;




import org.springframework.data.solr.repository.SolrCrudRepository;



/**
 * Spring Data SOLR repository for the Tweet entity.
 */
public interface TweetSearchRepository extends SolrCrudRepository<Tweet, Long> {
}
