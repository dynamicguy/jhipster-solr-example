'use strict';

angular.module('3rudApp')
    .factory('Cat', function ($resource, DateUtils) {
        return $resource('api/cats/:id', {}, {
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
