'use strict';

angular.module('publicWebApp')
  .directive('pagination', function () {
    return {
      template: '<div style="text-align: center">' +
                  '<ul class="pagination">' +
                    '<li><a href="javascript:;" ng-click="loadItems(pageIndex - 5);">&laquo;</a></li>' +
                    '<li ng-repeat="page in pages" ng-class="getPageClass(page);">' +
                      '<a href="javascript:;" ng-click="loadItems(page-1);">{{page}}</a>' +
                    '</li>' +
                    '<li><a href="javascript:;" ng-click="loadItems(pageIndex + 5);">&raquo;</a></li>' +
                  '</ul>' +
                '</div>',
      restrict: 'E'
    };
  });
