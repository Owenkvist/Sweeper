import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box; //доступ к списку ВОХ
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper  extends JFrame {
    private Game game;

    private final int COLS = 9; //количество столбцов
    private final int ROWS = 9; //кол-во строк
    private final int BOMBS = 10; //кол-во бомб
    private final int IMAGE_SIZE = 50; //для удобства создаем переменную с размером 50
    private JPanel panel;
    private JLabel label; //текущее состояние игры

    public static void main(String[] args) {
    new JavaSweeper();
    }
    private JavaSweeper(){
        game = new Game (COLS, ROWS, BOMBS );
        game.start(); //запускаем игру
        setImages(); //инициализация всех картинок
        initPanel();
        initLabel();
        initFrame();

    }

    private void initLabel() {
        label = new JLabel(getMassege());
        Font font = new Font("Arial", Font.BOLD, 20);//установка шрифта
        label.setFont (font);
        add (label, BorderLayout.SOUTH); //размещения надписи внизу


    }


    private void initPanel() {
        panel= new JPanel(){ //инициализируем поле panel


            @Override
            protected void paintComponent(Graphics g) { //метод для рисования на экране
                super.paintComponent(g);
                for (Coord coord: Ranges.getAllCords())  //перебираем все значаения координат
                                    g.drawImage((Image) game.getBox(coord).image,
                                    coord.x * IMAGE_SIZE,
                                    coord.y * IMAGE_SIZE, this); //рисуем картинку, box.ordinal()текущий номер в списке для элемента

            }
        };

        panel.addMouseListener(new MouseAdapter() { //код для добавления слушателя мышки
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;//координаты куда мы нажали
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                switch (e.getButton()){
                    case MouseEvent.BUTTON1 : game.pressLeftButton(coord); break;
                    case MouseEvent.BUTTON3 : game.pressRightButton(coord); break;
                    case MouseEvent.BUTTON2 : game.start(); break;
                    }
                label.setText (getMassege());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAGE_SIZE,
                Ranges.getSize().y *IMAGE_SIZE)); //размер панели
        add(panel); //добавить панель
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //закрытие при нажатии на крестик
        setTitle("Java Sweeper"); //название приложения
        setResizable(false); //заперет на изменение размера
        pack(); //применить изменения
        setVisible(true); //для визуализации панели
        setLocationRelativeTo(null); //расположение панели по центру
    }

    private void setImages(){  //установка картинки в каждый экзепляр перечисления ВОХ
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
        setIconImage(getImage("icon")); //устнановка иконки приложения

    }
    private Image getImage(String name){ //для отображения картинки
        String filename = "img/" + name + ".png";// создадим переменную с указанием пути к картинкам
        ImageIcon icon = new ImageIcon(getClass().getResource(filename) ); //подключение к папке с картинками
        return icon.getImage();
    }

    private String getMassege() { //получить текущее состояние
    switch (game.getState()){
        case BOMBED: return "F*ck you Looser!!!";
        case WINNER: return "Not bad!";
        case PLAYED:
        default: if (game.getTotalFlaged() == 0)
            return "Welcome!";
                else
                    return "Think twice! Flagged " +
                            game.getTotalFlaged() + " of " +
                            game.getTotalBombs() + " bombs.";

    }
    }
}
