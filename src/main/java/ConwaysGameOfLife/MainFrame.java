package ConwaysGameOfLife;

import javax.swing.*;
import java.awt.*;


class MainFrame {
    private int fieldSizeX;
    private int fieldSizeY;
    private int pointSize = 8;
    private int showDelay = 200;
    private GameField gameField;
    private JFrame frame;
    private ButtonPanel buttonPanel;


    void go() {
        createGame();
        while (true) {
            if (gameField.isGoing()) {
                redraw();
            } else {
                changeButtonText();
            }
            changeTitle();
            try {
                Thread.sleep(showDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createGame() {
        inputSize();
        createGameField();
        createButtonPanel();
        createFrame();
    }

    private void createGameField() {

        checkSize();
        gameField = new GameField(fieldSizeY, fieldSizeX, pointSize);
    }

    private void checkSize() {
        fieldSizeX = fieldSizeX > 9 && fieldSizeX <1000 ? fieldSizeX : 50; // default
        fieldSizeY = fieldSizeY > 9 && fieldSizeY <1000 ? fieldSizeY : 50; // default
    }

    private void changeTitle() {
        frame.setTitle("Conway's Game of Life. Turn # " + gameField.getTurn());
    }

    private void changeButtonText() {
        JButton button = (JButton) buttonPanel.getComponent(2);
        button.setText("Play");
    }

    private void createButtonPanel() {
        buttonPanel = new ButtonPanel(gameField);
    }

    private void inputSize() {
        JTextField xField = new JTextField(4);
        JTextField yField = new JTextField(4);
        JPanel myPanel = createInputSizeDialog(xField, yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter X and Y values from 10 to 999", JOptionPane.OK_CANCEL_OPTION);

        switch (result) {
            case JOptionPane.OK_OPTION:
                try {
                    fieldSizeX = Integer.parseInt(xField.getText());
                    fieldSizeY = Integer.parseInt(yField.getText());
                } catch (NumberFormatException e) {
                    inputSize();
                }
                break;
            default:
                System.exit(0);
        }

    }

    private JPanel createInputSizeDialog(JTextField xField, JTextField yField) {
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Field X size:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(10));
        myPanel.add(new JLabel("Field Y size:"));
        myPanel.add(yField);
        return myPanel;
    }

    private void redraw() {
        gameField.processOfLife();
        gameField.repaint();
    }

    private void createFrame() {
        String title = "Conway's Game of Life";
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int fieldWidth = 100 * pointSize;
        int fieldHeight = 100 * pointSize;

        int BTN_PANEL_HEIGHT = 58;
        frame.setSize(fieldWidth, fieldHeight + BTN_PANEL_HEIGHT);
        frame.setLocation(50, 50);
        frame.setResizable(true);

        final JScrollPane scrollPane = new JScrollPane(gameField);
        gameField.setPreferredSize(new Dimension(fieldSizeX * pointSize, fieldSizeY * pointSize));
        gameField.setAutoscrolls(true);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.setVisible(true);

    }

}
