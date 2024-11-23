const app = angular.module("controleGastosApp", [])

app.controller("MainController", function ($scope, $http){
    $http.get("http://localhost:8080/api/usuarios")
    .then((response) => {
        $scope.usuarios = response.data
    })
    .catch((error) => {
        console.error("Erro ao carregar usuários: ", error)
    })
})