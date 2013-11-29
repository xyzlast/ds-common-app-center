'use strict';
angular.module('publicWebApp')
  .controller('LoginCtrl', function ($scope, $http, $location, authService) {
    $scope.username = "";
    $scope.password = "";
    $scope.principal = {};

    $scope.login = function() {
      var data = {
        username: $scope.username,
        password: $scope.password
      };
      var config = {
        withCredentials: true
      }
      $http.post(baseUrl + '/api/login', jQuery.param(data), config)
        .success(function(jsonResult){
          if(jsonResult.IsOK) {
            $scope.principal = jsonResult.Data;
          }
        });
    };

    $scope.logout = function() {
      var config = {
        withCredentials: true
      }
      $http.get(baseUrl + '/api/logout', config)
        .success(function(jsonResult) {
          $scope.principal = jsonResult.Data;
          $location.path("/#");
        })
    };
    authService.checkLogin($scope);
  });


