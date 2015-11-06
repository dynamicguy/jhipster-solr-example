'use strict';

angular.module('3rudApp').controller('ManufacturerDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Manufacturer', 'Product',
        function($scope, $stateParams, $modalInstance, entity, Manufacturer, Product) {

        $scope.manufacturer = entity;
        $scope.products = Product.query();
        $scope.load = function(id) {
            Manufacturer.get({id : id}, function(result) {
                $scope.manufacturer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('3rudApp:manufacturerUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.manufacturer.id != null) {
                Manufacturer.update($scope.manufacturer, onSaveSuccess, onSaveError);
            } else {
                Manufacturer.save($scope.manufacturer, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
