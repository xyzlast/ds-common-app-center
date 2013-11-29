'use strict';

angular.module('publicWebApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ngRoute'
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
      .when('/notices', {
        templateUrl: 'views/notices.html',
        controller: 'NoticesCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .when('/apis', {
        templateUrl: 'views/apis.html',
        controller: 'ApisCtrl'
      })
      .when('/apis/home', {
        templateUrl: 'views/apis/home.html',
        controller: 'ApisHomeCtrl'
      })
      .when('/apis/address', {
        templateUrl: 'views/apis/address.html',
        controller: 'ApisAddressCtrl'
      })
      .when('/keys', {
        templateUrl: 'views/keys.html',
        controller: 'KeysCtrl'
      })
      .when('/apis/keymanagement', {
        templateUrl: 'views/apis/keymanagement.html',
        controller: 'ApisKeymanagementCtrl'
      })
      .when('/apis/svnhelper', {
        templateUrl: 'views/apis/svnhelper.html',
        controller: 'ApisSvnhelperCtrl'
      })
      .when('/apis/crewmessage', {
        templateUrl: 'views/apis/crewmessage.html',
        controller: 'ApisCrewmessageCtrl'
      })
      .when('/apis/nleadermessage', {
        templateUrl: 'views/apis/nleadermessage.html',
        controller: 'ApisNleadermessageCtrl'
      })
      .when('/admin', {
        templateUrl: 'views/admin.html',
        controller: 'AdminCtrl'
      })
      .when('/admin/home', {
        templateUrl: 'views/admin/home.html',
        controller: 'AdminHomeCtrl'
      })
      .when('/admin/notices', {
        templateUrl: 'views/admin/notices.html',
        controller: 'AdminNoticesCtrl'
      })
      .when('/admin/statistics', {
        templateUrl: 'views/admin/statistics.html',
        controller: 'AdminStatisticsCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

var baseUrl = "http://localhost:8080/apiWeb"