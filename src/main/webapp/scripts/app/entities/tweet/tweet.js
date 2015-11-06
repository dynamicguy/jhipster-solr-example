'use strict';

angular.module('3rudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tweet', {
                parent: 'entity',
                url: '/tweets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.tweet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tweet/tweets.html',
                        controller: 'TweetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tweet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tweet.detail', {
                parent: 'entity',
                url: '/tweet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.tweet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tweet/tweet-detail.html',
                        controller: 'TweetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tweet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Tweet', function($stateParams, Tweet) {
                        return Tweet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tweet.new', {
                parent: 'tweet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tweet/tweet-dialog.html',
                        controller: 'TweetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tweet', null, { reload: true });
                    }, function() {
                        $state.go('tweet');
                    })
                }]
            })
            .state('tweet.edit', {
                parent: 'tweet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tweet/tweet-dialog.html',
                        controller: 'TweetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Tweet', function(Tweet) {
                                return Tweet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tweet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tweet.delete', {
                parent: 'tweet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tweet/tweet-delete-dialog.html',
                        controller: 'TweetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Tweet', function(Tweet) {
                                return Tweet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tweet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
