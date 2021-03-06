'use strict';

angular.module('3rudApp')
    .factory('UsersSearch', function ($resource) {
        return $resource('api/_search/users/:query', {}, {
            'query': {
                method: 'GET',
                isArray: true
            }
        });
    });
