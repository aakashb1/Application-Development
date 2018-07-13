/**
 * Whack a mole game.
 * @author Aakash Bhatia
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Defining Game.
 */
public class Game implements ActionListener {
    /**
     * Defining dimension of for start buttons and components.
     */
    private Dimension dim = new Dimension(150, 40);
    /**
     * Defining an array of buttons.
     */
    private JButton[] buttons;
    /**
     * Defining start button.
     */
    private JButton start;
    /**
     * Defining timer textfield.
     */
    private JTextField timer;
    /**
     * Defining array of threads for each button pop.
     */
    private Thread[] pop;
    /**
     * Defining textfield for score.
     */
    private JTextField scorefield;
    /**
     * Defining a global variable for calculating the timeleft.
     */
    private static long timeleft;
    /**
     * Defining score.
     */
    private int score;
    /**
     * Constructor where JFrame and other components are instantiated.
     */
    public Game() {
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);
        // Step 1: Create a window
        JFrame window = new JFrame("Whack-a-mole");
        window.setSize(900, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Step 2: Create a container
        JPanel master = new JPanel();
        JPanel pane1 = new JPanel(new FlowLayout());
        // Step 3: Create the components of a container
        start = new JButton("Start");
        start.setSize(dim);
        start.addActionListener(this);
        pane1.add(start);
        timer = new JTextField(7);
        timer.setEditable(false);
        addComponent(pane1, new JLabel("Time Left"), timer);
        scorefield = new JTextField(7);
        scorefield.setEditable(false);
        addComponent(pane1, new JLabel("Score"), scorefield);
        JPanel pane2 = new JPanel();
        pane2.setLayout(new GridLayout(8, 8, 7, 7));
        buttons = new JButton[64];
        for (int i = 0; i < buttons.length; i++) {
            // set every button to default state (neither walk nor stop)
            buttons[i] = new JButton("   ");
            buttons[i].setBackground(Color.LIGHT_GRAY);
            buttons[i].setPreferredSize(new Dimension(100, 40));
            buttons[i].setFont(font);
            buttons[i].setOpaque(true);
            pane2.add(buttons[i]);
        }
        master.add(pane1);
        master.add(pane2);
        // Step 4: Display the container
        window.setContentPane(master);
        window.setVisible(true);
    }
    /**
     * Method to add two components together.
     * @param pane pane in which the components are to added.
     * @param item component 1.
     * @param itemfield component 2.
     */
    public void addComponent(JPanel pane, JComponent item, JComponent itemfield) {
        pane.add(Box.createHorizontalStrut(20));
        pane.add(item);
        pane.add(Box.createHorizontalStrut(10));
        itemfield.setMaximumSize(dim);
        pane.add(itemfield);
        pane.add(Box.createHorizontalStrut(10));
        return;
    }
    /**
     * Method to be invoked when start button is clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        pop = new Thread[buttons.length];
        score = 0;
        scorefield.setText(Integer.toString(score));
        Thread fortime = new TimerThread(start, timer);
        fortime.start();
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setEnabled(false);
            pop[i] = new HelperThread(buttons[i], Color.GREEN, ":-)");
            pop[i].start();
            buttons[i].putClientProperty("firstIndex", null);
            buttons[i].putClientProperty("firstIndex", new Integer(i));
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttons[index].setEnabled(false);
                    score = score + 1;
                    buttons[index].setText(":-(");
                    scorefield.setText(Integer.toString(score));
                }
            });
        }
    }
    /**
     * inner class that extends Thread.
     */
    private class HelperThread extends Thread {
        // unique state (instance variables) per instance
        /**
         * Defining local variable myButtons.
         */
        private JButton myButtons;
        /**
         * Defining local variable myColor.
         */
        private Color myColor;
        /**
         * Defining local variable myText.
         */
        private String myText;
        /**
         * Creating new instance of random to generate random number.
         */
        private Random random = new Random();
        /**
         * Defining local variable light.
         */
        private JButton light;
        /**
         * Constructor.
         * @param button array of buttons.
         * @param color color.
         * @param text text.
         */
        private HelperThread(JButton button, Color color, String text) {
            myButtons = button;
            myColor = color;
            myText = text;
        }
        /**
         * Implement run method of Thread class.
         */
        @Override
        public void run() {
            try {
                int rand1 = random.nextInt(5000);
                Thread.sleep(rand1);
                // long-running task
                while (timeleft > 0) {
                    light = myButtons;
                    int rand = random.nextInt(4000 - 1000 + 1) + 1000;
                    light.setEnabled(true);
                    light.setText(myText);
                    light.setBackground(myColor);
                    Thread.sleep(rand); // to keep button up for a random time between 1 and 4 seconds
                    down(light); // to disable the button.
                    Thread.sleep(2000); // to disable the thread of button for 2 seconds.
                }
                // removing action listeners
                for (JButton b: buttons) {
                    for (ActionListener a1 : b.getActionListeners()) {
                        b.removeActionListener(a1);
                    }
                }
                // setting all buttons to down when timeleft < = 0.
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setText(null);
                    buttons[i].setBackground(Color.LIGHT_GRAY);
                }
            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }
        }
        /**
         * method to disable buttons.
         * @param light1 JButton.
         */
        public void down(JButton light1) {
            light1.setEnabled(false);
            light1.setBackground(Color.LIGHT_GRAY);
            light1.setText(null);
        }
    }
    /**
     * inner class that extends Thread for calculating time.
     */
    private class TimerThread extends Thread {
        // unique state (instance variables) per instance
        /**
         * Defining array of threads for each button pop.
         */
        private JButton start;
        /**
         * Defining textfield for stopwatch.
         */
        private JTextField stopwatch;
        /**
         * Constructor.
         * @param myButton array of buttons.
         * @param timer1 counts the timer.
         */
        private TimerThread(JButton myButton, JTextField timer1) {
            start = myButton;
            stopwatch = timer1;
        }
        /**
         * Implement run method of Thread class.
         */
        @Override
        public void run() {
            //long now = System.currentTimeMillis();
            timeleft = 20;
            stopwatch.setText(Long.toString(timeleft));
            start.setEnabled(false);
            while (timeleft > 0) {
                try {
                    Thread.sleep(1000);
                    timeleft--;
                    stopwatch.setText(Long.toString(timeleft));
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }
            if (timeleft <= 0) {
                try {
                    // long-running task
                    while (true) {
                        Thread.sleep(5000);
                        start.setEnabled(true);
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }
        }
    }
    /**
     * Main method that instantiates GUI Object.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Game();
    }
}
