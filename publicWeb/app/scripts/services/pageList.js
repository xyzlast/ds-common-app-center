'use strict';

angular.module('publicWebApp')
  .factory('pageList', function ($http, $q) {
    var targetUrl = '';
    var pageSize = 5;

    return {
      init : function(scope, url, pageSizeIn) {
        targetUrl = url;
        pageSize = pageSizeIn;

        scope.pageIndex = 0;
        scope.pages = [1];
        scope.pageItems = [];

        scope.getPageClass = function(page) {
          if(scope.pageIndex == page) {
            return "active";
          } else {
            return "";
          }
        }

        scope.loadItems = function(index) {
          if(index < 0) {
            alert('더이상 없습니다.');
            return;
          }

          var config = {
            withCredentials: true,
            params : {
              pageIndex : index,
              pageSize : pageSize
            }
          }

          return $q.all($http.get(baseUrl + targetUrl, config))
            .then(function(httpResult) {
              if(httpResult.status != 200) {
                console.log(httpResult);
                return;
              }

              var jsonResult = httpResult.data;
              if(jsonResult.IsOK == true) {
                if(jsonResult.Data.content.length == 0) {
                  alert('더이상 없습니다.');
                  return;
                }
                scope.pageIndex = jsonResult.Data.number + 1;
                scope.pageItems = jsonResult.Data.content;
              } else {
                scope.pageIndex = -1;
              }

              if(scope.pageIndex != -1) {
                scope.pages = new Array();
                var pageDivide = Math.floor(index / pageSize);
                for(var i = 0 ; i < 5 ; i++) {
                  var pageCount = pageDivide * pageSize + i;
                  scope.pages.push(pageCount + 1);
                }
              }
            });
        }
      }
    };
  });
