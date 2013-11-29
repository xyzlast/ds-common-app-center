'use strict';

angular.module('publicWebApp')
  .controller('KeysCtrl', function ($scope, $http, authService, $q) {
    $scope.apiKeys = [];
    $scope.currentApiKey = null;
    $scope.programName = "";
    $scope.programDescription = "";

    $scope.load = function() {
      var config = {
        withCredentials: true
      }
      $http.get(baseUrl + "/api/apiKey/list?pageIndex=0&pageSize=10", config)
        .success(function(jsonResult){
          if(jsonResult.IsOK) {
            $scope.apiKeys = jsonResult.Data;
            if($scope.apiKeys.length != 0) {
              $scope.currentApiKey = $scope.apiKeys[0];
            }
          }
        });
    }
    $scope.generateApiKey = function() {
      var confirmReturn = confirm('새로운 API 키를 발급하시겠습니까?');
      if(!confirmReturn) return;
      var config = {
        withCredentials: true
      }
      $http.post(baseUrl + "/api/apiKey/generate", {}, config)
        .success(function(jsonResult){
          if(jsonResult.IsOK) {
            $scope.apiKeys.push(jsonResult.Data);
            alert('KEY가 새로 발급되었습니다.' + jsonResult.Data.id);
          } else {
            alert(jsonResult.Message);
          }
        });
    };
    $scope.deleteApiKey = function() {
      var confirmReturn = confirm('API 키를 삭제하시겠습니까?');
      if(!confirmReturn) return;
      var config = {
        withCredentials: true
      }
      $http.post(baseUrl + "/api/apiKey/delete", jQuery.param({apiKeyId:$scope.currentApiKey.id}), config)
        .success(function(jsonResult){
          if(jsonResult.IsOK) {
            var tempKeys = new Array();
            for(var i = 0 ; i < $scope.apiKeys.length ;i++) {
              if($scope.apiKeys[i].id != $scope.currentApiKey.id) {
                tempKeys.push($scope.apiKeys[i]);
              }
            }
            $scope.apiKeys = tempKeys;
            if($scope.apiKeys.length > 0) {
              $scope.currentApiKey = $scope.currentApiKey[0];
            } else {
              $scope.currentApiKey = null;
            }
          }
        });
    };
    $scope.addProgram = function() {
      var data = {
        apiKeyId : $scope.currentApiKey.id,
        programName : $scope.programName,
        programDescription : $scope.programDescription
      };
      var config = {
        withCredentials: true
      }
      $http.post(baseUrl + "/api/apiKey/program/add", jQuery.param(data), config)
        .success(function(jsonResult){
          if(jsonResult.IsOK) {
            $scope.currentApiKey.programs.push(jsonResult.Data);
            $scope.programName = "";
            $scope.programDescription = "";
          } else {
            alert(jsonResult.Message);
          }
        });
    };

    $scope.deleteProgram = function(program) {
      var data = {
        programId: program.id
      };
      var config = {
        withCredentials: true
      }

      $q.all($http.post(baseUrl + "/api/apiKey/program/delete", jQuery.param(data), config))
        .then(function(httpResult) {
          console.log(httpResult);
          if(httpResult.status != 200) {
            alert('error');
          }
          var programs = new Array();
          for(var i = 0 ; i < $scope.currentApiKey.programs.length ; i++) {
            if($scope.currentApiKey.programs[i].id != program.id) {
              programs.push($scope.currentApiKey.programs[i]);
            }
          }
          $scope.currentApiKey.programs = programs;
        });
    }

    var httpResult = authService.checkLogin($scope);
    httpResult.then(function() {
      $scope.load();
    })
  });
