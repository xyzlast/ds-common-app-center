'use strict';

describe('Controller: ApilistCtrl', function() {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var ApilistCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller) {
    scope = {};
    ApilistCtrl = $controller('ApilistCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function() {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
