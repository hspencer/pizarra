/*
 *                             
 * Pizarra
 * 
 * dibujo para proyectores
 *
 * @hspencer
 *
 */

function setup() {
    document.addEventListener("touchmove", preventBehavior, { passive: false });
    pizarra = createCanvas(windowWidth, windowHeight); // pantalla completa
    let parent = document.getElementsByTagName("BODY")[0];
    lineToggle = select("#lineSwitch");
    modeToggle = select("#modeSwitch");

    instructions = false;
    resizeCanvas(parent.offsetWidth, parent.offsetHeight);
    strokeCap(ROUND);
    lineas = [];
    temporal = new Linea();
    count = 0;
    fondoNegro = true;
    noFill();
    grosor = 1;
    cursor(CROSS);
    textFont('monospace');
    blanco = color(255, 200);
    negro = color(0, 200);
    straight = false;
}

function draw() {
    strokeWeight(grosor);
    noFill();
    if (fondoNegro) {
        background(negro);
        stroke(blanco);
    } else {
        background(blanco);
        stroke(negro);
    }

    // SHIFT para una línea recta
    if (keyIsPressed && mouseIsPressed && keyCode === 16 || mouseIsPressed && straight) {

        console.log("straight!!!!");
        /* 
           la línea temporal debe ser de 2 puntos, para que sea recta, 
           se deja el primer punto y el segundo es la posición del mouse.
           */
        if (temporal.puntos.length > 2) {
            for (let i = 2; i < temporal.puntos.length; i++) {
                temporal.puntos.splice(i, 1);
            }
        }
        let last = createVector(mouseX, mouseY);
        temporal.puntos.push(last);
    }

    for (let i = 0; i < count; i++) {
        lineas[i].draw();
    }
    temporal.g = grosor;
    temporal.draw();
    if (instructions) { showInstructions(); }
}