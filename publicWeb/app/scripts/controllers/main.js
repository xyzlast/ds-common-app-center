'use strict';

angular.module('publicWebApp')
  .controller('MainCtrl', function ($scope, $http, $cookies) {
    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
    $scope.pageIndex = 0;
    $scope.pages = [1];
    $scope.notices = [];
    $scope.username = "";
    $scope.password = "";

    $scope.jsessionId = "";

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
      console.log('toggleContent');
      if(notice.show == null || notice.show == false) {
        notice.show = true;
      } else {
        notice.show = false;
      }
    }

    $scope.authorized = false;
    $scope.login = function() {
      var data = {
        username: $scope.username,
        password: $scope.password
      };
      var config = {
        withCredentials: true
      }
      $http.post(baseUrl + '/api/login', jQuery.param(data), config)
        .success(function(jsonResult){
          console.log(jsonResult);
        });
    }

    $scope.checkAuth = function() {
      var config = {
        withCredentials: true
      }
      $http.get(baseUrl + '/api/login', config)
        .success(function(jsonResult){
        console.log(jsonResult);
      });
    }

    $scope.getApiKeyList = function() {
      var config = {
        withCredentials: true
      }
      $http.get(baseUrl + "/api/apiKey/list?pageIndex=0&pageSize=10", config)
        .success(function(jsonResult) {
          console.log(jsonResult);
      });
    }
    $scope.loadNotices(0);
    $scope.checkAuth();
});
