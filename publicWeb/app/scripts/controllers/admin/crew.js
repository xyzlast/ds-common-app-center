'use strict';

angular.module('publicWebApp')
  .controller('AdminCrewCtrl', function ($scope, authService, pageList) {
    authService.checkLogin($scope);
    pageList.init($scope, "/api/admin/crew/list", 10);
    $scope.loadItems(0);
  });
