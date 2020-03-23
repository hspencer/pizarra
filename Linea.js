class Linea {
  constructor(){
  this.puntos = [];
  this.g = 1; // grosor
  this.c = blanco;
  }
 
  draw() {
    strokeWeight(this.g);
    if (fondoNegro) {
      stroke(blanco);
    } else {
      stroke(negro);
    }
    beginShape();
    for (let i = 0; i < this.puntos.length; i++) {
      //let punto = (PVector)puntos.get(i);
      vertex(this.puntos[i].x, this.puntos[i].y);
    }
    endShape();
  }
}

