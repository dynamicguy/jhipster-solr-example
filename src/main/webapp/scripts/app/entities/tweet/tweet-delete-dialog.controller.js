'use strict';

angular.module('3rudApp')
	.controller('TweetDeleteController', function($scope, $modalInstance, entity, Tweet) {

        $scope.tweet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Tweet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });