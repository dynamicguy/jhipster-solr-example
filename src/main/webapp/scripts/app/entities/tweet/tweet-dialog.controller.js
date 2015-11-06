'use strict';

angular.module('3rudApp').controller('TweetDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Tweet',
        function($scope, $stateParams, $modalInstance, entity, Tweet) {

        $scope.tweet = entity;
        $scope.load = function(id) {
            Tweet.get({id : id}, function(result) {
                $scope.tweet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('3rudApp:tweetUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.tweet.id != null) {
                Tweet.update($scope.tweet, onSaveSuccess, onSaveError);
            } else {
                Tweet.save($scope.tweet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
