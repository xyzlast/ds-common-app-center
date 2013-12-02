'use strict';

describe('Service: Pagelist', function () {

  // load the service's module
  beforeEach(module('publicWebApp'));

  // instantiate service
  var Pagelist;
  beforeEach(inject(function (_Pagelist_) {
    Pagelist = _Pagelist_;
  }));

  it('should do something', function () {
    expect(!!Pagelist).toBe(true);
  });

});
