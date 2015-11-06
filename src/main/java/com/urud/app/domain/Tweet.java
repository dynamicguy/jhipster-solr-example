package com.urud.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tweet.
 */
@Entity
@Table(name = "tweet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SolrDocument(solrCoreName = "tweets")
public class Tweet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 144)
    @Column(name = "tweet", length = 144, nullable = false)
    @Field("tweet")
    private String tweet;

    @Column(name = "url")
    @Field("url")
    private String url;

    @Column(name = "source")
    @Field("source")
    private String source;

    @Column(name = "user_id")
    @Field("user_id")
    private Long userId;

    @Column(name = "user_lang")
    @Field("user_lang")
    private String userLang;

    @Column(name = "user_name")
    @Field("user_name")
    private String userName;

    @Column(name = "user_screen_name")
    @Field("user_screen_name")
    private String userScreenName;

    @Column(name = "user_location")
    @Field("user_location")
    private String userLocation;

    @Column(name = "media_id")
    @Field("media_id")
    private Long mediaId;

    @Column(name = "media_url")
    @Field("media_url")
    private String mediaUrl;

    @Column(name = "in_reply_to_status_id")
    @Field("in_reply_to_status_id")
    private String inReplyToStatusId;

    @Column(name = "in_reply_to_screen_name")
    @Field("in_reply_to_screen_name")
    private String inReplyToScreenName;

    @Column(name = "in_reply_to_user_id")
    @Field("in_reply_to_user_id")
    private Long inReplyToUserId;

    @Column(name = "url_display")
    @Field("url_display")
    private String urlDisplay;

    @Column(name = "url_expanded")
    @Field("url_expanded")
    private String urlExpanded;

    @Column(name = "keywords")
    @Field("keywords")
    private String keywords;

    @Column(name = "place_id")
    @Field("place_id")
    private String placeId;

    @Column(name = "place_type")
    @Field("place_type")
    private String placeType;

    @Column(name = "place_name")
    @Field("place_name")
    private String placeName;

    @Column(name = "place_url")
    @Field("place_url")
    private String placeURL;

    @Column(name = "place_full_name")
    @Field("place_full_name")
    private String placeFullName;

    @Column(name = "place_country")
    @Field("place_country")
    private String placeCountry;

    @Column(name = "location")
    @Field("location")
    private String location;

    @Column(name = "user_mention_screen_name")
    @Field("user_mention_screen_name")
    private String userMentionScreenName;

    @Column(name = "user_mention_name")
    @Field("user_mention_name")
    private String userMentionName;

    @Column(name = "is_possibly_sensitive")
    @Field("is_possibly_sensitive")
    private Boolean isPossiblySensitive;

    @Column(name = "is_retweeted_by_me")
    @Field("is_retweeted_by_me")
    private Boolean isRetweetedByMe;

    @Column(name = "is_retweet")
    @Field("is_retweet")
    private Boolean isRetweet;

    @Column(name = "is_favorited")
    @Field("is_favorited")
    private Boolean isFavorited;

    @Column(name = "is_truncated")
    @Field("is_truncated")
    private Boolean isTruncated;

    @Column(name = "retweet_count")
    @Field("retweet_count")
    private Long retweetCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLang() {
        return userLang;
    }

    public void setUserLang(String userLang) {
        this.userLang = userLang;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(String inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public Long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(Long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getUrlDisplay() {
        return urlDisplay;
    }

    public void setUrlDisplay(String urlDisplay) {
        this.urlDisplay = urlDisplay;
    }

    public String getUrlExpanded() {
        return urlExpanded;
    }

    public void setUrlExpanded(String urlExpanded) {
        this.urlExpanded = urlExpanded;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceURL() {
        return placeURL;
    }

    public void setPlaceURL(String placeURL) {
        this.placeURL = placeURL;
    }

    public String getPlaceFullName() {
        return placeFullName;
    }

    public void setPlaceFullName(String placeFullName) {
        this.placeFullName = placeFullName;
    }

    public String getPlaceCountry() {
        return placeCountry;
    }

    public void setPlaceCountry(String placeCountry) {
        this.placeCountry = placeCountry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserMentionScreenName() {
        return userMentionScreenName;
    }

    public void setUserMentionScreenName(String userMentionScreenName) {
        this.userMentionScreenName = userMentionScreenName;
    }

    public String getUserMentionName() {
        return userMentionName;
    }

    public void setUserMentionName(String userMentionName) {
        this.userMentionName = userMentionName;
    }

    public Boolean getIsPossiblySensitive() {
        return isPossiblySensitive;
    }

    public void setIsPossiblySensitive(Boolean isPossiblySensitive) {
        this.isPossiblySensitive = isPossiblySensitive;
    }

    public Boolean getIsRetweetedByMe() {
        return isRetweetedByMe;
    }

    public void setIsRetweetedByMe(Boolean isRetweetedByMe) {
        this.isRetweetedByMe = isRetweetedByMe;
    }

    public Boolean getIsRetweet() {
        return isRetweet;
    }

    public void setIsRetweet(Boolean isRetweet) {
        this.isRetweet = isRetweet;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public Boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(Boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public Long getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Long retweetCount) {
        this.retweetCount = retweetCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tweet tweet = (Tweet) o;

        if ( ! Objects.equals(id, tweet.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tweet{" +
            "id=" + id +
            ", tweet='" + tweet + "'" +
            ", url='" + url + "'" +
            ", source='" + source + "'" +
            ", userId='" + userId + "'" +
            ", userLang='" + userLang + "'" +
            ", userName='" + userName + "'" +
            ", userScreenName='" + userScreenName + "'" +
            ", userLocation='" + userLocation + "'" +
            ", mediaId='" + mediaId + "'" +
            ", mediaUrl='" + mediaUrl + "'" +
            ", inReplyToStatusId='" + inReplyToStatusId + "'" +
            ", inReplyToScreenName='" + inReplyToScreenName + "'" +
            ", inReplyToUserId='" + inReplyToUserId + "'" +
            ", urlDisplay='" + urlDisplay + "'" +
            ", urlExpanded='" + urlExpanded + "'" +
            ", keywords='" + keywords + "'" +
            ", placeId='" + placeId + "'" +
            ", placeType='" + placeType + "'" +
            ", placeName='" + placeName + "'" +
            ", placeURL='" + placeURL + "'" +
            ", placeFullName='" + placeFullName + "'" +
            ", placeCountry='" + placeCountry + "'" +
            ", location='" + location + "'" +
            ", userMentionScreenName='" + userMentionScreenName + "'" +
            ", userMentionName='" + userMentionName + "'" +
            ", isPossiblySensitive='" + isPossiblySensitive + "'" +
            ", isRetweetedByMe='" + isRetweetedByMe + "'" +
            ", isRetweet='" + isRetweet + "'" +
            ", isFavorited='" + isFavorited + "'" +
            ", isTruncated='" + isTruncated + "'" +
            ", retweetCount='" + retweetCount + "'" +
            '}';
    }
}
