package com.urud.app.repository;

import com.urud.app.domain.Tweet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tweet entity.
 */
public interface TweetRepository extends JpaRepository<Tweet,Long> {

}
