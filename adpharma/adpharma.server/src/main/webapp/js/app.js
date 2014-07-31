'use strict';


// Declare app level module which depends on filters, and services
angular.module('adpharma', [
  'ui.bootstrap',
  'ngRoute',
  'ngResource',
  'ngCookies',
  'ngSanitize',
  'ngAnimate'
]).config(function ($routeProvider, $httpProvider) {
  $httpProvider.defaults.withCredentials = true;
  $httpProvider.responseInterceptors.push('httpInterceptor');
  $routeProvider.when('/salesOderList', {templateUrl: 'views/salesOrderList.html', controller: 'SalesOrderListController'});
  $routeProvider.when('/salesOrderEdit', {templateUrl: 'views/salesOrderEdit.html', controller: 'SalesOrderEditController'});
  $routeProvider.when('/login', {templateUrl: 'views/login.html', controller: 'auth'});
  $routeProvider.otherwise({redirectTo: '/salesOrderEdit'});
});

