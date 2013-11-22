'use strict';

describe('Controller: LoginCtrl', function() {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var LoginCtrl, scope, httpBackend, mockAuthService;
  mockAuthService = {
    checkLogin : function(scope) {
      scope.principal = {
        username : null,
        name : null,
        authenticated : false
      };
    }
  };

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller, $http, $httpBackend, authService) {
    httpBackend = $httpBackend;
    $httpBackend.when("post", baseUrl + "/api/login", "username=ykyoon&password=1234").respond(
      {
        IsOK : true,
        Message : 'API call completed',
        Data : {
          username : 'ykyoon',
          name : '윤영권',
          authenticated: true
        }
      }
    )
    scope = {};
    LoginCtrl = $controller('LoginCtrl', {
      $scope: scope,
      $http: $http,
      authService: mockAuthService
    });
  }));

  it('login progress', function() {
    expect(scope.principal.authenticated).toBe(false);
    httpBackend.expectPOST(baseUrl + "/api/login", "username=ykyoon&password=1234",
      {
        "Accept":"application/json, text/plain, */*",
        "Content-Type":"application/json;charset=utf-8"
      })
      .respond(
      {
        IsOK : true,
        Message : 'API call completed',
        Data : {
          username : 'ykyoon',
          name : '윤영권',
          authenticated: true
        }
      }
    );
    scope.username = 'ykyoon';
    scope.password = '1234';
    scope.login();
    httpBackend.flush();

    expect(scope.principal.username).toBe('ykyoon');
    expect(scope.principal.name).toBe('윤영권');
    expect(scope.principal.authenticated).toBe(true);
  })
});
