'use strict';

describe('Controller: ApisCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var ApisCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ApisCtrl = $controller('ApisCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
