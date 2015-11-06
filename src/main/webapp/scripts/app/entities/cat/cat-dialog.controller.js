'use strict';

angular.module('3rudApp').controller('CatDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Cat', 'Product',
        function($scope, $stateParams, $modalInstance, entity, Cat, Product) {

        $scope.cat = entity;
        $scope.products = Product.query();
        $scope.load = function(id) {
            Cat.get({id : id}, function(result) {
                $scope.cat = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('3rudApp:catUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cat.id != null) {
                Cat.update($scope.cat, onSaveSuccess, onSaveError);
            } else {
                Cat.save($scope.cat, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
