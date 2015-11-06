'use strict';

angular.module('3rudApp')
    .factory('CatSearch', function ($resource) {
        return $resource('api/_search/cats/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
