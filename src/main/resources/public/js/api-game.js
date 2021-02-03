const newGame = (userToken) => {
    return fetch('/kalaha', {
        method: 'post',
        // withCredentials: true,
        // credentials: 'include',
        headers: {
            'Authorization': `Bearer ${userToken}`,
            'Content-Type': 'application/json'
        },
    }).then(response => {
        return response.text();
    });
}

const loadGame = (loadGameId) => {
	let userToken = null;
    return fetch(`/kalaha?id=${loadGameId}`, {
        method: 'get',
        // withCredentials: true,
        // credentials: 'include',
        headers: {
            'Authorization': `Bearer ${userToken}`,
            'Content-Type': 'application/json'
        },
    }).then(response => {
        return response.text();
    });
}

const makeMove = (userToken, gameId, selectedPit) => {
    const jsonCliente = JSON.stringify({
        gameId: gameId,
        selectedPit: selectedPit
    });
    return fetch('/kalaha', {
        method: 'put',
        // withCredentials: true,
        // credentials: 'include',
        headers: {
            'Authorization': `Bearer ${userToken}`,
            'Content-Type': 'application/json'
        },
        body: jsonCliente
    }).then(response => {
        return response.text();
    });
}
