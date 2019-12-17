
var socket = new WebSocket('ws://' + location.hostname + (location.port ? ':' + location.port : '') + '/Concurrencia/persona');

enviar = function (mensaje) {
    socket.send(mensaje);
};

conectar = socket.onopen = function (event) {
    console.log('Conexión abierta ' + event);
};

socket.onmessage = function (event) {
    notificarActualizar(event);
};

desconectar = socket.onclose = function (event) {
    console.log('Conexión socket cerrada ' + event);
};

socket.onerror = function (event) {
    console.log('Error! ' + event);
};

cerrarSesion = function () {
    socket.onclose = function () {};
    socket.close();
    console.log('Conexión socket cerrada!!');
};


function notificarActualizar(event) {
    console.log(event.data);
}
