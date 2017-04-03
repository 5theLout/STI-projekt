grassfield(0,0).
grassfield(50,0).
house(100,0).
house(150,0).
grassfield(200,0).
grassfield(250,0).
grassfield(300,0).
grassfield(350,0).
grassfield(400,0).
house(450,0).

garbages((100,0),[butelki(100), rozne(300)]).

bottles((100,0), 100).
various((100,0), 300).

hasgarbages(X,Y) :- house(X),garbages(X,Y).

roadfield(0,50).
house(50,50).
roadfield(100,50).
roadfield(150,50).
roadfield(200,50).
roadfield(250,50).
roadfield(300,50).
roadfield(350,50).
roadfield(400,50).
house(450,50).

roadfield(0,100).
grassfield(50,100).
roadfield(100,100).
roadfield(150,100).
grassfield(200,100).
grassfield(250,100).
grassfield(300,100).
grassfield(350,100).
roadfield(400,100).
grassfield(450,100).

roadfield(0,150).
grassfield(50,150).
grassfield(100,150).
roadfield(150,150).
house(200,150).
roadfield(250,150).
roadfield(300,150).
grassfield(350,150).
house(400,150).
grassfield(450,150).

roadfield(0,200).
house(50,200).
roadfield(100,200).
roadfield(150,200).
roadfield(200,200).
roadfield(250,200).
roadfield(300,200).
roadfield(350,200).
roadfield(400,200).
grassfield(450,200).

roadfield(0,250).
grassfield(50,250).
roadfield(100,250).
grassfield(150,250).
roadfield(200,250).
grassfield(250,250).
house(300,250).
house(350,250).
roadfield(400,250).
house(450,250).

roadfield(0,300).
house(50,300).
roadfield(100,300).
grassfield(150,300).
roadfield(200,300).
roadfield(250,300).
roadfield(300,300).
roadfield(350,300).
roadfield(400,300).
roadfield(450,300).

roadfield(0,350).
house(50,350).
roadfield(100,350).
roadfield(150,350).
roadfield(200,350).
grassfield(250,350).
grassfield(300,350).
house(350,350).
roadfield(400,350).
grassfield(450,350).

roadfield(0,400).
roadfield(50,400).
roadfield(100,400).
roadfield(150,400).
house(200,400).
house(250,400).
roadfield(300,400).
roadfield(350,400).
roadfield(400,400).
grassfield(450,400).

grassfield(0,450).
grassfield(50,450).
grassfield(100,450).
roadfield(150,450).
roadfield(200,450).
roadfield(250,450).
roadfield(300,450).
grassfield(350,450).
grassfield(400,450).
grassfield(450,450).

garbagetruck(0,50).