var kalahaSound = new Audio("sounds/kalaha.ogg");
const allPits = document.querySelectorAll('.pit');
var gameState;

var gameId;
var gameStatus;
var pitsData;
var player1Points;
var player2Points;
var turnNumber;
var kalahaChanged;

move = {
	gameId: 0,
	playerNumber: 0,
	selectedPit: 0
}

let loadGameId = getQuerystring("id");
if (loadGameId) {
	loadGame(loadGameId).then(response => {
		// $window.sessionStorage.accessToken = response.body.access_token;
		gameState = JSON.parse(response);
		loadGameVariables(gameState);
	});
} else {
	newGame().then(response => {
		// $window.sessionStorage.accessToken = response.body.access_token;
		gameState = JSON.parse(response);
		loadGameVariables(gameState);
	});
}


function loadGameVariables(gameState) {
	gameId = gameState.id;
	gameStatus = gameState.gameStatus;
	pitsData = gameState.pits;
	player1Points = gameState.playerLeftPoints;
	player2Points = gameState.playerRightPoints;
	turnNumber = gameState.turnNumber;

	pits = Array.prototype.slice.call(allPits);

	pits.sort((a, b) => {
		return parseInt(a.getAttribute('data-index')) < parseInt(b.getAttribute('data-index')) ? -1 : 1;
	});

	pitsData.forEach((pitData, index) => {
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
		case "PlayerLeftTurn": return "Player 1 Turn"; break;
		case "PlayerRightTurn": return "Player 2 Turn"; break;
		case "PlayerLeftWins": return "Game Over! Player 1 Wins!"; break;
		case "PlayerRightWins": return "Game Over! Player 2 Wins!"; break;
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

	console.log(gameState.gameStatus);

	if (gameState.gameStatus == "PlayerLeftTurn") {
		pitsEnabled = document.querySelectorAll(".pit-p1");
		kalahaP1.classList.add("kalaha-enabled");
	}
	if (gameState.gameStatus == "PlayerRightTurn") {
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
	pit.addEventListener("click", function (e) {
		let kalahaP1ValueBefore = document.querySelector(".kalaha-p1").innerText;
		let kalahaP2ValueBefore = document.querySelector(".kalaha-p2").innerText;

		if (!this.classList.contains("pit-enabled")) {
			return;
		}

		if (parseInt(this.innerText) <= 0) {
			alert('Select a pit with stones!');
		}

		let index = this.getAttribute("data-index");

		token = null;
		move.selectedPit = index;
		move.gameId = gameId;

		makeMove(token, move.gameId, move.selectedPit).then(response => {
			gameState = JSON.parse(response);
			updateGame(gameState);
			let kalahaP1ValueAfter = document.querySelector(".kalaha-p1").innerText;
			let kalahaP2ValueAfter = document.querySelector(".kalaha-p2").innerText;
			if (kalahaP1ValueBefore != kalahaP1ValueAfter ||
				kalahaP2ValueBefore != kalahaP2ValueAfter) {
				playKalahaStoneSound();
			}
		});
	});
});

function playKalahaStoneSound() {
	kalahaSound.play();
}

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

function getNumberFromPlayer(player) {
	return player.replace( /^\D+/g, '');
}