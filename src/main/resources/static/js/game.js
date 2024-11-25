// session manipulations

function createSession() {
    const token = $("meta[name='_csrf']").attr("content");
    const dimension = document.getElementById('dimension-select').value;
    $.ajax({
        url: '/game/create',
        headers: {"X-CSRF-TOKEN": token},
        method: 'POST',
        dataType: 'json',
        data: {'dimension': dimension},
        success: function (data) {
            renderSessionPage(data['target']);
            startReloadPositionsLoop(1000);
        },
        error: function (data) {
            showErrorModal(data)
        }
    });
}

function connectSession() {
    const token = $("meta[name='_csrf']").attr("content");
    const input = document.getElementById('connect-input').value;
    $.ajax({
        url: '/game/connect',
        headers: {"X-CSRF-TOKEN": token},
        method: 'POST',
        dataType: 'json',
        data: {'target': Number(input)},
        success: function (data) {
            renderSessionPage(data['target'])
            startReloadPositionsLoop(1000);
        },
        error: function (data) {
            showErrorModal(data)
        }
    });
}

function closeSession() {
    const token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: '/game/close',
        headers: {"X-CSRF-TOKEN": token},
        method: 'POST',
        success: function () {
            location.reload();
        }
    });
}

function renderSessionPage(target) {
    const sessionInfoSection = document.getElementById("session-info");
    sessionInfoSection.innerHTML = `<p>Target: ${target}</p>
                         <button class="btn btn-secondary" onclick="closeSession()">End session</button>`
    renderCellsGrid(getDimensionHttpEntity(target));
}

function renderCellsGrid(dimension) {
    const gameGrid = document.getElementById('game-inner');

    let html = '';
    for (let i = 1; i <= dimension * dimension; i++) {
        html += `<div class="cell" id="${i}" onclick="makeMove(this.id)"></div>`
    }

    gameGrid.innerHTML = html;
    gameGrid.style.gap = '4px';
    gameGrid.style.width = '500px';
    gameGrid.style.height = '500px';
    gameGrid.style.setProperty('grid-template-columns', 'repeat(' + dimension + ', 1fr)');

}

document.addEventListener("DOMContentLoaded", function () {
    const target = getTargetHttpEntity();
    if (target !== 0) {
        renderSessionPage(target);
        startReloadPositionsLoop(1000);
    }
});


// game process


function makeMove(cell) {
    const token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: '/game/move',
        headers: {"X-CSRF-TOKEN": token},
        method: 'POST',
        dataType: 'json',
        data: {'cell': Number(cell)},
        success: function () {
            handleGameState()
        },
        error: function (data) {
            showErrorModal(data)
        }
    });
}

function handleGameState() {
    const target = getTargetHttpEntity();
    const state = getStateHttpEntity(target)
    const positions = state['positions'];
    renderPositions(positions, getPlayersHttp(target));
}

function startReloadPositionsLoop(duration) {
    const target = getTargetHttpEntity();
    let counter = 0;
    let state = null

    const thread = setInterval(() => {
        const checkState = () => {
            state = getStateHttpEntity(target)
            if (!state['over']) {
                console.log('sending')
                const counter_live = getCounterHttpEntity(target);
                if (counter_live !== counter) {
                    counter = counter_live;
                    handleGameState();
                }
            } else {
                clearInterval(thread);
                handleGameState();
                gameOverShow(state['winner']);
            }
        };

        checkState();
    }, duration);
}


function renderPositions(positions, players) {
    for (let i = 0; i < positions.length; i++) {
        if (positions[i] !== 0) {
            const cell = document.getElementById(String(i + 1));
            if (positions[i] === 1) cell.style.backgroundImage = `url(/profile_photos/${players[0]}.png)`;
            else {
                cell.style.backgroundImage = `url(/profile_photos/${players[1]}.png)`;
            }
            cell.style.backgroundSize = 'cover';
        }
    }
}

function gameOverShow(winner) {
    const modal = new bootstrap.Modal(document.getElementById('error-modal'), {keyboard: false});
    const modalTitle = document.getElementById("modal-title");
    const modalBody = document.getElementById("modal-body")
    modalTitle.textContent = 'The game is over';
    modalBody.textContent = 'The winner is ' + winner + '!'
    modal.show();
}

function showErrorModal(error) {
    let message = jQuery.parseJSON(error.responseText)['message']

    const modal = new bootstrap.Modal(document.getElementById('error-modal'), {keyboard: false});
    const modalTitle = document.getElementById("modal-title");
    const modalBody = document.getElementById("modal-body")
    modalTitle.textContent = 'Error';
    modalBody.textContent = message;
    modal.show();
}

// http entities
function getStateHttpEntity(target) {
    let state = null;
    $.ajax({
        url: '/game/state',
        method: 'GET',
        async: false,
        dataType: 'json',
        data: {'target': target},
        success: function (data) {
            state = data;
        }
    });
    return state;
}

function getTargetHttpEntity() {
    let target = null;
    $.ajax({
        url: '/game/session', method: 'GET', dataType: 'json', async: false, success: function (data) {
            target = data['target'];
        }
    });
    return target;
}

function getCounterHttpEntity(target) {
    let counter = null;
    $.ajax({
        url: '/game/counter',
        method: 'GET',
        dataType: 'json',
        async: false,
        data: {'target': target},
        success: function (data) {
            counter = data['counter'];
        }
    });
    return counter;
}

function getDimensionHttpEntity(target) {
    let dimension = null;
    $.ajax({
        url: '/game/dimension',
        method: 'GET',
        async: false,
        dataType: 'json',
        data: {'target': target},
        success: function (data) {
            dimension = data['dimension'];
        }
    });
    return dimension;
}

function getPlayersHttp(target) {
    let players = [];
    $.ajax({
        url: '/game/players',
        method: 'GET',
        async: false,
        dataType: 'json',
        data: {'target': target},
        success: function (data) {
            players[0] = data['player1'];
            players[1] = data['player2'];
        }
    });
    return players;
}