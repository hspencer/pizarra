function mousePressed() {
  let punto = createVector(mouseX, mouseY);
  temporal.puntos = [];
}

function mouseDragged() {
  let tpunto = createVector(mouseX, mouseY);
  temporal.puntos.push(tpunto);
}

function mouseReleased() {
  lineas.push(temporal);
  temporal.g = grosor;
  temporal = new Linea();
  count++;
}

function keyTyped() {
  let filename = "img/"+year()+"-"+month()+"-"+day()+"--"+hour()+"-"+minute()+"-"+second();
  if (key === 'm') {
    fondoNegro = !fondoNegro;
  }

  if (key === 's') {
    let file = createImage(width, height);
    file = get();
    file.save(filename, 'png');
  }

  if (key === 'z') {
    if (count > 0) {
      count --;
      lineas.splice(lineas.length, 1);
    }
  }

  if (key === ' ') {
    lineas = [];
    count = 0;
  }

  if (key === ',') {
    grosor ++;
  }
  if (key === '.') {
    if (grosor > 1) {
      grosor --;
    }
  }
  
  if( key === 'i' || key === 'I'){
   instructions = !instructions; 
  }
}

function showInstructions(){
  let m = 100; // margen
 fill(negro);
  noStroke();
  rect(m, m, width - 2*m, height - 2*m);
  fill(blanco);
  text("(i) - mostrar / ocultar instrucciones\n(SHIFT) - dibujar trazos rectos (debe estar presionado)\n(,  .) - modificar grosor del trazo\n(m) swith entre modo claro y oscuro\n(s) - salvar la imagen actual al escritorio\n(z) - deshacer\n(ESPACIO) - borrar todo", m*2, m*2);
}
