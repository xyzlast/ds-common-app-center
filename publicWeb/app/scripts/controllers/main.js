'use strict';

angular.module('publicWebApp')
  .controller('MainCtrl', function ($scope, authService) {
    $scope.location = 'notices';
    $scope.menus = {
      notices : {
        on: '/images/menu1_on.gif',
        off: '/images/menu1_off.gif'
      },
      apikey: {
        on: '/images/menu2_on.gif',
        off: '/images/menu2_off.gif'
      },
      apilist: {
        on: '/images/menu3_on.gif',
        off: '/images/menu3_off.gif'
      }
    };
    $scope.menuButtons = [];
    $scope.changeMenu = function(menu) {
      $scope.location = menu;
      $scope.menuButtons = new Array();
      if(menu == "notices") {
        $scope.menuButtons.push($scope.menus.notices.on);
        $scope.menuButtons.push($scope.menus.apikey.off);
        $scope.menuButtons.push($scope.menus.apilist.off);
      } else if(menu == "apikey") {
        $scope.menuButtons.push($scope.menus.notices.off);
        $scope.menuButtons.push($scope.menus.apikey.on);
        $scope.menuButtons.push($scope.menus.apilist.off);
      } else if(menu == 'apilist') {
        $scope.menuButtons.push($scope.menus.notices.off);
        $scope.menuButtons.push($scope.menus.apikey.off);
        $scope.menuButtons.push($scope.menus.apilist.on);
      }
      console.log($scope.menuButtons);
    }
    $scope.changeMenu('notices');
    authService.checkLogin($scope);
});
