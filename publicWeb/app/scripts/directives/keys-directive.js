'use strict';

angular.module('publicWebApp')
  .directive('keyspage', function () {
    return {
      templateUrl: 'views/keys.html',
      restrict: 'E',
      controller: 'KeysCtrl'
    };
  });
