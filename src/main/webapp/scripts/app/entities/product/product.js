'use strict';

angular.module('3rudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('product', {
                parent: 'entity',
                url: '/products',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.product.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/products.html',
                        controller: 'ProductController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('product');
                        $translatePartialLoader.addPart('status');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('product.detail', {
                parent: 'entity',
                url: '/product/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '3rudApp.product.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/product-detail.html',
                        controller: 'ProductDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('product');
                        $translatePartialLoader.addPart('status');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Product', function($stateParams, Product) {
                        return Product.get({id : $stateParams.id});
                    }]
                }
            })
            .state('product.new', {
                parent: 'product',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
                        controller: 'ProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    url: null,
                                    description: null,
                                    status: null,
                                    inStock: null,
                                    price: null,
                                    keywords: null,
                                    features: null,
                                    popularity: null,
                                    location: null,
                                    manufactureDate: null,
                                    image: null,
                                    imageContentType: null,
                                    weight: null,
                                    sku: null,
                                    includes: null,
                                    incubationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('product');
                    })
                }]
            })
            .state('product.edit', {
                parent: 'product',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
                        controller: 'ProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Product', function(Product) {
                                return Product.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('product.delete', {
                parent: 'product',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/product/product-delete-dialog.html',
                        controller: 'ProductDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Product', function(Product) {
                                return Product.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
