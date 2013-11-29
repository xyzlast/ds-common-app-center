'use strict';

angular.module('publicWebApp')
  .directive('apispage', function () {
    return {
      templateUrl: 'views/apis.html',
      restrict: 'E',
      controller: 'ApisCtrl'
    };
  });
