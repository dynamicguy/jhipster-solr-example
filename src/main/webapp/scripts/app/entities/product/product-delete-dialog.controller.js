'use strict';

angular.module('3rudApp')
	.controller('ProductDeleteController', function($scope, $modalInstance, entity, Product) {

        $scope.product = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Product.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });