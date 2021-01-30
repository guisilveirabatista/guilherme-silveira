const allPits = document.querySelectorAll('.pit');
var gameState;

var gameId;
var gameStatus;
var pitsData;
var player1Points;
var player2Points;
var turnNumber;

move = {
	gameId: 0,
	playerNumber: 0,
	selectedPit: 0
}

let loadGameId = getQuerystring("id");
if (loadGameId) {
	loadGame(loadGameId).then(response => {
		// $window.sessionStorage.accessToken = response.body.access_token;
		// console.log(response);
		gameState = JSON.parse(response);
		// console.log(gameState);

		loadGameVariables(gameState);
	});
} else {
	newGame().then(response => {
		// $window.sessionStorage.accessToken = response.body.access_token;
		// console.log(response);
		gameState = JSON.parse(response);
		// console.log(gameState);

		loadGameVariables(gameState);
	});
}


function loadGameVariables(gameState) {
	//TODO RENAME ID TO GAMEID ON THE BACKEND
	gameId = gameState.id;
	gameStatus = gameState.gameStatus;
	pitsData = gameState.pitsState;
	player1Points = gameState.player1Points;
	player2Points = gameState.player2Points;
	turnNumber = gameState.turnNumber;

	pits = Array.prototype.slice.call(allPits);

	pits.sort((a, b) => {
		return parseInt(a.getAttribute('data-index')) < parseInt(b.getAttribute('data-index')) ? -1 : 1;
	});

	pitsData.forEach((pitData, index) => {
		// pits[index].innerHTML = `<span class="stones">${pitData.stones}</span>`;
		pits[index].innerHTML = pitData.stones;
	})

	let player1PointsDiv = document.querySelector('.player1-points');
	let player2PointsDiv = document.querySelector('.player2-points');
	player1PointsDiv.innerHTML = `Player 1 Points: ${player1Points}`;
	player2PointsDiv.innerHTML = `Player 2 Points: ${player2Points}`;
	let gameStatusDiv = document.querySelector('.game-status');
	let turnNumberDiv = document.querySelector('.turn-number');
	gameStatusDiv.innerHTML = getGameStatus();
	turnNumberDiv.innerHTML = `Turn Number: ${turnNumber}`;

	enablePits(gameState);
}

function getGameStatus() {
	switch (gameStatus) {
		case "Player1Turn": return "Player 1 Turn"; break;
		case "Player2Turn": return "Player 2 Turn"; break;
		case "Player1Wins": return "Game Over! Player 1 Wins!"; break;
		case "Player2Wins": return "Game Over! Player 2 Wins!"; break;
		case "Draw": return "Game Over! It's a Draw!"; break;
	}
}

function updateGame(gameState) {
	loadGameVariables(gameState);
}

function enablePits(gameState) {

	pitsEnabled = null;
	kalahas = document.querySelectorAll(".kalaha");
	kalahaP1 = document.querySelector(".kalaha-p1");
	kalahaP2 = document.querySelector(".kalaha-p2");

	kalahas.forEach(k => {
		k.classList.remove("kalaha-enabled");
	})
	allPits.forEach(p => {
		p.classList.remove("pit-enabled");
	});

	if (gameState.gameStatus == "Player1Turn") {
		pitsEnabled = document.querySelectorAll(".pit-p1");
		kalahaP1.classList.add("kalaha-enabled");
	}
	if (gameState.gameStatus == "Player2Turn") {
		pitsEnabled = document.querySelectorAll(".pit-p2");
		kalahaP2.classList.add("kalaha-enabled");
	}

	if (pitsEnabled) {
		pitsEnabled.forEach(p => {
			p.classList.add("pit-enabled");
		});
	}

}

allPits.forEach(pit => {

	pit.addEventListener("click", function(e) {

		if (!this.classList.contains("pit-enabled")) {
			return;
		}

		let index = this.getAttribute("data-index");

		token = null;
		move.selectedPit = index;
		move.gameId = gameId;

		makeMove(token, move.gameId, move.selectedPit).then(response => {
			gameState = JSON.parse(response);
			updateGame(gameState);
		});

		// makeMove(token, move.gameId, move.selectedPit).then(response => {
		//     gameState = JSON.parse(response);
		//     updateGame(gameState);
		// });
	});
});

function getQuerystring(key) {
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		if (pair[0] == key) {
			return pair[1];
		}
	}
}


//TODO AJUSTAR BIND THIS

// move = (e) => {
//     let index = this.getAttribute("data-index");
//     let stones = this.innerHTML;
//     console.log("index: " + index);
//     console.log("stones: " + stones);

//     token = null;
//     move.selectedPit = index;
//     move.gameId = gameId;

//     makeMove(token, move.gameId, move.selectedPit)
// }

// pitsEnabled.forEach(pitEnabled => {
//     pitEnabled.addEventListener("click", move.bind(this));
// });

