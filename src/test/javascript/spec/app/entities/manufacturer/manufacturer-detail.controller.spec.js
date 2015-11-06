'use strict';

describe('Manufacturer Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockManufacturer, MockProduct;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockManufacturer = jasmine.createSpy('MockManufacturer');
        MockProduct = jasmine.createSpy('MockProduct');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Manufacturer': MockManufacturer,
            'Product': MockProduct
        };
        createController = function() {
            $injector.get('$controller')("ManufacturerDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = '3rudApp:manufacturerUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
