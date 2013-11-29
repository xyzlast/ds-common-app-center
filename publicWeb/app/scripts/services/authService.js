'use strict';

angular.module('publicWebApp')
  .service('authService', function Authservice($http, $location, $q) {
    return {
      checkLogin: function($scope) {
        var config = {
          withCredentials: true
        }
        return $q.all($http.get(baseUrl + '/api/login', config))
          .then(function(result) {
            $scope.principal = result.data.Data;
            if(!$scope.principal.authenticated) {
              if($location.path() != "/") {
                $location.path("/");
              }
            }
          });
      }
    }
  });
