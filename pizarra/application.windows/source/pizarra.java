import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class pizarra extends PApplet {

ArrayList lineas;
Linea temporal;
boolean mode;
int count;

public void setup() {
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

public void draw() {
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


public void mousePressed() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
}

public void mouseDragged() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
}

public void mouseReleased() {
  lineas.add(temporal);
  temporal = new Linea();
  count++;
}

public void keyPressed() {
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

class Linea {
  ArrayList puntos;
  Linea() {
    puntos = new ArrayList();
  } 

  public void draw() {
    beginShape();
    for (int i = 0; i < puntos.size(); i++) {
      PVector punto = (PVector)puntos.get(i);
      vertex(punto.x, punto.y);
    }
    endShape();
  }
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "pizarra" });
  }
}