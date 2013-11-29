'use strict';

describe('Controller: ApisSvnhelperCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var ApisSvnhelperCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ApisSvnhelperCtrl = $controller('ApisSvnhelperCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
