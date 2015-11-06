'use strict';

angular.module('3rudApp')
    .controller('ProductDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Product, User, Cat, Manufacturer) {
        $scope.product = entity;
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
            });
        };
        var unsubscribe = $rootScope.$on('3rudApp:productUpdate', function(event, result) {
            $scope.product = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
