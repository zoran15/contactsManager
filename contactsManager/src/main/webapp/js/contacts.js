var contactsApp = angular.module('contactsApp', []);

contactsApp.controller('ContactsCtrl', function ($scope, $http) {
    $scope.url = "/api/contacts/";

    $scope.displayValidationError = false;
    $scope.contact = {}

    $scope.getContactList = function () {
        var url = $scope.url;
        $scope.startDialogAjaxRequest();

        var config = {};

        $http.get(url, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, null, false);
            }).error(function () {
                $scope.state = 'error';
            });
    }

    $scope.populateTable = function (data) {
        if (data.contactsCount > 0) {
            $scope.state = 'list';
            $scope.page = {source: data.contacts};
        } else {
            $scope.state = 'noresult';
        }
    }

    $scope.exit = function (modalId) {
        $(modalId).modal('hide');
        $scope.contact = {};
        $scope.displayValidationError = false;
    }

    $scope.finishAjaxCallOnSuccess = function (data, modalId) {
        $scope.populateTable(data);
        $("#loadingModal").modal('hide');

        if (modalId){
            $scope.exit(modalId);
        }
    }

    $scope.startDialogAjaxRequest = function () {
        $scope.displayValidationError = false;
        $("#loadingModal").modal('show');
        $scope.previousState = $scope.state;
    }

    $scope.handleErrorInDialogs = function (status) {
        $("#loadingModal").modal('hide');
        $scope.state = $scope.previousState;
    }

    $scope.resetContact = function(){
        $scope.contact = {};
    };

    $scope.createContact = function (newContactForm) {
        var url = $scope.url;
        
        if ($scope.contact.name == null || $scope.contact.email == null || $scope.contact.phoneNumber == null) {
        	$scope.displayValidationError = true;
        	return;
        }

        var config = {headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}};

        $scope.startDialogAjaxRequest();

        $http.post(url, $.param($scope.contact), config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#addContactsModal", false);
            })
            .error(function(data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

    $scope.selectedContact = function (contact) {
        var selectedContact = angular.copy(contact);
        $scope.contact = selectedContact;
    }

    $scope.deleteContact = function () {
        var url = $scope.url + $scope.contact.id;
        $scope.startDialogAjaxRequest();

        $http({
            method: 'DELETE',
            url: url
        }).success(function (data) {
                $scope.resetContact();
                $scope.finishAjaxCallOnSuccess(data, "#deleteContactsModal", false);
            }).error(function(data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };
    
    $scope.resetContact = function(){
        $scope.contact = {};
    };

    $scope.getContactList();
});