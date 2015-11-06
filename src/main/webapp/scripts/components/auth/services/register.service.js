'use strict';

angular.module('3rudApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


