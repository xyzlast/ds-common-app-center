'use strict';

angular.module('publicWebApp')
  .controller('MainCtrl', function ($scope, authService, navService, $http) {
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
    $scope.pageIndex = 0;
    $scope.pages = [1];
    $scope.notices = [];
    $scope.selectedNotice = null;
    var PAGE_SIZE = 5;

    $scope.loadNotices = function(index) {
      if(index < 0) {
        alert('더이상 없습니다.');
        return;
      }
      $http.get(baseUrl + "/api/notice/list", {params : {pageIndex : index, pageSize : PAGE_SIZE}})
        .success(function(jsonResult){
          console.log(jsonResult);
          if(jsonResult.IsOK == true) {
            if(jsonResult.Data.content.length == 0) {
              alert('더이상 없습니다.');
              return;
            }
            $scope.pageIndex = jsonResult.Data.number + 1;
            $scope.notices = jsonResult.Data.content;
            console.log($scope.notices);
          } else {
            $scope.pageIndex = -1;
          }

          if($scope.pageIndex != -1) {
            $scope.pages = new Array();
            var pageDivide = Math.floor(index / PAGE_SIZE);
            for(var i = 0 ; i < 5 ; i++) {
              var pageCount = pageDivide * PAGE_SIZE + i;
              $scope.pages.push(pageCount + 1);
            }
          }
        });
    }

    $scope.toggleContent = function(notice) {
      if(notice.show == null || notice.show == false) {
        notice.show = true;
      } else {
        notice.show = false;
      }
    }

    $scope.showNotice = function(notice) {
      $scope.selectedNotice = notice;
      $('#noticeModal').modal();
    };

    $scope.getPageClass = function(page) {
      if($scope.pageIndex == page) {
        return "active";
      } else {
        return "";
      }
    }
    navService.changeMenu("index");
    $scope.loadNotices(0);
    var result = authService.checkLogin($scope);
  });
