package com.urud.app.web.rest;

import com.urud.app.Application;
import com.urud.app.domain.Cat;
import com.urud.app.repository.CatRepository;
import com.urud.app.repository.search.CatSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CatResource REST controller.
 *
 * @see CatResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CatResourceTest {

    private static final String DEFAULT_CAT = "AAA";
    private static final String UPDATED_CAT = "BBB";

    @Inject
    private CatRepository catRepository;

    @Inject
    private CatSearchRepository catSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCatMockMvc;

    private Cat cat;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CatResource catResource = new CatResource();
        ReflectionTestUtils.setField(catResource, "catRepository", catRepository);
        ReflectionTestUtils.setField(catResource, "catSearchRepository", catSearchRepository);
        this.restCatMockMvc = MockMvcBuilders.standaloneSetup(catResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cat = new Cat();
        cat.setCat(DEFAULT_CAT);
    }

    @Test
    @Transactional
    public void createCat() throws Exception {
        int databaseSizeBeforeCreate = catRepository.findAll().size();

        // Create the Cat

        restCatMockMvc.perform(post("/api/cats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cat)))
                .andExpect(status().isCreated());

        // Validate the Cat in the database
        List<Cat> cats = catRepository.findAll();
        assertThat(cats).hasSize(databaseSizeBeforeCreate + 1);
        Cat testCat = cats.get(cats.size() - 1);
        assertThat(testCat.getCat()).isEqualTo(DEFAULT_CAT);
    }

    @Test
    @Transactional
    public void checkCatIsRequired() throws Exception {
        int databaseSizeBeforeTest = catRepository.findAll().size();
        // set the field null
        cat.setCat(null);

        // Create the Cat, which fails.

        restCatMockMvc.perform(post("/api/cats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cat)))
                .andExpect(status().isBadRequest());

        List<Cat> cats = catRepository.findAll();
        assertThat(cats).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCats() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        // Get all the cats
        restCatMockMvc.perform(get("/api/cats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cat.getId().intValue())))
                .andExpect(jsonPath("$.[*].cat").value(hasItem(DEFAULT_CAT.toString())));
    }

    @Test
    @Transactional
    public void getCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        // Get the cat
        restCatMockMvc.perform(get("/api/cats/{id}", cat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cat.getId().intValue()))
            .andExpect(jsonPath("$.cat").value(DEFAULT_CAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCat() throws Exception {
        // Get the cat
        restCatMockMvc.perform(get("/api/cats/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

		int databaseSizeBeforeUpdate = catRepository.findAll().size();

        // Update the cat
        cat.setCat(UPDATED_CAT);

        restCatMockMvc.perform(put("/api/cats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cat)))
                .andExpect(status().isOk());

        // Validate the Cat in the database
        List<Cat> cats = catRepository.findAll();
        assertThat(cats).hasSize(databaseSizeBeforeUpdate);
        Cat testCat = cats.get(cats.size() - 1);
        assertThat(testCat.getCat()).isEqualTo(UPDATED_CAT);
    }

    @Test
    @Transactional
    public void deleteCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

		int databaseSizeBeforeDelete = catRepository.findAll().size();

        // Get the cat
        restCatMockMvc.perform(delete("/api/cats/{id}", cat.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cat> cats = catRepository.findAll();
        assertThat(cats).hasSize(databaseSizeBeforeDelete - 1);
    }
}
