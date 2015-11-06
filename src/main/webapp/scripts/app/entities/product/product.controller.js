'use strict';

angular.module('3rudApp')
    .controller('ProductController', function ($scope, $state, $modal, DataUtils, Product, ProductSearch, ParseLinks) {

        $scope.products = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Product.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.products = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            ProductSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.products = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.product = {
                name: null,
                url: null,
                description: null,
                status: null,
                inStock: null,
                price: null,
                keywords: null,
                features: null,
                popularity: null,
                location: null,
                manufactureDate: null,
                image: null,
                imageContentType: null,
                weight: null,
                sku: null,
                includes: null,
                incubationDate: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        // bulk operations start
        $scope.areAllProductsSelected = false;

        $scope.updateProductsSelection = function (productArray, selectionValue) {
            for (var i = 0; i < productArray.length; i++)
            {
            productArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.products.length; i++){
                var product = $scope.products[i];
                if(product.isSelected){
                    //Product.update(product);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.products.length; i++){
                var product = $scope.products[i];
                if(product.isSelected){
                    //Product.update(product);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.products.length; i++){
                var product = $scope.products[i];
                if(product.isSelected){
                    Product.delete(product);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.products.length; i++){
                var product = $scope.products[i];
                if(product.isSelected){
                    Product.update(product);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Product.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.products = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    });
