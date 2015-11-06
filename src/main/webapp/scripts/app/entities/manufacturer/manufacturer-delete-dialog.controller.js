'use strict';

angular.module('3rudApp')
	.controller('ManufacturerDeleteController', function($scope, $modalInstance, entity, Manufacturer) {

        $scope.manufacturer = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Manufacturer.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });