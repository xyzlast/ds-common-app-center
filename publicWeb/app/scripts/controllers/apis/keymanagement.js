'use strict';

angular.module('publicWebApp')
  .controller('ApisKeymanagementCtrl', function ($scope, navService) {
    navService.changeMenu('apilist');
  });
