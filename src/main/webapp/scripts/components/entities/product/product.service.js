'use strict';

angular.module('3rudApp')
    .factory('Product', function ($resource, DateUtils) {
        return $resource('api/products/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.manufactureDate = DateUtils.convertDateTimeFromServer(data.manufactureDate);
                    data.incubationDate = DateUtils.convertDateTimeFromServer(data.incubationDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
				
        });
    });
