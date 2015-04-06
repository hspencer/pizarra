class Linea {
  ArrayList puntos;
  Linea() {
    puntos = new ArrayList();
  } 

  void draw() {
    beginShape();
    for (int i = 0; i < puntos.size(); i++) {
      PVector punto = (PVector)puntos.get(i);
      vertex(punto.x, punto.y);
    }
    endShape();
  }
}

