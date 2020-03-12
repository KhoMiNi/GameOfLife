package ConwaysGameOfLife;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonPanel extends JPanel {

    ButtonPanel(final GameField gameField) {
        JButton fillButton = new JButton("Fill");
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameField.fillField();
                gameField.setTurn(0);
                gameField.repaint();
            }
        });
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameField.processOfLife();
                gameField.repaint();

            }
        });
        final JButton goButton = new JButton("Play");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameField.setGoing(!gameField.isGoing());
                goButton.setText(gameField.isGoing() ? "Stop" : "Play");
            }
        });
        this.add(fillButton);
        this.add(stepButton);
        this.add(goButton);
    }
}
