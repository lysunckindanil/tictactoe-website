// session manipulations

function create_session() {
    const token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        url: '/game/create',
        headers: {"X-CSRF-TOKEN": token},
        method: 'POST',
        success: function (data) {
            renderSessionPage(data);
            startPositionReloader(1000);
        }
    });
}

function connect_session() {
    const token = $("meta[name='_csrf']").attr("content");
    const input = document.getElementById("target_connect_input").value
    $.ajax({
        url: '/game/connect',
        headers: {"X-CSRF-TOKEN": token},
        method: 'POST',
        data: {'target': Number(input)},
        success: function (data) {
            renderSessionPage(data)
            startPositionReloader(1000);
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
    const section = document.getElementById("session_info");
    section.innerHTML = `<p>Target: ${target}</p>
                         <button onclick="endSession()">End session</button>`

}

document.addEventListener("DOMContentLoaded", function () {
    $.ajax({
        url: '/game/session',
        method: 'GET',
        success: function (data) {
            if (Number(data) !== 0) {
                renderSessionPage(data);
                startPositionReloader(1000);
            }
        }
    });
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
        }
    });
}

function startPositionReloader(duration) {
    const thread = setInterval(() => {
        let counter = 0;
        let state = null
        const target = getTargetHttpEntity();

        const checkState = () => {
            state = getStateHttpEntity(target)
            if (!state['over']) {
                console.log('sending')
                const counter_live = getCounterHttpEntity();
                if (counter_live !== counter) {
                    counter = counter_live;
                    handleGameState();
                }
            } else {
                clearInterval(thread);
                handleGameState();
                gameOver(state['winner']);
            }
        };

        checkState();
    }, duration);
}


function handleGameState() {
    const target = getTargetHttpEntity();
    const state = getStateHttpEntity(target)
    const isOver = state['over'];
    const positions = state['positions'];
    renderPositions(positions);
}

function renderPositions(positions) {
    for (let i = 0; i < positions.length; i++) {
        if (positions[i] !== 0) {
            const cell = document.getElementById(String(i + 1));
            if (positions[i] === 1) cell.style.backgroundColor = '#D657F9';
            else cell.style.backgroundColor = '#9DE4CD';
        }
    }
}

function gameOver(winner) {
    alert('winner is ' + winner)
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
            target = data;
        }
    });
    return target;
}

function getCounterHttpEntity() {
    let counter = null;
    $.ajax({
        url: '/game/counter',
        method: 'GET',
        async: false,
        success: function (data) {
            counter = data;
        }
    });
    return counter;
}