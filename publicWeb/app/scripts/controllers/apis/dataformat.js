'use strict';

angular.module('publicWebApp')
  .controller('ApisDataformatCtrl', function ($scope, navService) {
    navService.changeMenu('apilist');
  });
