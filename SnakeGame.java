import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    JFrame jFrame;
    SnakeGame(){
        jFrame=new JFrame("Snake Game");
        jFrame.setBounds(10,10,905,780);
        MyPanel myPanel=new MyPanel();
        myPanel.setBackground(Color.gray);
        jFrame.add(myPanel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public static void main(String[] args){
        SnakeGame snakeGame=new SnakeGame();
    }
}
