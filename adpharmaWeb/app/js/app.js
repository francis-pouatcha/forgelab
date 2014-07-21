'use strict';


// Declare app level module which depends on filters, and services
angular.module('adpharma', [
  'ngRoute',
  'ngResource',
  'ngCookies',
  'ngSanitize',
  'ngAnimate',
  'adpharma.filters',
  'adpharma.services',
  'adpharma.directives'
]).config(function ($routeProvider, $httpProvider) {
  $httpProvider.responseInterceptors.push('httpInterceptor');
  $routeProvider.when('/salesOderList', {templateUrl: 'views/salesOrderList.html', controller: 'SalesOrderListController'});
  $routeProvider.when('/salesOrderEdit', {templateUrl: 'views/salesOrderEdit.html', controller: 'SalesOrderEditController'});
  $routeProvider.when('/login', {templateUrl: 'views/login.html', controller: 'auth'});
  $routeProvider.otherwise({redirectTo: '/login'});
}).run(function (api) {
  api.init();
});

