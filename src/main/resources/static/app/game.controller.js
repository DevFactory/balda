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
                    this.move = {
                        selectedTiles: [],
                        word: '',
                        lastTile: {},
                        emptyUsed: false
                    };


                    this.offset = this.square;
                    this.offsetTextX = this.offset * 0.3;
                    this.offsetTextY = this.offset * 0.7;

                    this.view = function (tile) {
                        return {
                            original: tile,
                            height: self.square,
                            width: self.square,
                            x: self.offset * tile.column,
                            y: self.offset * tile.row,
                            textX: self.offsetTextX + self.offset * tile.column,
                            textY: self.offsetTextY + self.offset * tile.row,
                            text: tile.letter,
                            selected: false
                        }
                    };

                    this.loadNewGame = function () {
                        $http.get('/new-game')
                            .then(function (response) {
                                self.move = {
                                    selectedTiles: [],
                                    word: ''
                                };
                                self.tiles = response.data.tiles.map(self.view);
                                self.width = self.square * (Math.sqrt(self.tiles.length) + 1);
                                self.description = response.data.description;
                            });
                    };

                    this.tileClicked = function (tile) {
                        console.log(tile.x, tile.y, tile.letter);
                        var move = self.move;
                        if (tile === self.move.lastTile) {
                            console.log("Attempt to remove: " + tile.text);
                            tile.selected = false;
                            move.selectedTiles.pop(); // remove from stack
                            if (move.selectedTiles.length > 0) {
                                move.lastTile = move.selectedTiles[move.selectedTiles.length - 1];
                            }
                            if (move.selectedTiles.length === 0) {
                                move.lastTile = null;
                                move.emptyUsed = false;
                                console.log("Cleaned full word");
                            }

                            move.word = move.selectedTiles.reduce(self.wordGlue, '');
                            return;
                        }

                        if (self.isValidNextTile(tile)) {
                            move.lastTile = tile;
                            tile.selected = true;
                            if (move.emptyUsed) {
                                tile.text = '#';
                                move.emptyUsed = true;
                            }
                            move.selectedTiles.push(tile);

                            move.word = move.selectedTiles.reduce(self.wordGlue, '');
                        }
                    };

                    this.wordGlue = function (total, nextTile) {
                        return total + nextTile.text;
                    };

                    this.isValidNextTile = function (tile) {

                        return (!self.move.emptyUsed || tile.text !== '') && self.move.selectedTiles.indexOf(tile) < 0
                    };


                }]);


})();
