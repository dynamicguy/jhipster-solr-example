package com.urud.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urud.app.domain.Cat;
import com.urud.app.repository.CatRepository;
import java.util.stream.StreamSupport;
import com.urud.app.repository.search.CatSearchRepository;
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
 * REST controller for managing Cat.
 */
@RestController
@RequestMapping("/api")
public class CatResource {

    private final Logger log = LoggerFactory.getLogger(CatResource.class);

    @Inject
    private CatRepository catRepository;

    @Inject
    private CatSearchRepository catSearchRepository;

    /**
     * POST  /cats -> Create a new cat.
     */
    @RequestMapping(value = "/cats",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cat> createCat(@Valid @RequestBody Cat cat) throws URISyntaxException {
        log.debug("REST request to save Cat : {}", cat);
        if (cat.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cat cannot already have an ID").body(null);
        }
        Cat result = catRepository.save(cat);
        catSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cat", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cats -> Updates an existing cat.
     */
    @RequestMapping(value = "/cats",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cat> updateCat(@Valid @RequestBody Cat cat) throws URISyntaxException {
        log.debug("REST request to update Cat : {}", cat);
        if (cat.getId() == null) {
            return createCat(cat);
        }
        Cat result = catRepository.save(cat);
        catSearchRepository.save(cat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cat", cat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cats -> get all the cats.
     */
    @RequestMapping(value = "/cats",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cat>> getAllCats(Pageable pageable)
        throws URISyntaxException {
        Page<Cat> page = catRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cats/:id -> get the "id" cat.
     */
    @RequestMapping(value = "/cats/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cat> getCat(@PathVariable Long id) {
        log.debug("REST request to get Cat : {}", id);
        return Optional.ofNullable(catRepository.findOne(id))
            .map(cat -> new ResponseEntity<>(
                cat,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cats/:id -> delete the "id" cat.
     */
    @RequestMapping(value = "/cats/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCat(@PathVariable Long id) {
        log.debug("REST request to delete Cat : {}", id);
        catRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cat", id.toString())).build();
    }


    
     /**
     * SEARCH  /_search/cats/:query -> search for the cat corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cats/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cat> searchCats(@PathVariable String query) {
        return StreamSupport
            .stream(catSearchRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

}
