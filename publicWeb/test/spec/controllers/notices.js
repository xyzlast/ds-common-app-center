'use strict';

describe('Controller: NoticesCtrl', function() {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var NoticesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller) {
    scope = {};
    NoticesCtrl = $controller('NoticesCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function() {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
