(function () {
    'use strict';

    var game =
        angular
            .module('baldaApp')
            .controller('GameController',
                ['$http', function ($http) {
                    var self = this;

                    this.width = 500;
                    this.square = 50;

                    this.offset = this.square;
                    this.offsetTextX = this.offset * 0.3;
                    this.offsetTextY = this.offset * 0.7;

                    this.view = function (tile) {
                        return {
                            height: self.square,
                            width: self.square,
                            x: self.offset * tile.column,
                            y: self.offset * tile.row,
                            textX: self.offsetTextX + self.offset * tile.column,
                            textY: self.offsetTextY + self.offset * tile.row,
                            text: tile.letter
                        }
                    };

                    this.loadNewGame = function () {
                        $http.get('/new-game')
                            .then(function (response) {
                                self.tiles = response.data.map(self.view);
                                self.width = self.square * (Math.sqrt(self.tiles.length) + 1);
                            });
                    };


                }]);


})();
