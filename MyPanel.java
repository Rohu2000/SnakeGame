import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements KeyListener, ActionListener {
    //add graphics to the panel
    ImageIcon snaketitle= new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon rightmouth=new ImageIcon(getClass().getResource("rightmouth.png"));

    ImageIcon snakeimage=new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon upmouth=new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon downmouth=new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon leftmouth=new ImageIcon(getClass().getResource("leftmouth.png"));

    ImageIcon food=new ImageIcon(getClass().getResource("enemy.png"));
    boolean isUp=false;
    boolean isDown=false;
    boolean isRight=true;
    boolean isLeft=false;

    //positions of the food in random positions and the pixel size of food is 25px
    int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    Random random = new Random();//get random values

    int foodx=150;//initial position of the food
    int foody=150;
    int[] snakeX=new int[750];
    int[] snakeY=new int[750];
    int move=0;//initial move ,that is the game beginning from here
    int lengthOfSnake=3;//initially the size of the snake is 3

    Timer time;

    boolean GameOvervar=false;

    int score=0;

    //for making actions or movements , create keylisteners
    MyPanel(){
        addKeyListener(this);
        setFocusable(true);
        time=new Timer(150,this);
        time.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        //partition the menu and game window
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,850,576);

        if(move==0)
        {
            //the snakeimage is size of 25 pixel and for that we take 25 pixel difference
            snakeX[0]=100;//position of the head
            snakeX[1]=75;//
            snakeX[2]=50;

            snakeY[0]=100;
            snakeY[1]=100;
            snakeY[2]=100;
        }
        if(isRight)
        {
            rightmouth.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if(isDown){
            downmouth.paintIcon(this,g,snakeX[0],snakeY[0]);

        }
        if(isUp){
            upmouth.paintIcon(this,g,snakeX[0],snakeY[0]);

        }
        if(isLeft){
            leftmouth.paintIcon(this,g,snakeX[0],snakeY[0]);

        }

       // rightmouth.paintIcon(this,g,snakeX[0],snakeY[0]);//paint the right mouth,head part
        for(int i=1;i<lengthOfSnake;i++)
        {
            snakeimage.paintIcon(this,g,snakeX[i],snakeY[i]);//iterate to print the other two circle part of body
        }
        food.paintIcon(this,g,foodx,foody);

        //if the game is over, we need to show it to the user
        if(GameOvervar)
        {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("Game Over!!!!",300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.ROMAN_BASELINE,10));
            g.drawString("Press the Space Key to Restart the Game ",300,330);
        }
        //paint the score and length
        g.setColor(Color.white);
        g.setFont(new Font("ITALIC",Font.PLAIN,15));//15 size pxl
        g.drawString("Score: "+score,750,30);
        g.drawString("Length: "+ lengthOfSnake,750,50);
        g.dispose();//dispose all things
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //if we press space key then restarts the game 
        if(e.getKeyCode()==KeyEvent.VK_SPACE && GameOvervar)
        {
            restart();
        }


        //if we press right,left,up or down button it performs the following
        // operations
      if(e.getKeyCode()==KeyEvent.VK_RIGHT){
          isUp=false;
          isLeft=false;
          isDown=false;
          isRight=true;

          move++;//movement is incremented when pressing right arrow key

      }
      if(e.getKeyCode()==KeyEvent.VK_LEFT)
      {
          isUp=false;
          isLeft=true;
          isDown=false;
          isRight=false;

          move++;
      }
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            isUp=false;
            isLeft=false;
            isDown=true;
            isRight=false;

            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP)
        {
            isUp=true;
            isLeft=false;
            isDown=false;
            isRight=false;

            move++;
        }
    }

    private void restart() {
        //we need to restart the game and  put GameOvervar value as false
        GameOvervar=false;
        move=0;
        score=0;
        lengthOfSnake=3;
        isLeft=false;
        isRight=true;
        isUp=false;
        isDown=false;
        time.start();
        newfood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake;i>0;i--)
        {
            snakeX[i]=snakeX[i-1];//moving forward means increment the snakex and snakey index
            snakeY[i]=snakeY[i-1];
        }
        if(isLeft) {// when the snake move left means decreasing pxl values
            snakeX[0] = snakeX[0] - 25;
        }
        if(isRight) {
            snakeX[0] = snakeX[0] + 25;
        }
        if(isUp) {
            snakeY[0] = snakeY[0] - 25;
        }
        if(isDown) {
            snakeY[0] = snakeY[0] + 25;
        }

        if(snakeX[0]>850) snakeX[0]=25;//if the snake move outside the boundary then snake
        //should come to the next direction.
        if(snakeX[0]<25) snakeX[0]=850;
        if(snakeY[0]>625) snakeY[0]=75;
        if(snakeY[0]<75) snakeY[0]=625;
        CollisionWithfood();
        CollisionWithBody();
        repaint();


    }

    private void CollisionWithBody() {
        //In this function we check that if the body hit itself, then the game is over
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakeX[i]==snakeX[0] && snakeY[i]==snakeY[0])
            {
                time.stop();//game should stop when hit head in the body
                GameOvervar=true;
            }
        }

    }

    private void CollisionWithfood() {
        //check that if the snake head colliding with food then we need to
        //increment the size of snake & vanish the food and locate another
        //random position
        if(snakeX[0]==foodx && snakeY[0]==foody)
        {
            newfood();
            //length of snake incremented by one when colliding with food
            lengthOfSnake++;
            //when hit the food, then increment the score by one.
            score++;
            //copying the previous value
            snakeX[lengthOfSnake-1]=snakeX[lengthOfSnake-2];
            snakeY[lengthOfSnake-1]=snakeY[lengthOfSnake-2];

        }
    }

    private void newfood() {
        //choose random position for food
        foodx=xpos[random.nextInt(xpos.length-1)];
        foody=ypos[random.nextInt(ypos.length-1)];
        //if the food print over the snake in the next time
        // it become unfair , then recall newfood() again
        for(int i=lengthOfSnake-1;i>0;i--){
           if(snakeX[i]==foodx && snakeY[i]==foody){
               newfood();
           }
        }
    }
}
