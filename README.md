# JetpackChess
Chess Board Library for Jetpack Compose \
[still in early developpement]


The initial goal of JetpackChess is to serve as an interactive board to train with puzzle or practice your opening lines.

## v1.0

### Puzzle Mode :
- import base position + list of moves in UCI format. Exemple : 1.e4 c6 = 'e2e4 c7c6'
- must find the next move imported to advance
- auto-play the opponent move until the end of the variation

#### Can be use for puzzle or opening line memory test

### Scroll Mode :
- import base position + list of moves in UCI format. Exemple : 1.e4 c6 = 'e2e4 c7c6'
- can go forward and backward to see the variation from the beginning to the end

#### Can be use to see and study a specific opening line


## Controller Documentation 

### PuzzleController

2 way to import a puzzle/opening

newPuzzle(): 
- fen: String 
  - can import the initial position with const FEN_DEFAULT_POSITION or a full fen. 
  - example : "2r3k1/1q3ppp/B3pbb1/2Rp4/P7/1Q2P2P/1P4P1/6K1 b - - 2 27")
- uciMoves: String 
  - import all the moves in UCI Format. 
  - example for 1.e4 c6 => "e2e4 c7c6"
- playerSide: PlayerColor
  - which side yoy ant to play : PlayerColor.WHITE or PlayerColor.BLACK
- firstDisplayedMove: Int 
  - 0 if start at the initial position
  - 1 for starting after the first move - if testing opening for black pieces after the first white move for example

newPuzzleLichess() :
- need only fen and uciMoves
- will autodetect Side and firstDisplayedMove
- build to be used with the puzzles from the Lichess DB : https://database.lichess.org/#puzzles



### Scroll

newVariation()
- fen: String,
- uciMoves: String,
- playerSide: PlayerColor
- will start at the initial position (firstDisplayedMove = 0)





## Not yet implemented :
- Move Validation // Check or Checkmate 
- Animation when a piece move
