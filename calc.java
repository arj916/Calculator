import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.*;


import javalib.funworld.World;
import javalib.funworld.WorldScene;
import java.awt.Color;
import javalib.worldimages.*;

//represents an abstract class of either number buttons or math buttons
abstract class Button {
  
  //declaration of fields
  char value;
  Posn pos;
  
  //constructors
  Button(char value){
    this.value = value;
    this.pos = new Posn(0,0);
  }
  Button(char value, Posn pos){
    this.value = value;
    this.pos = pos;
  }
  
  //draws the buttons on the calculator
  abstract WorldImage draw();
  
  //determines if a button is a number button or a math button
  abstract boolean isNum();
  
  //determines if a button has been clicked
  boolean buttonClick(Posn p) {
    return(pos.x > p.x -30 && pos.x < p.x + 3-0 && pos.y > p.y - 40 && pos.y  < p.y +40); 
  }
  
  
}
//class of buttons that represent the numbers on the calc
class NumButton extends Button{
  
  //declaration of fields
  WorldImage c = new RectangleImage(60, 80, "solid", Color.gray);
  
  //constructors
  NumButton(char value){
    super(value);
  }
  NumButton(char value, Posn p){
    super(value,p);
  }
  
  //draws the button with the number on it
  public WorldImage draw() {
    return new OverlayOffsetImage(new TextImage(Character.toString(this.value), Color.red),0,0,c); 
  }
  
  //determines if a button is a number button
  public boolean isNum() {
    return true;
  }
 
}

//a class that represents a button with a Math expression +,-,/,*
class MathButton extends Button{
  
  //constructors
  MathButton(char value){
    super(value);
  }
  MathButton(char value, Posn p){
    super(value, p);
  }
  
  //draws the math expressions on the buttons for the calc
  public WorldImage draw() {
    
    if(value == '=') {
      WorldImage c = new RectangleImage(60, 80, "solid", Color.green);
      return new OverlayOffsetImage(new TextImage(Character.toString(this.value), Color.white),0,0,c); 
    }
    else if(value == 'C') {
      WorldImage c = new RectangleImage(60, 80, "solid", Color.orange);
      return new OverlayOffsetImage(new TextImage(Character.toString(this.value), Color.white),0,0,c); 
    }
    WorldImage c = new RectangleImage(60, 80, "solid", Color.blue);
    return new OverlayOffsetImage(new TextImage(Character.toString(this.value), Color.white),0,0,c); 
  }
  
  //determines if a button is a number buttons
  public boolean isNum() {
    return false;
  }
 
}

//class that creates the calculator in the world
class Calc extends World {
  
  //declaration of fields
  ArrayList<Button> buttonList;
  ArrayList<Button> pressed;
  WorldImage eq;
  WorldScene ws = new WorldScene(400, 600);
  double pastVal;
  
  //constructors
  Calc(ArrayList<Button> pressed){
    this.buttonList = (new ArrayList<Button>(Arrays.asList(
        new NumButton('0'),new NumButton('1'),new NumButton('2'),
        new NumButton('3'),new NumButton('4'),new NumButton('5'),
        new NumButton('6'),new NumButton('7'),new NumButton('8'),
        new NumButton('9'),new MathButton('+'),new MathButton('-'),
        new MathButton('*'),new MathButton('/'),new MathButton('='),
        new MathButton('C'))));
    this.pressed = pressed;
    this.eq = new RectangleImage(230, 120, "solid", Color.black);
    this.pastVal = 0.0;

  }
  
  Calc(ArrayList<Button> buttonList,ArrayList<Button> pressed){
    this.buttonList = buttonList;
    this.pressed = pressed;
    this.eq = new RectangleImage(230, 120, "solid", Color.black);
    this.pastVal =0.0;
  }
  
  //creates the calculator image within the world
  public WorldScene makeScene() {
    WorldScene ws = new WorldScene(400, 600);
    WorldScene part1 = ws.placeImageXY(eq, 115,60);
    int currentButton = 0;
    int yPos = 100;
    int xPos= 20;
    for(int i=0; i< 3; i++) {
      xPos= 20;

      for(int j=0;j<3;j++) {
        part1= part1.placeImageXY(buttonList.get(currentButton).draw(), xPos,yPos);
        buttonList.get(currentButton).pos = new Posn(xPos, yPos);
        xPos= xPos + 60;
        currentButton=currentButton +1;
      }
      yPos=yPos+ 80;
    }
    yPos = 100;
    part1= part1.placeImageXY(buttonList.get(currentButton).draw(), 20,340);
    currentButton=currentButton +1;
    for(int i=0;i<4;i++) {
      part1= part1.placeImageXY(buttonList.get(currentButton).draw(), xPos,yPos);
      buttonList.get(currentButton).pos = new Posn(xPos, yPos);
      yPos= yPos + 80;
      currentButton=currentButton +1;
    }
    part1= part1.placeImageXY(buttonList.get(currentButton).draw(), 80,340);
    buttonList.get(currentButton).pos = new Posn(80, 340);
    part1= part1.placeImageXY(buttonList.get(currentButton+1).draw(), 140,340);
    buttonList.get(currentButton+1).pos = new Posn(140, 340);
    return part1;
  }
  
  //changes the Calc in respond to a button click
  public Calc onMouseClicked(Posn pos) {
    int xPos =110;
    for(int i=0;i<buttonList.size();i++) {
      if(buttonList.get(i).buttonClick(pos)){
        pressed.add(0,buttonList.get(i));
        if(pastVal != 0) {
          eq = new OverlayOffsetImage(new TextImage(Character.toString(pressed.get(0).value), Color.red),
               xPos-(10*pressed.size())-25,20,eq);
        }else {
          eq = new OverlayOffsetImage(new TextImage(Character.toString(pressed.get(0).value), Color.red),
              xPos-(10*pressed.size()),20,eq);
        }
      }
    }
    return this;
  }
  
  //carries out the math expression in the calculator
  double equation(String n1, String n2, char eq) {
  
    double num1 = Double.valueOf(n1);
    double num2 = Double.valueOf(n2);
    if( eq == '-') {
      return num1 - num2;
    }
    if(eq == '+') {
      return num1 + num2;
    }
   
    else if( eq == '*') {
      return num1 * num2;
    }
    else {
      return num1 / num2;
    }
    
  }
  

  
  //separates the parts of the equation and carries out the math expression
  Calc doMath(){
    double answer =0.0;
    String part1 ="";
    String part2 ="";
    char exp ='n';
    int cIndex=0;
    if(pastVal != 0.0) {
      part1= String.valueOf(pastVal);
      for(int i=pressed.size()-1; i>0;i--) {
        if(!(pressed.get(i).isNum())) {
          cIndex=i;
          break;
        }
      }
    }
    else {
      for(int i=pressed.size()-1; i>0;i--) {
        if(!(pressed.get(i).isNum())) {
          cIndex=i;
          break;
        }
        part1= part1+pressed.get(i).value;
      }
    }
    exp = pressed.get(cIndex).value;
    for(int i=cIndex-1;i>0;i--) {
      part2=part2 + pressed.get(i).value;
    }
    answer= this.equation(part1,part2,exp);
    this.pastVal = answer;
    answer = Math.round(answer *100.0)/100.0;
    eq = new OverlayOffsetImage(new TextImage(String.valueOf(answer), Color.red),
        95,20,new RectangleImage(230, 120, "solid", Color.black));
    this.pressed = new ArrayList<Button>(); 
    return this;
  }
  
  //changes the calculator world in response to changes in the pressed arraylist
  public Calc onTick() {
    if(pressed.size()== 0) {
      return this;
    }
    else {
      if(pressed.get(0).value == '=') {
        return this.doMath();
      }
      else if(pressed.get(0).value == 'C') {
        this.pressed = new ArrayList<Button>();
        this.eq =new RectangleImage(230, 120, "solid", Color.black);
        this.pastVal=0;
        return this;
      }
      else {
        return this;
      }
    }
  }
  
  
}