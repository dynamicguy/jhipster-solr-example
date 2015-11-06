'use strict';

angular.module('3rudApp')
	.controller('CatDeleteController', function($scope, $modalInstance, entity, Cat) {

        $scope.cat = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cat.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });