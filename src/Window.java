import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by  Victoria on 01.11.2015.
 */
public class Window {

    private static int rows;
    private static int columns;
    private static JPanel pixelPanel;
    private static ArrayList<Integer> out;
    private static Net net;
    private static JTextField speed;
    private static JTextField resultField;

    public static int getAmount() {
        return rows*columns;
    }

    public static ArrayList<Integer> input() {
        return new ArrayList<Integer>(out);
    }

    public static void main(String[] args) {
        startGUI();
    }

    public static void startGUI() {

        JFrame mainFrame= new JFrame("Распознавание чиcел. Щукина Виктория");

        rows=5;
        columns=3;
        pixelPanel= new JPanel(new GridLayout(rows,columns));
        out=new ArrayList<>();
        for (int i=0; i< rows*columns; i++){
            JButton x = new JButton();
            x.addActionListener(new ButtonListener());
            x.setBackground(Color.LIGHT_GRAY);
            x.setName(i+"");
            out.add(0);
            pixelPanel.add(x);
        };
        pixelPanel.setPreferredSize(new Dimension(400, 1));

        JLabel speedLabel= new JLabel("Скорость обучения:");
        speed= new JTextField("0.1");
        speed.setPreferredSize(new Dimension(10, 20));

        JButton recognize= new JButton("Распознать ");
        recognize.addActionListener(new RecognizeListener());

        JButton teach=new JButton("Обучить       ");
        teach.addActionListener(new TeachListener());

        JLabel result= new JLabel("Результат:");
        resultField=new JTextField("");

        Box box = new Box(BoxLayout.Y_AXIS);
        //recognize.setPreferredSize();
        box.add(recognize);
        box.add(teach);
        box.add(speedLabel);
        box.add(speed);
        box.add(result);
        box.add(resultField);
        JPanel y=new JPanel();
        y.setPreferredSize(new Dimension(1,1000));
        box.add(y);
        //box.setPreferredSize(new Dimension(150,0));

        mainFrame.getContentPane().add(pixelPanel,BorderLayout.WEST);
        mainFrame.getContentPane().add(box,BorderLayout.EAST);
        mainFrame.setSize(600,600);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        net=new Net();
    }
    private static class RecognizeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            resultField.setText(net.recognize(input())+"");
        }
    }

    private static class TeachListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            net.correctAllNeurons(resultField.getText().toCharArray()[0], Double.parseDouble(speed.getText()), input());

        }
    }

    private static class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton test=(JButton)e.getSource();
            if (test.getBackground().equals(Color.CYAN)){
                test.setBackground(Color.LIGHT_GRAY);
                out.set(Integer.parseInt(test.getName()), 0);
            }
            else {
                test.setBackground(Color.CYAN);
                out.set(Integer.parseInt(test.getName()), 1);
            }

        }
    }

}
