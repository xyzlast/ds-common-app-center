'use strict';

angular.module('publicWebApp')
  .directive('loginpage', function () {
    return {
      templateUrl: 'views/login.html',
      restrict: 'E',
      controller: 'LoginCtrl'
    };
  });
