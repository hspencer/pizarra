import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pizarra extends PApplet {

ArrayList lineas;
Linea temporal;
boolean mode;
int count;
float grosor;
float alfa; 
float h; // hue
float luz;
float sat;
String lastLineWeight;

Gui GROSOR, ALFA, CROMA;

public void setup() {
  size(displayWidth, displayHeight); //pantalla completa
  smooth();                          // suavizado (anti-alisaing)
  background(0);                     // fondo negro
  stroke(255);
  strokeWeight(2);
  // strokeCap(PROJECT);
  lineas = new ArrayList();
  temporal = new Linea();
  count = 0;
  mode = true;
  noFill();
  grosor = 1;
  font = createFont("Monaco", 10);
  textFont(font, 10);
  rectMode(CORNERS);
  GROSOR = new Gui(10, 100, 20, 200);
  ALFA   = new Gui(10, 200, 20, 300);
  CROMA  = new Gui(10, 300, 20, 400);
  colorMode(HSB, 1, 1, 1, 1);
  lastLineWeight = "";
}

public void draw() {
  noFill();
  if (mode) {
    background(0, 0, 0);
  }
  else {
    background(0, 0, 1);
  }
  
  GROSOR.draw();
  grosor = GROSOR.yval;
 
  ALFA.draw();
  CROMA.draw();
  
  stroke(CROMA.yval, 1, 1, ALFA.yval);
  line(15, 500, 15, 500 + grosor);

  for (int i = 0; i < count; i++) {
    Linea lin = (Linea)lineas.get(i);
    lin.draw();
  }
  temporal.draw();
  printvals();
}


public void mousePressed() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.puntos.add(punto);
}

public void mouseDragged() {
  PVector punto = new PVector(mouseX, mouseY);
  temporal.c = color(CROMA.yval, 1, 1, 1 - ALFA.yval);
  temporal.g = GROSOR.yval * 100;
  temporal.puntos.add(punto);
}

public void mouseReleased() {
  lineas.add(temporal);
  lastLineWeight = ""+temporal.g;

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

  if (key == ',') {
    if (grosor > 1) {
      grosor --;
    }
  }
  if (key == '.') {
    grosor ++;
  }
}

PFont font;
public void printvals() {
  fill(1);
  text(grosor +", "+lastLineWeight, 10, height - 20);
  text(millis()+"\t"+ mouseX+"\t"+mouseY, 10, 20);
}

class Gui {

  float x1, x2, y1, y2;
  float xval, yval;

  Gui(float x1, float y1, float x2, float y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    xval = x1;
    yval = y1;
  }

  public void calc() {
    if ( 
    mouseX < this.x2 && 
      mouseX > this.x1 && 
      mouseY > this.y1 && 
      mouseY < this.y2) {

      xval = map(mouseX, x1, x2, 0, 1);
      yval = map(mouseY, y1, y2, 0, 1);
    }
  }

  public void draw() {
    calc();
    noStroke();
    // rect(x1, y1, x2, y2);
    fill(CROMA.yval, 0, 1);
    beginShape();
    vertex(x1, y1);
    vertex(x2, y1);
    vertex((x1 + x2)/2, y2);
    endShape();
  }
}

class Linea {
  ArrayList puntos;
  float g; // grosor
  int c;
  
  Linea() {
    puntos = new ArrayList();
  } 

  public void draw() {
    noFill();
    strokeWeight(g);
    stroke(c);
    beginShape();
    for (int i = 0; i < puntos.size(); i++) {
      PVector punto = (PVector)puntos.get(i);
      vertex(punto.x, punto.y);
    }
    endShape();
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "pizarra" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}