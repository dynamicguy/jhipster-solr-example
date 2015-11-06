'use strict';

angular.module('3rudApp')
    .controller('CatController', function ($scope, $state, $modal, Cat, CatSearch, ParseLinks) {

        $scope.cats = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Cat.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cats = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            CatSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cats = result;
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
            $scope.cat = {
                cat: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllCatsSelected = false;

        $scope.updateCatsSelection = function (catArray, selectionValue) {
            for (var i = 0; i < catArray.length; i++)
            {
            catArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.cats.length; i++){
                var cat = $scope.cats[i];
                if(cat.isSelected){
                    //Cat.update(cat);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.cats.length; i++){
                var cat = $scope.cats[i];
                if(cat.isSelected){
                    //Cat.update(cat);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.cats.length; i++){
                var cat = $scope.cats[i];
                if(cat.isSelected){
                    Cat.delete(cat);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.cats.length; i++){
                var cat = $scope.cats[i];
                if(cat.isSelected){
                    Cat.update(cat);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Cat.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cats = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    });
