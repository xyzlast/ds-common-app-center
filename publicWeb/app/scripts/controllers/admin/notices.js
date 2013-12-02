'use strict';

angular.module('publicWebApp')
  .controller('AdminNoticesCtrl', function ($scope, $http, authService, $q, pageList) {
    $scope.pageIndex = 0;
    $scope.pages = [1];
    $scope.selectedNotice = null;
    $scope.modalTitle = '';
    $scope.modalTop = false;

    var PAGE_SIZE = 10;

    $scope.showWriteModal = function() {
      $scope.selectedNotice = null;
      $scope.modalTitle = '';
      $scope.modalTop = false;
      var nicEditor = nicEditors.findEditor('noticeContent');
      nicEditor.setContent('');
      $('#noticeModal').modal();
    }

    $scope.writeNotice = function() {

    }

    $scope.showNotice = function(notice) {
      showOrHideNotice("show", notice);
    };

    $scope.hideNotice = function(notice) {
      showOrHideNotice("hide", notice);
    };

    $scope.showEditNoticeModal = function(notice) {
      $scope.selectedNotice = notice;
      $scope.modalTitle = notice.title;
      $scope.modalTop = notice.top;

      var nicEditor = nicEditors.findEditor('noticeContent');
      nicEditor.setContent(notice.content);
      $('#noticeModal').modal();
    };

    $scope.showWriteNoticeModal = function() {
      $scope.selectedNotice = null;
      $scope.modalTitle = '';
      $scope.modalTop = false;
      var nicEditor = nicEditors.findEditor('noticeContent');
      nicEditor.setContent('');

      $('#noticeModal').modal();
    };

    $scope.writeOrEdit = function() {
      var url = "/api/admin/notice/edit";
      console.log('step01');
      var nicEditor = nicEditors.findEditor('noticeContent');
      console.log(nicEditor);
      var config = {
        withCredentials: true
      };
      var params = {};
      params.title = $scope.modalTitle;
      params.top = $scope.modalTop;
      params.content = nicEditor.getContent();

      if($scope.selectedNotice == null) {
        url = "/api/admin/notice/write";
        $scope.pageIndex = 1;
      } else {
        url = "/api/admin/notice/edit";
        params.noticeId = $scope.selectedNotice.id;
      }

      $q.all($http.post(baseUrl + url, jQuery.param(params), config))
        .then(function(httpResult){
          if(httpResult.status == 200) {
            $scope.loadItems($scope.pageIndex - 1);
            $('#noticeModal').modal('hide');
          }
        });
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
            for(var i = 0 ; i < $scope.pageItems.length ; i++) {
              if($scope.pageItems[i].id == updatedNotice.id) {
                $scope.pageItems[i] = updatedNotice;
                break;
              }
            }
          } else {
            console.log(httpResult);
          }
        });
    }

    authService.checkLogin($scope);
    pageList.init($scope, '/api/admin/notice/list', PAGE_SIZE);
    $scope.loadItems(0);

    new nicEditor({ fullPanel: true }).panelInstance('noticeContent', { hasPanel: true });
  });
