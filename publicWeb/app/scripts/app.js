'use strict';

var publicWebApp = angular.module('publicWebApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize'
])
  .config(function ($routeProvider, $httpProvider) {
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;

    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/messages', {
        templateUrl: 'views/messages.html',
        controller: 'MessagesCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .when('/notices', {
        templateUrl: 'views/notices.html',
        controller: 'NoticesCtrl'
      })
      .when('/apilist', {
        templateUrl: 'views/apilist.html',
        controller: 'ApilistCtrl'
      })
      .when('/apikey', {
        templateUrl: 'views/apikey.html',
        controller: 'ApikeyCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
});

publicWebApp.directive('login', function(){
  return {
    restrict: 'E',
    templateUrl: 'views/login.html',
    controller: 'LoginCtrl'
  };
});

publicWebApp.directive('notices', function(){
  return {
    restrict: 'E',
    templateUrl: 'views/notices.html',
    controller: 'NoticesCtrl'
  };
});

publicWebApp.directive('apikey', function() {
  return {
    restrict: 'E',
    templateUrl: 'views/apikey.html',
    controller: 'ApikeyCtrl'
  };
});

publicWebApp.directive('apilist', function() {
  return {
    restrict: 'E',
    templateUrl: 'views/apilist.html',
    controller: 'ApilistCtrl'
  };
});
var baseUrl = "http://localhost:8080/apiWeb"
// $.support.cors = true;
