'use strict';

angular.module('publicWebApp')
  .service('navService', function Navservice() {
    return {
      changeMenu : function(menuName) {
        $('.navbar-collapse .nav li').removeClass('active')
        $('.navbar-collapse .nav li[name="' + menuName +'"]').addClass('active');
      }
    }
  });
