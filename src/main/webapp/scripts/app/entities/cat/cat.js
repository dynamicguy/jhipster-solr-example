'use strict';

angular.module('3rudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cat', {
                parent: 'entity',
                url: '/cats',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.cat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cat/cats.html',
                        controller: 'CatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cat');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cat.detail', {
                parent: 'entity',
                url: '/cat/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.cat.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cat/cat-detail.html',
                        controller: 'CatDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cat');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cat', function($stateParams, Cat) {
                        return Cat.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cat.new', {
                parent: 'cat',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cat/cat-dialog.html',
                        controller: 'CatDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    cat: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cat', null, { reload: true });
                    }, function() {
                        $state.go('cat');
                    })
                }]
            })
            .state('cat.edit', {
                parent: 'cat',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cat/cat-dialog.html',
                        controller: 'CatDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cat', function(Cat) {
                                return Cat.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cat', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cat.delete', {
                parent: 'cat',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cat/cat-delete-dialog.html',
                        controller: 'CatDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cat', function(Cat) {
                                return Cat.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cat', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
