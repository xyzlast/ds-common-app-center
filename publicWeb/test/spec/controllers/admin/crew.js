'use strict';

describe('Controller: AdminCrewCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var AdminCrewCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AdminCrewCtrl = $controller('AdminCrewCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
