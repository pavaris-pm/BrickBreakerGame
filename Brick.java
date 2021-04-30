import java.awt.*;

public class Brick{
  public int BrickTable[][];
  public int brickWidth;
  public int brickHeight;


  // make a table generator
  public Brick(int row, int col){
    BrickTable = new int[row][col];
    for(int i = 0; i < BrickTable.length ; i++){
      for(int j = 0; j < BrickTable[0].length ; j++){
        BrickTable[i][j] = 1; //  1= true: can be drawn
      }
    }
    brickWidth = 400/col; // to make we know the width of each brick by dividing the number of col with the total width
    brickHeight = 100/row; // the the height by dividing number of row with the total height
  }

  // draw when the brick is exist
  public void draw(Graphics2D g){ //in order to draw an object(brick)
    for(int i = 0; i < BrickTable.length ; i++){
      for(int j = 0 ; j < BrickTable[0].length ; j++){
        if(BrickTable[i][j] > 0){
          g.setColor(Color.RED);
          g.fillRect(j*brickWidth + 50, i*brickHeight + 50, brickWidth, brickHeight); 

          g.setStroke(new BasicStroke(3)); //what for (method for graphics 2D)
          g.setColor(Color.BLACK); //setColor to be the same as background to make a table line
          g.drawRect(j*brickWidth + 50, i*brickHeight + 50, brickWidth, brickHeight);
        }
      }
    }
  }


  public void setBrickValue(int value, int row , int col){ // to set value to be 0 when the ball hit the brick
    BrickTable[row][col] = value;
  }
}