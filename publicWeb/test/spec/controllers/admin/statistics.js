'use strict';

describe('Controller: AdminStatisticsCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var AdminStatisticsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AdminStatisticsCtrl = $controller('AdminStatisticsCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
