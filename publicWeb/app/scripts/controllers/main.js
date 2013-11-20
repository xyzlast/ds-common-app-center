'use strict';

angular.module('publicWebApp')
  .controller('MainCtrl', function ($scope) {
    $scope.pageIndex = 0;
    $scope.pages = [1];



    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
});
