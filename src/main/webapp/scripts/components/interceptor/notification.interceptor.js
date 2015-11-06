 'use strict';

angular.module('3rudApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-3rudApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-3rudApp-params')});
                }
                return response;
            }
        };
    });
