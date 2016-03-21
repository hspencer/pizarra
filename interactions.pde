void mousePressed() {
  // PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos = new ArrayList<PVector>();
}

void mouseDragged() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
  grosor = 15 * tablet.getPressure();
  grosor = constrain(grosor, 1, 20);
  temporal.g = grosor;
}

void mouseReleased() {
  // le resto 5 pasos, para recuperar el grosor anterior 5 ciclos atrás
  temporal.g = grosores[abs((contador - 5) % grosores.length)];
  lineas.add(temporal);
  temporal = new Linea();
  count++;
}

void keyPressed() {
  String filename = "img/"+year()+"-"+month()+"-"+day()+"--"+hour()+"-"+minute()+"-"+second()+".png";
  if (key == 'm') {
    fondoNegro = !fondoNegro;
  }

  if (key == 's') {
    saveFrame(filename);
  }

  if (key == 'z') {
    if (count > 0) {
      count --;
      lineas.remove(lineas.size()-1);
    }
  }

  if (key == ' ') {
    lineas.clear();
    count = 0;
  }

  if (key == ',') {
    grosor ++;
  }
  if (key == '.') {
    if (grosor > 1) {
      grosor --;
    }
  }
}