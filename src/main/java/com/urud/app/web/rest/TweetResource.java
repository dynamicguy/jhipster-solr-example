package com.urud.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urud.app.domain.Tweet;
import com.urud.app.repository.TweetRepository;
import java.util.stream.StreamSupport;
import com.urud.app.repository.search.TweetSearchRepository;
import com.urud.app.web.rest.util.HeaderUtil;
import com.urud.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Tweet.
 */
@RestController
@RequestMapping("/api")
public class TweetResource {

    private final Logger log = LoggerFactory.getLogger(TweetResource.class);

    @Inject
    private TweetRepository tweetRepository;

    @Inject
    private TweetSearchRepository tweetSearchRepository;

    /**
     * POST  /tweets -> Create a new tweet.
     */
    @RequestMapping(value = "/tweets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tweet> createTweet(@Valid @RequestBody Tweet tweet) throws URISyntaxException {
        log.debug("REST request to save Tweet : {}", tweet);
        if (tweet.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tweet cannot already have an ID").body(null);
        }
        Tweet result = tweetRepository.save(tweet);
        tweetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tweets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tweet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tweets -> Updates an existing tweet.
     */
    @RequestMapping(value = "/tweets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tweet> updateTweet(@Valid @RequestBody Tweet tweet) throws URISyntaxException {
        log.debug("REST request to update Tweet : {}", tweet);
        if (tweet.getId() == null) {
            return createTweet(tweet);
        }
        Tweet result = tweetRepository.save(tweet);
        tweetSearchRepository.save(tweet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tweet", tweet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tweets -> get all the tweets.
     */
    @RequestMapping(value = "/tweets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tweet>> getAllTweets(Pageable pageable)
        throws URISyntaxException {
        Page<Tweet> page = tweetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tweets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tweets/:id -> get the "id" tweet.
     */
    @RequestMapping(value = "/tweets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tweet> getTweet(@PathVariable Long id) {
        log.debug("REST request to get Tweet : {}", id);
        return Optional.ofNullable(tweetRepository.findOne(id))
            .map(tweet -> new ResponseEntity<>(
                tweet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tweets/:id -> delete the "id" tweet.
     */
    @RequestMapping(value = "/tweets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        log.debug("REST request to delete Tweet : {}", id);
        tweetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tweet", id.toString())).build();
    }


    
     /**
     * SEARCH  /_search/tweets/:query -> search for the tweet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/tweets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Tweet> searchTweets(@PathVariable String query) {
        return StreamSupport
            .stream(tweetSearchRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

}
