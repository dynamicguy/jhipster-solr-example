'use strict';

angular.module('3rudApp')
    .controller('TweetDetailController', function ($scope, $rootScope, $stateParams, entity, Tweet) {
        $scope.tweet = entity;
        $scope.load = function (id) {
            Tweet.get({id: id}, function(result) {
                $scope.tweet = result;
            });
        };
        var unsubscribe = $rootScope.$on('3rudApp:tweetUpdate', function(event, result) {
            $scope.tweet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
