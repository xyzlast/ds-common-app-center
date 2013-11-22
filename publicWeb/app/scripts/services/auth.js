'use strict';

publicWebApp.factory("authService", function($http){
  return {
    checkLogin: function($scope) {
      var config = {
        withCredentials: true
      }
      $http.get(baseUrl + '/api/login', config)
        .success(function(jsonResult){
          $scope.principal = jsonResult.Data;
      })
    }
  }
});