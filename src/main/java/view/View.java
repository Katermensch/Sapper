package view;

import controller.Controller;
import controller.ControllerListener;
import model.Difficult;
import model.ModelListener;
import model.SapperCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class View implements ModelListener{

    private Controller controller;
    private ImageIcon one;
    private ImageIcon two;
    private ImageIcon three;
    private ImageIcon four;
    private ImageIcon five;
    private ImageIcon six;
    private ImageIcon seven;
    private ImageIcon eight;
    private ImageIcon open;
    private ImageIcon close;
    private ImageIcon mine;
    private ImageIcon flag;
    private int width;
    private int height;
    private JFrame frame;
    private JPanel field;
    private JPanel mainPanel;
    private JButton restartButton;
    private JButton[][] buttonField;
    private JLabel timer;

    public View(Controller controller) {
        this.controller = controller;
        cellIcons();
        frame = new JFrame("Sapper");

    }

    @Override
    public void setScreenSize(int width, int height) {
        frame.dispose();
        frame = new JFrame("Sapper");
        this.width = width;
        this.height = height;
        initGamePanels();
        initScreen();
    }

    @Override
    public void restartScreen(int line, int column) {
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                buttonField[i][j].setIcon(close);
                buttonField[i][j].setBackground(Color.GRAY);
            }
        }
    }

    private JMenuBar menuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game...");
        JMenuItem restartGame = new JMenuItem("Restart");
        restartGame.addActionListener(event -> controller.restartGame());
        JMenuItem difficult = new JMenuItem("Difficult");
        difficult.addActionListener(e -> showDifficultSettings());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(event -> System.exit(1));
        menuBar.add(gameMenu);
        gameMenu.add(restartGame);
        gameMenu.add(difficult);
        gameMenu.add(exit);
        return menuBar;
    }

    private void showDifficultSettings(){
        JDialog dialog = new JDialog(frame, "Difficult", true);
        dialog.setSize(520, 340);
        dialog.setLocationRelativeTo(frame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel grid = new JPanel(new GridLayout(1, 4, 5, 0));
        dialog.add(grid);
        JButton lowDifficultButton = new JButton("Low");
        lowDifficultButton.addActionListener((ControllerListener) e -> controller.setDifficult(Difficult.LOW));
        grid.add(lowDifficultButton);
        JButton mediumDifficultButton = new JButton("Medium");
        mediumDifficultButton.addActionListener((ControllerListener)e -> controller.setDifficult(Difficult.MEDIUM));
        grid.add(mediumDifficultButton);
        JButton hardDifficultButton = new JButton("Hard");
        hardDifficultButton.addActionListener((ControllerListener)e -> controller.setDifficult(Difficult.HARD));
        grid.add(hardDifficultButton);
        JButton customDifficultButton = new JButton("Custom");
        customDifficultButton.addActionListener((ControllerListener)e -> showCustomGameDialog());
        grid.add(customDifficultButton);
        dialog.setVisible(true);
        dialog.pack();
    }

    private void showCustomGameDialog() {
        JDialog dialog = new JDialog(frame, "Custom field", true);
        dialog.setSize(320, 240);
        dialog.setLocationRelativeTo(frame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel parameters = new JPanel(new GridLayout(3, 1, 5, 0));
        parameters.add(new JLabel("Width: "));
        JSpinner spinnerHeight = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        parameters.add(spinnerHeight);
        parameters.add(new JLabel("Height: "));
        JSpinner spinnerLength = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        parameters.add(spinnerLength);
        parameters.add(new JLabel("Mines: "));
        JSpinner spinnerMines = new JSpinner(new SpinnerNumberModel(1, 1, 600, 1));
        parameters.add(spinnerMines);
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e1 -> controller.setCustomLevel((Integer) spinnerHeight.getValue(), (Integer) spinnerLength.getValue(),
                (Integer) (spinnerMines.getValue())));
        grid.add(nextButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e12 -> dialog.dispose());
        grid.add(cancelButton);
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(grid);
        JPanel flow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        flow1.add(parameters);
        Container container = dialog.getContentPane();
        container.add(flow, BorderLayout.SOUTH);
        container.add(flow1, BorderLayout.NORTH);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void cellIcons(){
        close = findIcon(this.getClass().getResource("/icons/close.png"));
        open = findIcon(this.getClass().getResource("/icons/open.png"));
        mine = findIcon(this.getClass().getResource("/icons/bomb.png"));
        flag = findIcon(this.getClass().getResource("/icons/flag.png"));
        one = findIcon(this.getClass().getResource("/icons/1.png"));
        two = findIcon(this.getClass().getResource("/icons/2.png"));
        three = findIcon(this.getClass().getResource("/icons/3.png"));
        four = findIcon(this.getClass().getResource("/icons/4.png"));
        five = findIcon(this.getClass().getResource("/icons/5.png"));
        six = findIcon(this.getClass().getResource("/icons/6.png"));
        seven = findIcon(this.getClass().getResource("/icons/7.png"));
        eight = findIcon(this.getClass().getResource("/icons/8.png"));
    }

    private ImageIcon findIcon(URL imageString) {
        ImageIcon imageIcon = new ImageIcon(imageString);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }

    private void addCellButtons() {
        MyMouseAdapter l = new MyMouseAdapter();
        field = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        buttonField = new JButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buttonField[i][j] = new JButton(close);
                buttonField[i][j].setPreferredSize(new Dimension(21, 21));
                buttonField[i][j].setName(i + " " + j);
                buttonField[i][j].addMouseListener(l);
                gbc.fill = GridBagConstraints.NONE;
                gbc.gridx = i;
                gbc.gridy = j;
                gbc.anchor = GridBagConstraints.CENTER;
                field.add(buttonField[i][j], gbc);
            }
        }
    }

    private void initScreen() {
        JPanel menu = new JPanel();
        menu.add(menuBar());
        //frame.add(menuBar(), BorderLayout.NORTH);
        frame.add(menu, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(field, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void initGamePanels() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        restartButton = new JButton("Restart");
        restartButton.addActionListener(event -> controller.restartGame());
        c.anchor = GridBagConstraints.PAGE_START;
        mainPanel.add(restartButton, c);
        timer = new JLabel("TIMER: ");
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        mainPanel.add(timer, c);
        addCellButtons();
    }

    @Override
    public void setCellMineNearNeighbors(int width, int height, int countNeighbors) {
        ImageIcon icon;
        switch (countNeighbors) {
            case 1:
                icon = one;
                break;
            case 2:
                icon = two;
                break;
            case 3:
                icon = three;
                break;
            case 4:
                icon = four;
                break;
            case 5:
                icon = five;
                break;
            case 6:
                icon = six;
                break;
            case 7:
                icon = seven;
                break;
            case 8:
                icon = eight;
                break;
            default:
                icon = new ImageIcon("open.png");
                break;
        }
        buttonField[width][height].setIcon(icon);
    }

    @Override
    public void setCellOpened(int width, int height) {
        buttonField[width][height].setIcon(open);
    }

    @Override
    public void setCellFlag(int width, int height) {
        buttonField[width][height].setIcon(flag);
    }

    @Override
    public void setCellDefault(int width, int height) {
        buttonField[width][height].setIcon(close);
    }

    @Override
    public void setCellMine(int width, int height) {
        buttonField[width][height].setIcon(mine);
        buttonField[width][height].setBackground(Color.RED);
    }

    @Override
    public void Win() {
        controller.stopTimer();
        String massage = "Все мины обезврежены!";
        String title = "Победа!";
        JOptionPane.showMessageDialog(null, massage, title, JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void GameOver() {
        controller.stopTimer();
        String massage = "You Lose!";
        String title = "BOOM!";
        JOptionPane.showMessageDialog(null, massage, title, JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void openAllMine(SapperCell[][] gameField) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (gameField[i][j].isMine()) {
                    buttonField[i][j].setIcon(mine);
                }
            }
        }
    }

    @Override
    public void setGameTime(String seconds) {
        timer.setText("TIME: " + seconds);
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Component component = e.getComponent();
            String name = component.getName();
            int x = Integer.parseInt(name.split(" ")[0]);
            int y = Integer.parseInt(name.split(" ")[1]);
            if (e.getButton() == 1) {
                controller.leftClick(x, y);
            } else {
                controller.rightClick(x, y);
            }
        }
    }
}