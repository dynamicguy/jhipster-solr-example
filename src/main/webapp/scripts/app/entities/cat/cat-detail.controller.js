'use strict';

angular.module('3rudApp')
    .controller('CatDetailController', function ($scope, $rootScope, $stateParams, entity, Cat, Product) {
        $scope.cat = entity;
        $scope.load = function (id) {
            Cat.get({id: id}, function(result) {
                $scope.cat = result;
            });
        };
        var unsubscribe = $rootScope.$on('3rudApp:catUpdate', function(event, result) {
            $scope.cat = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
