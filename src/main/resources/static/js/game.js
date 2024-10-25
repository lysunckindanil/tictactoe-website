// session manipulations

function createSession() {
    const token = $("meta[name='_csrf']").attr("content");
    const dimension = document.getElementById('dimension-select').value;
    $.ajax({
        url: '/game/create',
        headers: {"X-CSRF-TOKEN": token},
        method: 'POST',
        data: {'dimension': dimension},
        success: function (data) {
            renderSessionPage(data['target']);
            startReloadPositionsLoop(1000);
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

function endSession() {
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
                         <button class="btn btn-secondary" onclick="endSession()">End session</button>`
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
    gameGrid.style.setProperty('grid-template-columns',
        'repeat(' + dimension + ', 1fr)');

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
    renderPositions(positions);
}

function startReloadPositionsLoop(duration) {
    let counter = 0;
    let state = null
    const target = getTargetHttpEntity();

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


function renderPositions(positions) {
    for (let i = 0; i < positions.length; i++) {
        if (positions[i] !== 0) {
            const cell = document.getElementById(String(i + 1));
            const cell_width = cell.style.width;
            if (positions[i] === 1)
                cell.style.backgroundImage = 'url(/img/player1.jpg)';

            else {
                cell.style.backgroundImage = 'url(/img/player2.jpg)';
            }
            cell.style.backgroundSize = 'cover';
        }
    }
}

function gameOverShow(winner) {
    alert('winner is ' + winner)
}

function showErrorModal(error) {
    let message = jQuery.parseJSON(error.responseText)['message']

    const modal = new bootstrap.Modal(document.getElementById('error-modal'), {keyboard: false});
    const modalTitle = document.getElementById("modal-title");
    const modalBody = document.getElementById("modal-body")
    modalTitle.textContent = 'Ошибка';
    modalBody.textContent = message
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
        data: {'target': Number(target)},
        success: function (data) {
            state = data;
        }
    });
    return state;
}

function getTargetHttpEntity() {
    let target = null;
    $.ajax({
        url: '/game/session',
        method: 'GET',
        async: false,
        success: function (data) {
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
        data: {'target': target},
        success: function (data) {
            dimension = data['dimension'];
        }
    });
    return dimension;
}
