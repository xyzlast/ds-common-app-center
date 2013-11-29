'use strict';

angular.module('publicWebApp')
  .directive('navigation', function () {
    return {
      templateUrl: 'views/res/navigation.html',
      restrict: 'E'
    };
  });
