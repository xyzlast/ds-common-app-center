'use strict';

console.log('messages.js');

publicWebApp.controller('MessagesCtrl', function($scope, $http) {

  $scope.messages = [];
  this.load = function() {
    $http.get('http://localhost:8080/apiWeb/api/public/crew/list?key=123&pageIndex=0&pageSize=10')
      .success(function(jsonResult) {
        if(jsonResult.IsOK == true) {
          $scope.messages = jsonResult.Data;
        }
      })
  }

  $scope.awesomeThings = [
    'HTML5 Boilerplate',
    'AngularJS',
    'Testacular'
  ];

  this.load();
});
