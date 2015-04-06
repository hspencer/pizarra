ArrayList lineas;
Linea temporal;
boolean mode;
int count;

void setup() {
  size(screen.width, screen.height); //pantalla completa
  smooth(); // suavizado (anti-alisaing)
  background(0); // fondo negro
  stroke(255);
  strokeWeight(2);
  strokeCap(PROJECT);
  lineas = new ArrayList();
  temporal = new Linea();
  count = 0;
  mode = true;
  noFill();
}

void draw() {
  if (mode) {
    background(0);
    stroke(255, 222);
  }
  else {
    background(255);
    stroke(0, 222);
  }
  for (int i = 0; i < count; i++) {
    Linea lin = (Linea)lineas.get(i);
    lin.draw();
  }
  temporal.draw();
}


void mousePressed() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
}

void mouseDragged() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
}

void mouseReleased() {
  lineas.add(temporal);
  temporal = new Linea();
  count++;
}

void keyPressed() {
  String filename = "img/"+year()+"-"+month()+"-"+day()+"--"+hour()+"-"+minute()+"-"+second()+".png";
  if (key == 'm') {
    mode = !mode;
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
}

