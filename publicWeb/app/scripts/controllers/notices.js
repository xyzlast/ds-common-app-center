'use strict';

publicWebApp.controller('NoticesCtrl', function($scope, $http) {
  $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
  $scope.pageIndex = 0;
  $scope.pages = [1];
  $scope.notices = [];

  var PAGE_SIZE = 5;
  $scope.loadNotices = function(index) {
    if(index < 0) {
      alert('더이상 없습니다.');
      return;
    }
    $http.get(baseUrl + "/api/notice/list", {params : {pageIndex : index, pageSize : PAGE_SIZE}})
      .success(function(jsonResult){
        if(jsonResult.IsOK == true) {
          if(jsonResult.Data.content.length == 0) {
            alert('더이상 없습니다.');
            return;
          }
          $scope.pageIndex = jsonResult.Data.number + 1;
          $scope.notices = jsonResult.Data.content;
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

  $scope.loadNotices(0);
});
