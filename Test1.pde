void setup(){
  size(2500, 2000);
  //big ol nuts
  for (int i = 0; i < 80; i = i+5) {
  line(30, i, 80, i);
}
}
void draw(){
  background(0);
  rect(1250, 1000, 500, 400);
}
