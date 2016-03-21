import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import codeanticode.tablet.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pizarra extends PApplet {

/*
 *                             
 * Pizarra
 * 
 * dibujo para proyectores
 *
 * @hspencer
 *
 */


Tablet tablet;

ArrayList lineas;                   // todas las l\u00edneas del dibujo
Linea temporal;                     // la l\u00ednea que se est\u00e1 dibujando
boolean fondoNegro;                 // modo blanco o negro
int count;                          // contador de l\u00edneas
float grosor;                       // grosor de la l\u00ednea
float[] grosores;                   // buffer de grosores
int contador;

int blanco = color(255, 200);
int negro = color(0, 200);

public void setup() {
  
  //pixelDensity(2);
                            // suavizado (anti-alisaing)
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

public void draw() {
  strokeWeight(grosor);
  noFill();
  if (fondoNegro) {
    background(negro);
    stroke(blanco);
  } else {
    background(blanco);
    stroke(negro);
  }

  // SHIFT para una l\u00ednea recta
  if (keyPressed && mousePressed && key == CODED && keyCode == SHIFT) {
    /* 
     la l\u00ednea temporal debe ser de 2 puntos, para que sea recta, 
     se deja el primer punto y el segundo es la posici\u00f3n del mouse.
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

public float getAverage(float[] a) {
  float val = 0;
  for (int i = 0; i < a.length; i++) {
    val += a[i];
  }
  return (val / (float)a.length);
}
class Linea {
  ArrayList puntos;
  float g = 2; // grosor
  int c = blanco;

  Linea() {
    puntos = new ArrayList<PVector>();
  } 


  public void draw() {
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
public void mousePressed() {
  // PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos = new ArrayList<PVector>();
}

public void mouseDragged() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
  grosor = 15 * tablet.getPressure();
  grosor = constrain(grosor, 1, 20);
  temporal.g = grosor;
}

public void mouseReleased() {
  // le resto 5 pasos, para recuperar el grosor anterior 5 ciclos atr\u00e1s
  temporal.g = grosores[abs((contador - 5) % grosores.length)];
  lineas.add(temporal);
  temporal = new Linea();
  count++;
}

public void keyPressed() {
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
  public void settings() {  fullScreen();  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "pizarra" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
