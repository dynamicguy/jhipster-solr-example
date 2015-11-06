'use strict';

angular.module('3rudApp')
    .factory('TweetSearch', function ($resource) {
        return $resource('api/_search/tweets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
