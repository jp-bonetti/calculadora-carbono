const app = angular.module('app',
    ['app.login', 'app.register', 'app.home', 'app.users', 'ngRoute']
);

app.config(($routeProvider) => {
    $routeProvider.when('/login', {
        templateUrl: 'modules/login/login.html',
        controller: 'LoginController'
    }).when('/register', {
        templateUrl: 'modules/register/register.html',
        controller: 'RegisterController'
    }).when('/home', {
        templateUrl: 'modules/home/home.html',
        controller: 'HomeController'
    }).when('/users', {
        templateUrl: 'modules/users/users.html',
        controller: 'UsersController'
    }).otherwise({
        redirectTo: '/home'
    });
})