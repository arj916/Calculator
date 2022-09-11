import tester.Tester;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//represents examples and tests for the Calc class
class CalcExamples{
  CalcExamples() {}
  
  ArrayList<Button> button;
  ArrayList<Character> press;
  NumButton m;
  WorldImage w;
  MathButton m1;
  WorldImage w1;
  MathButton m2;
  WorldImage w2;
  MathButton m3;
  WorldImage w3;
  MathButton m4;
  NumButton m5;
  Calc ca;
  
  void initData() {
    this.button = (new ArrayList<Button>(Arrays.asList(
        new NumButton('0'),new NumButton('1'),new NumButton('2'),
        new NumButton('3'),new NumButton('4'),new NumButton('5'),
        new NumButton('6'),new NumButton('7'),new NumButton('8'),
        new NumButton('9'),new MathButton('+'),new MathButton('-'),
        new MathButton('*'),new MathButton('/'),new MathButton('='),
        new MathButton('C'))));
    this.press = new ArrayList<Character>(Arrays.asList('2','+','1','='));
    this.m = new NumButton('2', new Posn(50,50));
    this.w = new OverlayOffsetImage(new TextImage("2", Color.red),0,0,
        new RectangleImage(60, 80, "solid", Color.gray));
    this.m1 = new MathButton('=',new Posn(120,120));
    this.w1 = new OverlayOffsetImage(new TextImage("=", Color.white),0,0,
        new RectangleImage(60, 80, "solid", Color.green));
    this.m2 = new MathButton('C');
    this.w2 = new OverlayOffsetImage(new TextImage("C", Color.white),0,0,
        new RectangleImage(60, 80, "solid", Color.orange));
    this.m3 = new MathButton('+');
    this.w3 = new OverlayOffsetImage(new TextImage("+", Color.white),0,0,
        new RectangleImage(60, 80, "solid", Color.blue));
    this.ca = new Calc(new ArrayList<Button>());
    
    
    
  }
  
  void testDraw(Tester t) {
    this.initData();
    
    t.checkExpect(m.draw(), w);
    t.checkExpect(m1.draw(), w1);
    t.checkExpect(m2.draw(), w2);
    t.checkExpect(m3.draw(), w3);
    
  }
  
  void testIsNum(Tester t) {
    this.initData();
    t.checkExpect(m.isNum(),true);
    t.checkExpect(m1.isNum(), false);
  }
  
  void testButtonClick(Tester t) {
    this.initData();
    m.pos = new Posn(20,20);
    t.checkExpect(m.buttonClick(new Posn(40,30)), true);
    t.checkExpect(m.buttonClick(new Posn(120,120)), false);
    
  }
  
  void testMakeScene(Tester t) {
    this.initData();
    WorldImage c = new RectangleImage(60, 80, "solid", Color.gray);
    WorldImage c1 = new RectangleImage(60, 80, "solid", Color.blue);
    WorldImage c2 = new RectangleImage(60, 80, "solid", Color.green);
    WorldImage c3 = new RectangleImage(60, 80, "solid", Color.orange);
    WorldScene ws = new WorldScene(400, 600);
    WorldScene part1 = ws.placeImageXY(new RectangleImage(230, 120, "solid", Color.black)
        , 115,60);
    t.checkExpect(ca.makeScene(), part1.placeImageXY((new OverlayOffsetImage(new TextImage("0", Color.red),0,0,c))
        , 20, 100).placeImageXY((new OverlayOffsetImage(new TextImage("1", Color.red),0,0,c)),80,100).placeImageXY(
            (new OverlayOffsetImage(new TextImage("2", Color.red),0,0,c)),140,100).placeImageXY
        (new OverlayOffsetImage(new TextImage("3", Color.red),0,0,c),20,180).placeImageXY(
            new OverlayOffsetImage(new TextImage("4", Color.red),0,0,c),80,180).placeImageXY(
                new OverlayOffsetImage(new TextImage("5", Color.red),0,0,c),140,180).placeImageXY(
                    new OverlayOffsetImage(new TextImage("6", Color.red),0,0,c),20,260).placeImageXY(
                        new OverlayOffsetImage(new TextImage("7", Color.red),0,0,c),80,260).placeImageXY(
                            new OverlayOffsetImage(new TextImage("8", Color.red),0,0,c),140,260).placeImageXY(
                                new OverlayOffsetImage(new TextImage("9", Color.red),0,0,c),20,340).placeImageXY(
                                        new OverlayOffsetImage(new TextImage("+", Color.white),0,0,c1),200,100).placeImageXY(
                                            new OverlayOffsetImage(new TextImage("-", Color.white),0,0,c1),200,180).placeImageXY(
                                                new OverlayOffsetImage(new TextImage("*", Color.white),0,0,c1),200,260).placeImageXY(
                                                    new OverlayOffsetImage(new TextImage("/", Color.white),0,0,c1),200,340).placeImageXY(
                                                        new OverlayOffsetImage(new TextImage("=", Color.white),0,0,c2),80,340).placeImageXY(
                                                            new OverlayOffsetImage(new TextImage("C", Color.white),0,0,c3),140,340));
    
  }
  
  void testOnMouseClick(Tester t) {
    this.initData();
    Calc c2 = new Calc((new ArrayList<Button>(Arrays.asList(m,m1))), new ArrayList<Button>());
    t.checkExpect(c2.pressed.size(), 0);
    t.checkExpect(m.buttonClick(new Posn(50,50)), true);
    c2.onMouseClicked(new Posn(50,50));
    t.checkExpect(c2.pressed.size(),1);
    t.checkExpect(c2.pressed.get(0).value, '2');
    c2.onMouseClicked(new Posn(120,120));
    t.checkExpect(c2.pressed.size(),2);
    t.checkExpect(c2.pressed.get(0).value, '=');
    
  }
 
  
  void testEquation(Tester t) {
    this.initData();
    Calc c2 = new Calc((new ArrayList<Button>(Arrays.asList(m))),
        new ArrayList<Button>(Arrays.asList(m1,m,m)));
    t.checkExpect(c2.equation("22", "1", '+'), 23.0);
    t.checkExpect(c2.equation("22", "1", '-'), 21.0);
    t.checkExpect(c2.equation("2", "2", '*'), 4.0);
    t.checkExpect(c2.equation("4", "2", '/'), 2.0);
  }
  

  void testDoMath(Tester t) {
    this.initData();
    Calc c2 = new Calc((new ArrayList<Button>(Arrays.asList(m))),
        new ArrayList<Button>(Arrays.asList(m1,m,m3,m)));
    Calc c22 = new Calc((new ArrayList<Button>(Arrays.asList(m))),
        new ArrayList<Button>());
    c22.eq = new OverlayOffsetImage(new TextImage("4.0", Color.red),
        95,20,new RectangleImage(230, 120, "solid", Color.black));
    c22.pastVal=4.0;
    Calc c3 = new Calc((new ArrayList<Button>(Arrays.asList(m))),
        new ArrayList<Button>(Arrays.asList(m1,m,new MathButton('-'),m,m)));
    Calc c33 = new Calc((new ArrayList<Button>(Arrays.asList(m))),
        new ArrayList<Button>());
    c33.eq = new OverlayOffsetImage(new TextImage("20.0", Color.red),
        95,20,new RectangleImage(230, 120, "solid", Color.black));
    c33.pastVal=20.0;
    t.checkExpect(c2.doMath(),c22 );
    t.checkExpect(c3.doMath(),c33 );
  }
  void testOnTick(Tester t) {
    this.initData();
    Calc c2 = new Calc((new ArrayList<Button>(Arrays.asList(m))),
        new ArrayList<Button>(Arrays.asList(m1,m,m3,m)));
    Calc c3 =new Calc((new ArrayList<Button>(Arrays.asList(m))),
        new ArrayList<Button>());
    c3.eq = new OverlayOffsetImage(new TextImage("4.0", Color.red),
        95,20,new RectangleImage(230, 120, "solid", Color.black));
    c3.pastVal=4.0;
    t.checkExpect(c2.pressed.get(0).value,'=' );
    t.checkExpect(c2.onTick(), c3);
  }
  
  boolean testBigBang(Tester t) {
    this.initData();
    int worldWidth = 230;
    int worldHeight = 380;
    double tickRate = 1;
    Calc world = new Calc(new ArrayList<Button>());
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }
  
}