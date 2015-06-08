/*
 *                             
 * Pizarra
 * 
 * dibujo para proyectores
 *
 * @hspencer
 *
 */

ArrayList lineas;                   // todas las líneas del dibujo
Linea temporal;                     // la línea que se está dibujando
boolean fondoNegro;                 // modo blanco o negro
int count;                          // contador de líneas
float grosor;                       // grosor de la línea

color blanco = color(255, 200);
color negro = color(0, 200);

void setup() {
  size(900, 600); // pantalla completa
  smooth();                          // suavizado (anti-alisaing)
  // fondo negro

  strokeCap(ROUND);
  lineas = new ArrayList<Linea>();
  temporal = new Linea();
  count = 0;
  fondoNegro = true;
  noFill();
  grosor = 1;
  cursor(CROSS);
}

void draw() {

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
  if (keyPressed && mousePressed && key == CODED && keyCode == SHIFT) {
    /* 
     la línea temporal debe ser de 2 puntos, para que sea recta, 
     se deja el primer punto y el segundo es la posición del mouse.
     */
    if (temporal.puntos.size() > 2) {
      for (int i = 2; i < temporal.puntos.size (); i++) {
        temporal.puntos.remove(i);
      }
    } 
    PVector last = new PVector(mouseX, mouseY);
    temporal.puntos.add(last);
  }

  for (int i = 0; i < count; i++) {
    Linea lin = (Linea)lineas.get(i);
    lin.draw();
  }
  temporal.g= grosor;
  temporal.draw();
}

class Linea {
  ArrayList puntos;
  float g = 1; // grosor
  color c = blanco;

  Linea() {
    puntos = new ArrayList<PVector>();
  } 

  Linea(float x1, float y1, float x2, float y2) {
    puntos = new ArrayList<PVector>();
  }

  void draw() {
    strokeWeight(g);
    if (fondoNegro) {
      stroke(blanco);
    } else {
      stroke(negro);
    }
    beginShape();
    for (int i = 0; i < puntos.size (); i++) {
      PVector punto = (PVector)puntos.get(i);
      vertex(punto.x, punto.y);
    }
    endShape();
  }
}

void mousePressed() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos = new ArrayList<PVector>();
}

void mouseDragged() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
}

void mouseReleased() {
  lineas.add(temporal);
  temporal.g = grosor;
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


