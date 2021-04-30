

public class Ball{
  public int posX = 0;
  public int posY = 0;
  // projectile motion + vecter summation (vecter x + vecter y = result ==> result is the direction of the ball)
  public int Xdir = -1; //move left first
  public int Ydir = -2; //move up first
  public int radius = 0;

  Ball(int posX, int posY, int radius){
    this.posX = posX;
    this.posY = posY;
    this.radius = radius;
  }
}