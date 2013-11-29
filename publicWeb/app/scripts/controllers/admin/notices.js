'use strict';

angular.module('publicWebApp')
  .controller('AdminNoticesCtrl', function ($scope, $http, authService, $q) {
    $scope.pageIndex = 0;
    $scope.pages = [1];
    $scope.notices = [];
    $scope.selectedNotice = null;
    var PAGE_SIZE = 10;

    $scope.loadNotices = function(index) {
      if(index < 0) {
        alert('더이상 없습니다.');
        return;
      }
      var config = {
        withCredentials: true,
        params : {
          pageIndex : index,
          pageSize : PAGE_SIZE
        }
      }

      $q.all($http.get(baseUrl + "/api/admin/notice/list", config))
        .then(function(httpResult) {
          if(httpResult.status == 200) {
            var jsonResult = httpResult.data;
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
          } else {
            alert('인증에 문제가 발생했습니다.');
          }
        })
    };

    $scope.showNotice = function(notice) {
      showOrHideNotice("show", notice);
    };

    $scope.hideNotice = function(notice) {
      showOrHideNotice("hide", notice);
    };

    $scope.showEditNoticeModal = function(notice) {
      $scope.selectedNotice = notice;
      $('#myModalLabel').html(notice.title);
      $('#noticeContent').html(notice.content);
      var noticeEditor = new nicEditor({ fullPanel: true }).panelInstance('noticeContent', { hasPanel: true });
      $('#noticeModal').modal();
      //var editor = new nicEditor({ fullPanel: true }).panelInstance('noticeContent', { hasPanel: true });
    };

    var showOrHideNotice = function(path, notice) {
      var config = {
        withCredentials: true
      };
      var params = {
        noticeId : notice.id
      };
      $q.all($http.post(baseUrl + "/api/admin/notice/" + path, jQuery.param(params), config))
        .then(function(httpResult){
          if(httpResult.status == 200 && httpResult.data.IsOK) {
            var updatedNotice = httpResult.data.Data;
            for(var i = 0 ; i < $scope.notices.length ; i++) {
              if($scope.notices[i].id == updatedNotice.id) {
                $scope.notices[i] = updatedNotice;
                break;
              }
            }
          } else {
            console.log(httpResult);
          }
        });
    }
    authService.checkLogin($scope);
    $scope.loadNotices(0);
  });
