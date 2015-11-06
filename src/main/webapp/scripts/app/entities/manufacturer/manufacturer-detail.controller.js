'use strict';

angular.module('3rudApp')
    .controller('ManufacturerDetailController', function ($scope, $rootScope, $stateParams, entity, Manufacturer, Product) {
        $scope.manufacturer = entity;
        $scope.load = function (id) {
            Manufacturer.get({id: id}, function(result) {
                $scope.manufacturer = result;
            });
        };
        var unsubscribe = $rootScope.$on('3rudApp:manufacturerUpdate', function(event, result) {
            $scope.manufacturer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
