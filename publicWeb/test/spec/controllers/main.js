'use strict';

describe('Controller: MainCtrl', function () {

  // load the controller's module
  beforeEach(module('publicWebApp'));

  var MainCtrl,
    scope, httpBackend;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $http, $httpBackend) {
    scope = $rootScope.$new();
    var notices = '{"IsOK":true,"Message":"Execute api result ok","Data":{"content":[{"id":1,"top":false,"title":"","content":"","createTime":1384827093000,"writer":"201105010","deleted":false},{"id":2,"top":false,"title":"TITLE","content":"CONTENT","createTime":1384827094000,"writer":"201105010","deleted":false},{"id":4,"top":false,"title":"TITLE","content":"CONTENT","createTime":1384827094000,"writer":"201105010","deleted":true},{"id":5,"top":false,"title":"","content":"","createTime":1384828098000,"writer":"201105010","deleted":false},{"id":6,"top":false,"title":"TITLE","content":"CONTENT","createTime":1384828098000,"writer":"201105010","deleted":false},{"id":8,"top":false,"title":"TITLE","content":"CONTENT","createTime":1384828099000,"writer":"201105010","deleted":true},{"id":9,"top":false,"title":"","content":"","createTime":1384829817000,"writer":"201105010","deleted":false},{"id":10,"top":false,"title":"TITLE","content":"CONTENT","createTime":1384829819000,"writer":"201105010","deleted":false},{"id":12,"top":false,"title":"TITLE","content":"CONTENT","createTime":1384829822000,"writer":"201105010","deleted":true},{"id":13,"top":false,"title":"","content":"","createTime":1384830408000,"writer":"201105010","deleted":false}],"size":10,"number":0,"totalPages":5,"numberOfElements":10,"sort":null,"firstPage":true,"lastPage":false,"totalElements":43}}';
    httpBackend = $httpBackend;
    httpBackend.when('GET', baseUrl + "/api/notice/list?pageIndex=0&pageSize=10").respond(angular.fromJson(notices));
    httpBackend.when('GET', baseUrl + "/api/notice/list?pageIndex=1&pageSize=10").respond(angular.fromJson(notices));
    MainCtrl = $controller('MainCtrl', {
      $scope: scope,
      $http: $http
    });
    httpBackend.flush();
  }));

  afterEach(function() {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });

  it('page load 1', function() {
    MainCtrl.loadNotices(1);
    expect(scope.pageIndex).toEqual(1);
    httpBackend.flush();
  });

  it('click title', function() {
    MainCtrl.loadNotices(0);
    var notice = scope.notices[0];
    expect(notice.show).toEqual(null);
    scope.toggleContent(notice);
    expect(notice.show).toEqual(true);
    httpBackend.flush();
  });
});
