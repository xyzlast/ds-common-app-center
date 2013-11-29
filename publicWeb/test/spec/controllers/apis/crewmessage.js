'use strict';

describe('Controller: ApisCrewmessageCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var ApisCrewmessageCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ApisCrewmessageCtrl = $controller('ApisCrewmessageCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
