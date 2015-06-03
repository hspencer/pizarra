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
  size(displayWidth, displayHeight); // pantalla completa
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

