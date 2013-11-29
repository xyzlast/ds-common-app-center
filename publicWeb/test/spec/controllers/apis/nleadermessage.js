'use strict';

describe('Controller: ApisNleadermessageCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var ApisNleadermessageCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ApisNleadermessageCtrl = $controller('ApisNleadermessageCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
