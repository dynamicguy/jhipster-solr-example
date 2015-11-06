'use strict';

angular.module('3rudApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/api/account") == 0 )){
	                $rootScope.$emit('3rudApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });