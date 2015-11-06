'use strict';

describe('Product Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProduct, MockUser, MockCat, MockManufacturer;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProduct = jasmine.createSpy('MockProduct');
        MockUser = jasmine.createSpy('MockUser');
        MockCat = jasmine.createSpy('MockCat');
        MockManufacturer = jasmine.createSpy('MockManufacturer');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Product': MockProduct,
            'User': MockUser,
            'Cat': MockCat,
            'Manufacturer': MockManufacturer
        };
        createController = function() {
            $injector.get('$controller')("ProductDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = '3rudApp:productUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
