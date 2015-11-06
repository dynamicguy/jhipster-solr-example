package com.urud.app.repository.search;

import com.urud.app.domain.User;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;


/**
 * Spring Data SOLR repository for the User entity.
 */
public interface UserSearchRepository extends SolrCrudRepository<User, Long> {


    List<User> findByLoginStartingWith(String query);

}
