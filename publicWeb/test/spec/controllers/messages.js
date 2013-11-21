'use strict';

describe('Controller: MessagesCtrl', function() {
  // load the controller's module
  beforeEach(module('publicWebApp'));
  var MessagesCtrl, scope, httpBackend;
  // Initialize the controller and a mock scope
  beforeEach(
    inject(function($controller, $httpBackend, $http) {
      scope = {};
      $httpBackend.when('GET', 'http://localhost:8080/apiWeb/api/public/crew/list?key=123&pageIndex=0&pageSize=10').respond(
        {
          IsOK : true,
          message : 'Api call ok',
          Data : [
            {
              id : 1,
              message : 'message01'
            },
            {
              id : 2,
              message : 'message02'
            }
          ]
        }
      );
      httpBackend = $httpBackend;
      MessagesCtrl = $controller('MessagesCtrl', {
          $scope: scope,
          $http: $http
      });
      httpBackend.flush();
  }));

  afterEach(function() {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  it('should attach a list of awesomeThings to the scope', function() {
    expect(scope.awesomeThings.length).toBe(3);
  });

  it("loads entries with http", function(){
    httpBackend.expectGET('http://localhost:8080/apiWeb/api/public/crew/list?key=123&pageIndex=0&pageSize=10');
    MessagesCtrl.load(function(){
      expect(scope.messages.length).toBe(2);
    });
    httpBackend.flush();
  });
});
