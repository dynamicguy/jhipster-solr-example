'use strict';

angular.module('3rudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manufacturer', {
                parent: 'entity',
                url: '/manufacturers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.manufacturer.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manufacturer/manufacturers.html',
                        controller: 'ManufacturerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manufacturer');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('manufacturer.detail', {
                parent: 'entity',
                url: '/manufacturer/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.manufacturer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manufacturer/manufacturer-detail.html',
                        controller: 'ManufacturerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manufacturer');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Manufacturer', function($stateParams, Manufacturer) {
                        return Manufacturer.get({id : $stateParams.id});
                    }]
                }
            })
            .state('manufacturer.new', {
                parent: 'manufacturer',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/manufacturer/manufacturer-dialog.html',
                        controller: 'ManufacturerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    address: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('manufacturer', null, { reload: true });
                    }, function() {
                        $state.go('manufacturer');
                    })
                }]
            })
            .state('manufacturer.edit', {
                parent: 'manufacturer',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/manufacturer/manufacturer-dialog.html',
                        controller: 'ManufacturerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Manufacturer', function(Manufacturer) {
                                return Manufacturer.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('manufacturer', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('manufacturer.delete', {
                parent: 'manufacturer',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/manufacturer/manufacturer-delete-dialog.html',
                        controller: 'ManufacturerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Manufacturer', function(Manufacturer) {
                                return Manufacturer.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('manufacturer', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
