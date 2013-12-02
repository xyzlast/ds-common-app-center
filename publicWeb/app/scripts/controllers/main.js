'use strict';

angular.module('publicWebApp')
  .controller('MainCtrl', function ($scope, authService, navService, $http, $q, pageList) {
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
    $scope.pageIndex = 0;
    $scope.pages = [1];
    $scope.notices = [];
    $scope.selectedNotice = null;
    var PAGE_SIZE = 5;

    $scope.showNotice = function(notice) {
      $scope.selectedNotice = notice;
      $('#noticeModal').modal();
    };
    navService.changeMenu("index");
    pageList.init($scope, '/api/notice/list', PAGE_SIZE);
    $scope.loadItems(0);
    authService.checkLogin($scope);
  });
