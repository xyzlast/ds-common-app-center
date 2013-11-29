'use strict';

describe('Controller: ApisHomeCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var ApisHomeCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ApisHomeCtrl = $controller('ApisHomeCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
