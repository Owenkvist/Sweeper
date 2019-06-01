package sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges { //класс для создания различных параметров и перебора координат
    static private Coord size; //для координат
    static private ArrayList<Coord> allCords; //список всех координат
    static private Random random = new Random(); //генератор случайных чисел

    static void setSize(Coord size){
        Ranges.size = size;
        allCords = new ArrayList<Coord>();
        for (int x = 0; x < size.x; x ++)
            for (int y = 0; y < size.y; y ++)//перебираем все координаты х и у
                allCords.add(new Coord(x,y));
    }


    public static Coord getSize(){
        return size;
    }

    public static ArrayList<Coord> getAllCords(){
        return allCords;
    }

    static boolean inRange (Coord coord)//проверяем находится ли коорлинаты в пределах игрового поля
    {
        return coord.x >= 0 && coord.x < size.x &&
               coord.y >= 0 && coord.y < size.y;
    }

    static Coord getRandomCoord(){
        return new Coord(random.nextInt(size.x), random.nextInt(size.y)); //координаты с ограничением
    }

    static ArrayList<Coord> getCoordsAround (Coord coord){ //создаем метод для перебора клеток вокруг бомб
        Coord  around;
        ArrayList<Coord> list = new ArrayList<Coord>(); //список клеток вокруг
        for (int x = coord.x - 1; x <= coord.x +1; x++)
            for (int y = coord.y - 1; y <= coord.y +1; y++)
                if (inRange(around = new Coord(x,y))) //проверка находятся ли координаты в пределах поля
                    if (!around.equals(coord)) //проверка не находятся ли координаты, там где бомба
                        list.add(around);
                    return list;


    }


    static int getSquare() {
        return size.x * size.y;
    }
}

