'use strict';

angular.module('publicWebApp')
  .controller('ApisAddressCtrl', function ($scope, navService) {
    navService.changeMenu('apilist');
  });
