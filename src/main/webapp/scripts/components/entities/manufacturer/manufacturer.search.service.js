'use strict';

angular.module('3rudApp')
    .factory('ManufacturerSearch', function ($resource) {
        return $resource('api/_search/manufacturers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
