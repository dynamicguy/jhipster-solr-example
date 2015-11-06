'use strict';

angular.module('3rudApp')
    .controller('TweetController', function ($scope, $state, $modal, Tweet, TweetSearch, ParseLinks) {

        $scope.tweets = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Tweet.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.tweets.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.tweets = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            TweetSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.tweets = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tweet = {
                tweet: null,
                url: null,
                source: null,
                userId: null,
                userLang: null,
                userName: null,
                userScreenName: null,
                userLocation: null,
                mediaId: null,
                mediaUrl: null,
                inReplyToStatusId: null,
                inReplyToScreenName: null,
                inReplyToUserId: null,
                urlDisplay: null,
                urlExpanded: null,
                keywords: null,
                placeId: null,
                placeType: null,
                placeName: null,
                placeURL: null,
                placeFullName: null,
                placeCountry: null,
                location: null,
                userMentionScreenName: null,
                userMentionName: null,
                isPossiblySensitive: null,
                isRetweetedByMe: null,
                isRetweet: null,
                isFavorited: null,
                isTruncated: null,
                retweetCount: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllTweetsSelected = false;

        $scope.updateTweetsSelection = function (tweetArray, selectionValue) {
            for (var i = 0; i < tweetArray.length; i++)
            {
            tweetArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.tweets.length; i++){
                var tweet = $scope.tweets[i];
                if(tweet.isSelected){
                    //Tweet.update(tweet);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.tweets.length; i++){
                var tweet = $scope.tweets[i];
                if(tweet.isSelected){
                    //Tweet.update(tweet);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.tweets.length; i++){
                var tweet = $scope.tweets[i];
                if(tweet.isSelected){
                    Tweet.delete(tweet);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.tweets.length; i++){
                var tweet = $scope.tweets[i];
                if(tweet.isSelected){
                    Tweet.update(tweet);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Tweet.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tweets = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    });
