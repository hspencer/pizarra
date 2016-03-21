class Linea {
  ArrayList puntos;
  float g = 2; // grosor
  color c = blanco;

  Linea() {
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