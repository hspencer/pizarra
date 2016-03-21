/*
 *                             
 * Pizarra
 * 
 * dibujo para proyectores
 *
 * @hspencer
 *
 */

import codeanticode.tablet.*;
Tablet tablet;

ArrayList lineas;                   // todas las líneas del dibujo
Linea temporal;                     // la línea que se está dibujando
boolean fondoNegro;                 // modo blanco o negro
int count;                          // contador de líneas
float grosor;                       // grosor de la línea
float[] grosores;                   // buffer de grosores
int contador;

color blanco = color(255, 200);
color negro = color(0, 200);

void setup() {
  fullScreen();
  //pixelDensity(2);
  smooth();                          // suavizado (anti-alisaing)
  // fondo negro
  tablet = new Tablet(this); 
  strokeCap(ROUND);
  lineas = new ArrayList<Linea>();
  temporal = new Linea();
  count = 0;
  fondoNegro = true;
  noFill();
  grosor = 1;
  grosores = new float[16]; // buffer de 16 grosores 
  cursor(CROSS);
  contador = 0;
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
  contador %= grosores.length;
  grosores[contador] = grosor;
  temporal.g = getAverage(grosores);
  temporal.draw();
  contador++;
}

float getAverage(float[] a) {
  float val = 0;
  for (int i = 0; i < a.length; i++) {
    val += a[i];
  }
  return (val / (float)a.length);
}