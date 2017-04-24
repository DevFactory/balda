(function () {
    'use strict';

    angular.module('baldaApp').controller('CharSelectorController', function ($uibModalInstance, chars) {
        var game = this;
        game.chars = chars;
        game.selected = {
            char: game.chars[0]
        };

        game.ok = function (char) {
            $uibModalInstance.close(char);
        };

        game.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });

})();
