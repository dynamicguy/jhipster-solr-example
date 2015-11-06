'use strict';

angular.module('3rudApp')
    .controller('ManufacturerController', function ($scope, $state, $modal, Manufacturer, ManufacturerSearch, ParseLinks) {

        $scope.manufacturers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Manufacturer.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.manufacturers = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            ManufacturerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.manufacturers = result;
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
            $scope.manufacturer = {
                name: null,
                address: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllManufacturersSelected = false;

        $scope.updateManufacturersSelection = function (manufacturerArray, selectionValue) {
            for (var i = 0; i < manufacturerArray.length; i++)
            {
            manufacturerArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.manufacturers.length; i++){
                var manufacturer = $scope.manufacturers[i];
                if(manufacturer.isSelected){
                    //Manufacturer.update(manufacturer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.manufacturers.length; i++){
                var manufacturer = $scope.manufacturers[i];
                if(manufacturer.isSelected){
                    //Manufacturer.update(manufacturer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.manufacturers.length; i++){
                var manufacturer = $scope.manufacturers[i];
                if(manufacturer.isSelected){
                    Manufacturer.delete(manufacturer);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.manufacturers.length; i++){
                var manufacturer = $scope.manufacturers[i];
                if(manufacturer.isSelected){
                    Manufacturer.update(manufacturer);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Manufacturer.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.manufacturers = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    });
