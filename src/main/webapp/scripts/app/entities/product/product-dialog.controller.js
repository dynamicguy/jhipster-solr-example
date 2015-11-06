'use strict';

angular.module('3rudApp').controller('ProductDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'Product', 'User', 'Cat', 'Manufacturer',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, Product, User, Cat, Manufacturer) {

        $scope.product = entity;
        $scope.users = User.query();
        $scope.cats = Cat.query();
        $scope.manufacturers = Manufacturer.query();
        $scope.load = function(id) {
            Product.get({id : id}, function(result) {
                $scope.product = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('3rudApp:productUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.product.id != null) {
                Product.update($scope.product, onSaveSuccess, onSaveError);
            } else {
                Product.save($scope.product, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setImage = function ($file, product) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        product.image = base64Data;
                        product.imageContentType = $file.type;
                    });
                };
            }
        };
}]);
