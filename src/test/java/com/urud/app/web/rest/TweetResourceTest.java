package com.urud.app.web.rest;

import com.urud.app.Application;
import com.urud.app.domain.Tweet;
import com.urud.app.repository.TweetRepository;
import com.urud.app.repository.search.TweetSearchRepository;

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
 * Test class for the TweetResource REST controller.
 *
 * @see TweetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TweetResourceTest {

    private static final String DEFAULT_TWEET = "AAAAA";
    private static final String UPDATED_TWEET = "BBBBB";
    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";
    private static final String DEFAULT_SOURCE = "AAAAA";
    private static final String UPDATED_SOURCE = "BBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final String DEFAULT_USER_LANG = "AAAAA";
    private static final String UPDATED_USER_LANG = "BBBBB";
    private static final String DEFAULT_USER_NAME = "AAAAA";
    private static final String UPDATED_USER_NAME = "BBBBB";
    private static final String DEFAULT_USER_SCREEN_NAME = "AAAAA";
    private static final String UPDATED_USER_SCREEN_NAME = "BBBBB";
    private static final String DEFAULT_USER_LOCATION = "AAAAA";
    private static final String UPDATED_USER_LOCATION = "BBBBB";

    private static final Long DEFAULT_MEDIA_ID = 1L;
    private static final Long UPDATED_MEDIA_ID = 2L;
    private static final String DEFAULT_MEDIA_URL = "AAAAA";
    private static final String UPDATED_MEDIA_URL = "BBBBB";
    private static final String DEFAULT_IN_REPLY_TO_STATUS_ID = "AAAAA";
    private static final String UPDATED_IN_REPLY_TO_STATUS_ID = "BBBBB";
    private static final String DEFAULT_IN_REPLY_TO_SCREEN_NAME = "AAAAA";
    private static final String UPDATED_IN_REPLY_TO_SCREEN_NAME = "BBBBB";

    private static final Long DEFAULT_IN_REPLY_TO_USER_ID = 1L;
    private static final Long UPDATED_IN_REPLY_TO_USER_ID = 2L;
    private static final String DEFAULT_URL_DISPLAY = "AAAAA";
    private static final String UPDATED_URL_DISPLAY = "BBBBB";
    private static final String DEFAULT_URL_EXPANDED = "AAAAA";
    private static final String UPDATED_URL_EXPANDED = "BBBBB";
    private static final String DEFAULT_KEYWORDS = "AAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBB";
    private static final String DEFAULT_PLACE_ID = "AAAAA";
    private static final String UPDATED_PLACE_ID = "BBBBB";
    private static final String DEFAULT_PLACE_TYPE = "AAAAA";
    private static final String UPDATED_PLACE_TYPE = "BBBBB";
    private static final String DEFAULT_PLACE_NAME = "AAAAA";
    private static final String UPDATED_PLACE_NAME = "BBBBB";
    private static final String DEFAULT_PLACE_URL = "AAAAA";
    private static final String UPDATED_PLACE_URL = "BBBBB";
    private static final String DEFAULT_PLACE_FULL_NAME = "AAAAA";
    private static final String UPDATED_PLACE_FULL_NAME = "BBBBB";
    private static final String DEFAULT_PLACE_COUNTRY = "AAAAA";
    private static final String UPDATED_PLACE_COUNTRY = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_USER_MENTION_SCREEN_NAME = "AAAAA";
    private static final String UPDATED_USER_MENTION_SCREEN_NAME = "BBBBB";
    private static final String DEFAULT_USER_MENTION_NAME = "AAAAA";
    private static final String UPDATED_USER_MENTION_NAME = "BBBBB";

    private static final Boolean DEFAULT_IS_POSSIBLY_SENSITIVE = false;
    private static final Boolean UPDATED_IS_POSSIBLY_SENSITIVE = true;

    private static final Boolean DEFAULT_IS_RETWEETED_BY_ME = false;
    private static final Boolean UPDATED_IS_RETWEETED_BY_ME = true;

    private static final Boolean DEFAULT_IS_RETWEET = false;
    private static final Boolean UPDATED_IS_RETWEET = true;

    private static final Boolean DEFAULT_IS_FAVORITED = false;
    private static final Boolean UPDATED_IS_FAVORITED = true;

    private static final Boolean DEFAULT_IS_TRUNCATED = false;
    private static final Boolean UPDATED_IS_TRUNCATED = true;

    private static final Long DEFAULT_RETWEET_COUNT = 1L;
    private static final Long UPDATED_RETWEET_COUNT = 2L;

    @Inject
    private TweetRepository tweetRepository;

    @Inject
    private TweetSearchRepository tweetSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTweetMockMvc;

    private Tweet tweet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TweetResource tweetResource = new TweetResource();
        ReflectionTestUtils.setField(tweetResource, "tweetRepository", tweetRepository);
        ReflectionTestUtils.setField(tweetResource, "tweetSearchRepository", tweetSearchRepository);
        this.restTweetMockMvc = MockMvcBuilders.standaloneSetup(tweetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tweet = new Tweet();
        tweet.setTweet(DEFAULT_TWEET);
        tweet.setUrl(DEFAULT_URL);
        tweet.setSource(DEFAULT_SOURCE);
        tweet.setUserId(DEFAULT_USER_ID);
        tweet.setUserLang(DEFAULT_USER_LANG);
        tweet.setUserName(DEFAULT_USER_NAME);
        tweet.setUserScreenName(DEFAULT_USER_SCREEN_NAME);
        tweet.setUserLocation(DEFAULT_USER_LOCATION);
        tweet.setMediaId(DEFAULT_MEDIA_ID);
        tweet.setMediaUrl(DEFAULT_MEDIA_URL);
        tweet.setInReplyToStatusId(DEFAULT_IN_REPLY_TO_STATUS_ID);
        tweet.setInReplyToScreenName(DEFAULT_IN_REPLY_TO_SCREEN_NAME);
        tweet.setInReplyToUserId(DEFAULT_IN_REPLY_TO_USER_ID);
        tweet.setUrlDisplay(DEFAULT_URL_DISPLAY);
        tweet.setUrlExpanded(DEFAULT_URL_EXPANDED);
        tweet.setKeywords(DEFAULT_KEYWORDS);
        tweet.setPlaceId(DEFAULT_PLACE_ID);
        tweet.setPlaceType(DEFAULT_PLACE_TYPE);
        tweet.setPlaceName(DEFAULT_PLACE_NAME);
        tweet.setPlaceURL(DEFAULT_PLACE_URL);
        tweet.setPlaceFullName(DEFAULT_PLACE_FULL_NAME);
        tweet.setPlaceCountry(DEFAULT_PLACE_COUNTRY);
        tweet.setLocation(DEFAULT_LOCATION);
        tweet.setUserMentionScreenName(DEFAULT_USER_MENTION_SCREEN_NAME);
        tweet.setUserMentionName(DEFAULT_USER_MENTION_NAME);
        tweet.setIsPossiblySensitive(DEFAULT_IS_POSSIBLY_SENSITIVE);
        tweet.setIsRetweetedByMe(DEFAULT_IS_RETWEETED_BY_ME);
        tweet.setIsRetweet(DEFAULT_IS_RETWEET);
        tweet.setIsFavorited(DEFAULT_IS_FAVORITED);
        tweet.setIsTruncated(DEFAULT_IS_TRUNCATED);
        tweet.setRetweetCount(DEFAULT_RETWEET_COUNT);
    }

    @Test
    @Transactional
    public void createTweet() throws Exception {
        int databaseSizeBeforeCreate = tweetRepository.findAll().size();

        // Create the Tweet

        restTweetMockMvc.perform(post("/api/tweets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tweet)))
                .andExpect(status().isCreated());

        // Validate the Tweet in the database
        List<Tweet> tweets = tweetRepository.findAll();
        assertThat(tweets).hasSize(databaseSizeBeforeCreate + 1);
        Tweet testTweet = tweets.get(tweets.size() - 1);
        assertThat(testTweet.getTweet()).isEqualTo(DEFAULT_TWEET);
        assertThat(testTweet.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testTweet.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testTweet.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTweet.getUserLang()).isEqualTo(DEFAULT_USER_LANG);
        assertThat(testTweet.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testTweet.getUserScreenName()).isEqualTo(DEFAULT_USER_SCREEN_NAME);
        assertThat(testTweet.getUserLocation()).isEqualTo(DEFAULT_USER_LOCATION);
        assertThat(testTweet.getMediaId()).isEqualTo(DEFAULT_MEDIA_ID);
        assertThat(testTweet.getMediaUrl()).isEqualTo(DEFAULT_MEDIA_URL);
        assertThat(testTweet.getInReplyToStatusId()).isEqualTo(DEFAULT_IN_REPLY_TO_STATUS_ID);
        assertThat(testTweet.getInReplyToScreenName()).isEqualTo(DEFAULT_IN_REPLY_TO_SCREEN_NAME);
        assertThat(testTweet.getInReplyToUserId()).isEqualTo(DEFAULT_IN_REPLY_TO_USER_ID);
        assertThat(testTweet.getUrlDisplay()).isEqualTo(DEFAULT_URL_DISPLAY);
        assertThat(testTweet.getUrlExpanded()).isEqualTo(DEFAULT_URL_EXPANDED);
        assertThat(testTweet.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testTweet.getPlaceId()).isEqualTo(DEFAULT_PLACE_ID);
        assertThat(testTweet.getPlaceType()).isEqualTo(DEFAULT_PLACE_TYPE);
        assertThat(testTweet.getPlaceName()).isEqualTo(DEFAULT_PLACE_NAME);
        assertThat(testTweet.getPlaceURL()).isEqualTo(DEFAULT_PLACE_URL);
        assertThat(testTweet.getPlaceFullName()).isEqualTo(DEFAULT_PLACE_FULL_NAME);
        assertThat(testTweet.getPlaceCountry()).isEqualTo(DEFAULT_PLACE_COUNTRY);
        assertThat(testTweet.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTweet.getUserMentionScreenName()).isEqualTo(DEFAULT_USER_MENTION_SCREEN_NAME);
        assertThat(testTweet.getUserMentionName()).isEqualTo(DEFAULT_USER_MENTION_NAME);
        assertThat(testTweet.getIsPossiblySensitive()).isEqualTo(DEFAULT_IS_POSSIBLY_SENSITIVE);
        assertThat(testTweet.getIsRetweetedByMe()).isEqualTo(DEFAULT_IS_RETWEETED_BY_ME);
        assertThat(testTweet.getIsRetweet()).isEqualTo(DEFAULT_IS_RETWEET);
        assertThat(testTweet.getIsFavorited()).isEqualTo(DEFAULT_IS_FAVORITED);
        assertThat(testTweet.getIsTruncated()).isEqualTo(DEFAULT_IS_TRUNCATED);
        assertThat(testTweet.getRetweetCount()).isEqualTo(DEFAULT_RETWEET_COUNT);
    }

    @Test
    @Transactional
    public void checkTweetIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetRepository.findAll().size();
        // set the field null
        tweet.setTweet(null);

        // Create the Tweet, which fails.

        restTweetMockMvc.perform(post("/api/tweets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tweet)))
                .andExpect(status().isBadRequest());

        List<Tweet> tweets = tweetRepository.findAll();
        assertThat(tweets).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTweets() throws Exception {
        // Initialize the database
        tweetRepository.saveAndFlush(tweet);

        // Get all the tweets
        restTweetMockMvc.perform(get("/api/tweets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tweet.getId().intValue())))
                .andExpect(jsonPath("$.[*].tweet").value(hasItem(DEFAULT_TWEET.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].userLang").value(hasItem(DEFAULT_USER_LANG.toString())))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
                .andExpect(jsonPath("$.[*].userScreenName").value(hasItem(DEFAULT_USER_SCREEN_NAME.toString())))
                .andExpect(jsonPath("$.[*].userLocation").value(hasItem(DEFAULT_USER_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].mediaId").value(hasItem(DEFAULT_MEDIA_ID.intValue())))
                .andExpect(jsonPath("$.[*].mediaUrl").value(hasItem(DEFAULT_MEDIA_URL.toString())))
                .andExpect(jsonPath("$.[*].inReplyToStatusId").value(hasItem(DEFAULT_IN_REPLY_TO_STATUS_ID.toString())))
                .andExpect(jsonPath("$.[*].inReplyToScreenName").value(hasItem(DEFAULT_IN_REPLY_TO_SCREEN_NAME.toString())))
                .andExpect(jsonPath("$.[*].inReplyToUserId").value(hasItem(DEFAULT_IN_REPLY_TO_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].urlDisplay").value(hasItem(DEFAULT_URL_DISPLAY.toString())))
                .andExpect(jsonPath("$.[*].urlExpanded").value(hasItem(DEFAULT_URL_EXPANDED.toString())))
                .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
                .andExpect(jsonPath("$.[*].placeId").value(hasItem(DEFAULT_PLACE_ID.toString())))
                .andExpect(jsonPath("$.[*].placeType").value(hasItem(DEFAULT_PLACE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].placeName").value(hasItem(DEFAULT_PLACE_NAME.toString())))
                .andExpect(jsonPath("$.[*].placeURL").value(hasItem(DEFAULT_PLACE_URL.toString())))
                .andExpect(jsonPath("$.[*].placeFullName").value(hasItem(DEFAULT_PLACE_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].placeCountry").value(hasItem(DEFAULT_PLACE_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].userMentionScreenName").value(hasItem(DEFAULT_USER_MENTION_SCREEN_NAME.toString())))
                .andExpect(jsonPath("$.[*].userMentionName").value(hasItem(DEFAULT_USER_MENTION_NAME.toString())))
                .andExpect(jsonPath("$.[*].isPossiblySensitive").value(hasItem(DEFAULT_IS_POSSIBLY_SENSITIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].isRetweetedByMe").value(hasItem(DEFAULT_IS_RETWEETED_BY_ME.booleanValue())))
                .andExpect(jsonPath("$.[*].isRetweet").value(hasItem(DEFAULT_IS_RETWEET.booleanValue())))
                .andExpect(jsonPath("$.[*].isFavorited").value(hasItem(DEFAULT_IS_FAVORITED.booleanValue())))
                .andExpect(jsonPath("$.[*].isTruncated").value(hasItem(DEFAULT_IS_TRUNCATED.booleanValue())))
                .andExpect(jsonPath("$.[*].retweetCount").value(hasItem(DEFAULT_RETWEET_COUNT.intValue())));
    }

    @Test
    @Transactional
    public void getTweet() throws Exception {
        // Initialize the database
        tweetRepository.saveAndFlush(tweet);

        // Get the tweet
        restTweetMockMvc.perform(get("/api/tweets/{id}", tweet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tweet.getId().intValue()))
            .andExpect(jsonPath("$.tweet").value(DEFAULT_TWEET.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userLang").value(DEFAULT_USER_LANG.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.userScreenName").value(DEFAULT_USER_SCREEN_NAME.toString()))
            .andExpect(jsonPath("$.userLocation").value(DEFAULT_USER_LOCATION.toString()))
            .andExpect(jsonPath("$.mediaId").value(DEFAULT_MEDIA_ID.intValue()))
            .andExpect(jsonPath("$.mediaUrl").value(DEFAULT_MEDIA_URL.toString()))
            .andExpect(jsonPath("$.inReplyToStatusId").value(DEFAULT_IN_REPLY_TO_STATUS_ID.toString()))
            .andExpect(jsonPath("$.inReplyToScreenName").value(DEFAULT_IN_REPLY_TO_SCREEN_NAME.toString()))
            .andExpect(jsonPath("$.inReplyToUserId").value(DEFAULT_IN_REPLY_TO_USER_ID.intValue()))
            .andExpect(jsonPath("$.urlDisplay").value(DEFAULT_URL_DISPLAY.toString()))
            .andExpect(jsonPath("$.urlExpanded").value(DEFAULT_URL_EXPANDED.toString()))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.placeId").value(DEFAULT_PLACE_ID.toString()))
            .andExpect(jsonPath("$.placeType").value(DEFAULT_PLACE_TYPE.toString()))
            .andExpect(jsonPath("$.placeName").value(DEFAULT_PLACE_NAME.toString()))
            .andExpect(jsonPath("$.placeURL").value(DEFAULT_PLACE_URL.toString()))
            .andExpect(jsonPath("$.placeFullName").value(DEFAULT_PLACE_FULL_NAME.toString()))
            .andExpect(jsonPath("$.placeCountry").value(DEFAULT_PLACE_COUNTRY.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.userMentionScreenName").value(DEFAULT_USER_MENTION_SCREEN_NAME.toString()))
            .andExpect(jsonPath("$.userMentionName").value(DEFAULT_USER_MENTION_NAME.toString()))
            .andExpect(jsonPath("$.isPossiblySensitive").value(DEFAULT_IS_POSSIBLY_SENSITIVE.booleanValue()))
            .andExpect(jsonPath("$.isRetweetedByMe").value(DEFAULT_IS_RETWEETED_BY_ME.booleanValue()))
            .andExpect(jsonPath("$.isRetweet").value(DEFAULT_IS_RETWEET.booleanValue()))
            .andExpect(jsonPath("$.isFavorited").value(DEFAULT_IS_FAVORITED.booleanValue()))
            .andExpect(jsonPath("$.isTruncated").value(DEFAULT_IS_TRUNCATED.booleanValue()))
            .andExpect(jsonPath("$.retweetCount").value(DEFAULT_RETWEET_COUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTweet() throws Exception {
        // Get the tweet
        restTweetMockMvc.perform(get("/api/tweets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTweet() throws Exception {
        // Initialize the database
        tweetRepository.saveAndFlush(tweet);

		int databaseSizeBeforeUpdate = tweetRepository.findAll().size();

        // Update the tweet
        tweet.setTweet(UPDATED_TWEET);
        tweet.setUrl(UPDATED_URL);
        tweet.setSource(UPDATED_SOURCE);
        tweet.setUserId(UPDATED_USER_ID);
        tweet.setUserLang(UPDATED_USER_LANG);
        tweet.setUserName(UPDATED_USER_NAME);
        tweet.setUserScreenName(UPDATED_USER_SCREEN_NAME);
        tweet.setUserLocation(UPDATED_USER_LOCATION);
        tweet.setMediaId(UPDATED_MEDIA_ID);
        tweet.setMediaUrl(UPDATED_MEDIA_URL);
        tweet.setInReplyToStatusId(UPDATED_IN_REPLY_TO_STATUS_ID);
        tweet.setInReplyToScreenName(UPDATED_IN_REPLY_TO_SCREEN_NAME);
        tweet.setInReplyToUserId(UPDATED_IN_REPLY_TO_USER_ID);
        tweet.setUrlDisplay(UPDATED_URL_DISPLAY);
        tweet.setUrlExpanded(UPDATED_URL_EXPANDED);
        tweet.setKeywords(UPDATED_KEYWORDS);
        tweet.setPlaceId(UPDATED_PLACE_ID);
        tweet.setPlaceType(UPDATED_PLACE_TYPE);
        tweet.setPlaceName(UPDATED_PLACE_NAME);
        tweet.setPlaceURL(UPDATED_PLACE_URL);
        tweet.setPlaceFullName(UPDATED_PLACE_FULL_NAME);
        tweet.setPlaceCountry(UPDATED_PLACE_COUNTRY);
        tweet.setLocation(UPDATED_LOCATION);
        tweet.setUserMentionScreenName(UPDATED_USER_MENTION_SCREEN_NAME);
        tweet.setUserMentionName(UPDATED_USER_MENTION_NAME);
        tweet.setIsPossiblySensitive(UPDATED_IS_POSSIBLY_SENSITIVE);
        tweet.setIsRetweetedByMe(UPDATED_IS_RETWEETED_BY_ME);
        tweet.setIsRetweet(UPDATED_IS_RETWEET);
        tweet.setIsFavorited(UPDATED_IS_FAVORITED);
        tweet.setIsTruncated(UPDATED_IS_TRUNCATED);
        tweet.setRetweetCount(UPDATED_RETWEET_COUNT);

        restTweetMockMvc.perform(put("/api/tweets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tweet)))
                .andExpect(status().isOk());

        // Validate the Tweet in the database
        List<Tweet> tweets = tweetRepository.findAll();
        assertThat(tweets).hasSize(databaseSizeBeforeUpdate);
        Tweet testTweet = tweets.get(tweets.size() - 1);
        assertThat(testTweet.getTweet()).isEqualTo(UPDATED_TWEET);
        assertThat(testTweet.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testTweet.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testTweet.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTweet.getUserLang()).isEqualTo(UPDATED_USER_LANG);
        assertThat(testTweet.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testTweet.getUserScreenName()).isEqualTo(UPDATED_USER_SCREEN_NAME);
        assertThat(testTweet.getUserLocation()).isEqualTo(UPDATED_USER_LOCATION);
        assertThat(testTweet.getMediaId()).isEqualTo(UPDATED_MEDIA_ID);
        assertThat(testTweet.getMediaUrl()).isEqualTo(UPDATED_MEDIA_URL);
        assertThat(testTweet.getInReplyToStatusId()).isEqualTo(UPDATED_IN_REPLY_TO_STATUS_ID);
        assertThat(testTweet.getInReplyToScreenName()).isEqualTo(UPDATED_IN_REPLY_TO_SCREEN_NAME);
        assertThat(testTweet.getInReplyToUserId()).isEqualTo(UPDATED_IN_REPLY_TO_USER_ID);
        assertThat(testTweet.getUrlDisplay()).isEqualTo(UPDATED_URL_DISPLAY);
        assertThat(testTweet.getUrlExpanded()).isEqualTo(UPDATED_URL_EXPANDED);
        assertThat(testTweet.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testTweet.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testTweet.getPlaceType()).isEqualTo(UPDATED_PLACE_TYPE);
        assertThat(testTweet.getPlaceName()).isEqualTo(UPDATED_PLACE_NAME);
        assertThat(testTweet.getPlaceURL()).isEqualTo(UPDATED_PLACE_URL);
        assertThat(testTweet.getPlaceFullName()).isEqualTo(UPDATED_PLACE_FULL_NAME);
        assertThat(testTweet.getPlaceCountry()).isEqualTo(UPDATED_PLACE_COUNTRY);
        assertThat(testTweet.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTweet.getUserMentionScreenName()).isEqualTo(UPDATED_USER_MENTION_SCREEN_NAME);
        assertThat(testTweet.getUserMentionName()).isEqualTo(UPDATED_USER_MENTION_NAME);
        assertThat(testTweet.getIsPossiblySensitive()).isEqualTo(UPDATED_IS_POSSIBLY_SENSITIVE);
        assertThat(testTweet.getIsRetweetedByMe()).isEqualTo(UPDATED_IS_RETWEETED_BY_ME);
        assertThat(testTweet.getIsRetweet()).isEqualTo(UPDATED_IS_RETWEET);
        assertThat(testTweet.getIsFavorited()).isEqualTo(UPDATED_IS_FAVORITED);
        assertThat(testTweet.getIsTruncated()).isEqualTo(UPDATED_IS_TRUNCATED);
        assertThat(testTweet.getRetweetCount()).isEqualTo(UPDATED_RETWEET_COUNT);
    }

    @Test
    @Transactional
    public void deleteTweet() throws Exception {
        // Initialize the database
        tweetRepository.saveAndFlush(tweet);

		int databaseSizeBeforeDelete = tweetRepository.findAll().size();

        // Get the tweet
        restTweetMockMvc.perform(delete("/api/tweets/{id}", tweet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tweet> tweets = tweetRepository.findAll();
        assertThat(tweets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
