import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// Action -> control the paddle , Mouse -> checking click , Item -> choose the difficulty
// Note: 
// Implicit type casting : automatic conversion as it is done by compiler w/o programmer assistance
// explicit type casting : programmer tells compiler tp type cast one data type to another data type

public class Gamesystem extends JPanel implements ActionListener, MouseListener, ItemListener{
  private boolean play = false; //to make the component can move when press start
  protected int score = 0;
  public static int totalBricks = 15;
  protected int totalClick = 0;
  protected int leftClick = 0;
  protected int rightClick = 0;
  Paddle player = new Paddle(250, 450, 100, 8);
  Ball ball = new Ball(120,350,20);
  Timer timer = new Timer(8,this);
  Brick brick;
  JPanel controlPanel = new JPanel();
  JPanel movePanel = new JPanel();
  JPanel modePanel = new JPanel();
  JButton startBT = new JButton("Start");
  JButton leftBT = new JButton("Left");
  JButton rightBT = new JButton("Right");
  JRadioButton easyRB = new JRadioButton("Easy");
  JRadioButton hardRB = new JRadioButton("Hard");
  JLabel founderNameLB = new JLabel("created by \tPavarissy");


  Gamesystem(String s){
    timer.start();
    brick = new Brick(3,5);
    //register 3 listeners
    leftBT.addMouseListener(this);
    rightBT.addMouseListener(this);
    leftBT.addActionListener(this);
    rightBT.addActionListener(this);
    startBT.addActionListener(this);
    easyRB.addItemListener(this);
    hardRB.addItemListener(this);
    setFocusable(true);

    //movePanel setting
    movePanel.setLayout(new FlowLayout());
    movePanel.add(leftBT);
    movePanel.add(rightBT);

    //modePanel setting
    modePanel.setLayout(new GridLayout(2,1));
    modePanel.add(easyRB);
    modePanel.add(hardRB);
    //controlPanel setting
    controlPanel.setLayout(new GridLayout(1,4));
    controlPanel.add(startBT);
    controlPanel.add(movePanel);
    controlPanel.add(modePanel);
    controlPanel.add(founderNameLB); 

  
    
    JFrame frame = new JFrame(s);
    frame.setLayout(new BorderLayout());
    frame.add(this, BorderLayout.CENTER);
    frame.add(controlPanel, BorderLayout.SOUTH);
    frame.setSize(500,550);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent e){
    timer.start();
    if(e.getSource() == startBT){
      play = true;
      ball.Xdir = -1;
      ball.Ydir = -2;
    }

    if(e.getSource() == leftBT){
      if (player.posX < 0){
        player.posX = 0;
      }
      else{
        player.moveLeft();
      }
    }

    else if(e.getSource() == rightBT){
      if (player.posX > 470){
        player.posX = 470;
      }
      else{
        player.moveRight();
      }
    }

    
    if(play){
      // to check taht the ball intersects with the paddle => must create a rectangle to check intersection
      if(new Rectangle(ball.posX, ball.posY,20, 20).intersects(new Rectangle(player.posX, player.posY , player.width , player.height))){
        ball.Ydir = -ball.Ydir;
      }

      //using for loop to check each brick and then we will set the value to 0 (in order to delete it when it is hit)
      // note : j = column , i = row
      startLoop : for(int i = 0; i < brick.BrickTable.length ; i++){
        for(int j = 0; j < brick.BrickTable[0].length ; j++){
          if(brick.BrickTable[i][j] > 0){
            int brickX = j*brick.brickWidth + 50; // number of column* width of brick = to find the posX of the n-brick
            int brickY = i*brick.brickHeight + 50; // number of row * height of brick = to find the posY of the n-brick
            int brickWidth = brick.brickWidth; // finding by division of the total column of table and width
            int brickHeight = brick.brickHeight; // finding by division of the total row of table and height

            //Checking intersection
            //brick detection (Rectangle form)
            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

            //use the ball detection again
            Rectangle ballDetect = new Rectangle(ball.posX, ball.posY,20, 20);

            Rectangle brickDetect = rect;

            //Checking the intersection => intersect = hit the brick
            if(ballDetect.intersects(brickDetect)){
              brick.setBrickValue(0, i, j);
              totalBricks--;
              score += 5;

              //but it will cause problem on brick (when the ball is hit, it wont reflect back), so we need to add some method
              if(ball.posX +19 <= brickDetect.x || ball.posX + 1 >= brickDetect.x + brickDetect.width){
                ball.Xdir = -ball.Xdir;
              }
              else{
                ball.Ydir = -ball.Ydir;
              }

              break startLoop;
            }
          }
        }
      }

      ball.posX += ball.Xdir;
      ball.posY += ball.Ydir;
      if(ball.posX < 0){
        ball.Xdir = -ball.Xdir;
      }

      if(ball.posY < 0){
        ball.Ydir = -ball.Ydir;
      }

      if(ball.posX > 470){
        ball.Xdir = -ball.Xdir;
      }
    }
    repaint();
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    //background
    g.setColor(Color.BLACK);
    g.fillRect(1, 1, 497, 497);

    //border Color
    g.setColor(Color.YELLOW);
    g.fillRect(0, 0, 499, 3); // top border
    g.fillRect(0, 0, 3, 500); // left border
    g.fillRect(497, 0, 3, 500); //right border

    //Paddle
    g.setColor(Color.GREEN);
    g.fillRect(player.posX, player.posY, player.width, player.height);

    //ball
    g.setColor(Color.YELLOW);
    g.fillOval(ball.posX, ball.posY, ball.radius , ball.radius);

    //draw a table (explicit casting ==> to make graphics g can )
    brick.draw((Graphics2D)g);

    // to draw the score
    g.setColor(Color.YELLOW);
    g.setFont(new Font("serif", Font.BOLD, 20));
    g.drawString("Points: "+score, 220, 30);
    g.setFont(new Font("serif", Font.BOLD, 10));
    g.drawString("move counting:"+totalClick,220,45);

    // to check that the game finished
    if(totalBricks <= 0){
      play = false;
      // to make the ball stop!
      ball.posX = 0;
      ball.posY = 0;
      g.setColor(Color.YELLOW);
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("Congratulations", 120, 250); 
      g.drawString("You Win!!!", 130, 300);
    }

    if(ball.posY > 470){
      play = false;
      ball.Xdir = 0;
      ball.Ydir = 0;
      g.setColor(Color.RED);
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("Game Over", 150, 250);
      g.drawString("You Lose!!!", 150, 290);
    }
  }

  public void mousePressed(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}

  //for player who want to increase their speed or reduce the move of themselves
  public void mouseClicked(MouseEvent e){
    if(e.getSource() == leftBT){
      if(leftClick >= 0){
      leftClick = e.getClickCount();
      } 
    }
    if(e.getSource() == rightBT){
      if(rightClick >= 0){
      rightClick = e.getClickCount();
      }
    }
    totalClick = leftClick + rightClick;
    repaint();
  }

  public void itemStateChanged(ItemEvent e){
    //easy : default speed of the ball | hard : double the speed of the ball
    if(e.getSource() == easyRB){
      hardRB.setSelected(false);
      ball.Xdir = -1;
      ball.Ydir = -2;
    }

    else if(e.getSource() == hardRB){
      easyRB.setSelected(false);
      ball.Xdir = -((int)Math.pow(ball.Xdir-1,2));
      ball.Ydir = -((int)Math.pow(ball.Ydir-1,2));
      //casting for prevent lossy conversion from double to int
    }
  }
}