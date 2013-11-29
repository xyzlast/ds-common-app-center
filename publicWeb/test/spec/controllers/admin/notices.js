'use strict';

describe('Controller: AdminNoticesCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var AdminNoticesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AdminNoticesCtrl = $controller('AdminNoticesCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
