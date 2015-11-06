'use strict';

angular.module('3rudApp')
    .factory('Tweet', function ($resource, DateUtils) {
        return $resource('api/tweets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
				
        });
    });
