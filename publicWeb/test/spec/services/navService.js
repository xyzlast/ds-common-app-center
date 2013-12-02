'use strict';

describe('Service: Navservice', function () {

  // load the service's module
  beforeEach(module('publicWebApp'));

  // instantiate service
  var Navservice;
  beforeEach(inject(function (_Navservice_) {
    Navservice = _Navservice_;
  }));

  it('should do something', function () {
    expect(!!Navservice).toBe(true);
  });

});
