class Linea {
  ArrayList puntos;
  float g; // grosor
  color c;
  
  Linea() {
    puntos = new ArrayList();
  } 

  void draw() {
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

