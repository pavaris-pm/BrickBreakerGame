//package GameComponent;
//import abstractComponentPackage.TwoDPaddle;

public class Paddle extends TwoDPaddle{
  // position of the paddle when the game start
  public int posX = 0;
  public int posY = 0;
  public int width = 0;
  public int height = 0;

  Paddle(int posX, int posY, int width, int height){
    this.posX = posX;
    this.posY = posY;
    this.width = width;
    this.height = height;
  }

  public int moveLeft(){
    return posX -= 20;
  }

  public int moveRight(){
    return posX += 20;
  }

}