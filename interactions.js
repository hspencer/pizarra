function mousePressed() {
    let punto = createVector(mouseX, mouseY);
    temporal.puntos = [];
}

function touchStarted() {
    let punto = createVector(mouseX, mouseY);
    temporal.puntos = [];
}

function mouseDragged() {
    let tpunto = createVector(mouseX, mouseY);
    temporal.puntos.push(tpunto);
}

function touchMoved() {
    let tpunto = createVector(mouseX, mouseY);
    temporal.puntos.push(tpunto);
}

function mouseReleased() {
    if (!toggling) {
        lineas.push(temporal);
        temporal.g = grosor;
        temporal = new Linea();
        count = lineas.length;

    }

}

function touchEnded() {
    if (!toggling) {
        lineas.push(temporal);
        temporal.g = grosor;
        temporal = new Linea();
        count = lineas.length;

    }
}

function keyTyped() {

    if (key === 'm') {
        toggleMode();
    }

    if (key === 's') {
        downloadImage();
    }

    if (key === 'z') {
        undo();
    }

    if (key === ' ') {
        newDrawing();
    }

    if (key === ',') {
        grosor++;
    }
    if (key === '.') {
        if (grosor > 1) {
            grosor--;
        }
    }

    if (key === 'i' || key === 'I') {
        instructions = !instructions;
    }
}

function preventBehavior(e) {
    e.preventDefault();
};


function showInstructions() {
    let m = 100; // margen
    fill(negro);
    noStroke();
    rect(m, m, width - 2 * m, height - 2 * m);
    fill(blanco);
    text("(i) - mostrar / ocultar instrucciones\n(SHIFT) - dibujar trazos rectos (debe estar presionado)\n(,  .) - modificar grosor del trazo\n(m) swith entre modo claro y oscuro\n(s) - salvar la imagen actual al escritorio\n(z) - deshacer\n(ESPACIO) - borrar todo", m * 2, m * 2);
}

/*** for buttons */

function newDrawing() {
    lineas = [];
    count = lineas.length;
}

function undo() {
    if (count > 0) {

        lineas.splice(lineas.length - 1, 1);
        count = lineas.length;

    }

}

function downloadImage() {
    let filename = "img/" + year() + "-" + month() + "-" + day() + "--" + hour() + "-" + minute() + "-" + second();
    let file = createImage(width, height);
    file = get();
    file.save(filename, 'png');
}

function toggleMode() {
    fondoNegro = !fondoNegro;
    if (fondoNegro) {
        modeToggle.html("<i class='icon-radio-unchecked'></i>dark mode");
    } else {
        modeToggle.html("<i class='icon-radio-checked'></i>light mode");
    }
}

function toogleLine() {
    straight = !straight;
    if (!straight) {
        lineToggle.html("<i class='icon-radio-unchecked'></i>free line");
    } else {
        lineToggle.html("<i class='icon-radio-checked'></i>straight line");
    }
}

function preventDrawing() {
    toggling = true;
}

function allowDrawing() {
    toggling = false;
}