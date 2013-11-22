'use strict';

describe('Controller: ApikeyCtrl', function() {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var ApikeyCtrl, scope, httpBackend;

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller, $http, $httpBackend) {
    scope = {};
    ApikeyCtrl = $controller('ApikeyCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function() {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
