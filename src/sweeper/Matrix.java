package sweeper;

import javax.naming.ldap.ManageReferralControl;

class Matrix {
    private Box[] [] matrix;
    Matrix (Box box){
        matrix = new Box [Ranges.getSize().x] [Ranges.getSize().y]; //создаем матрицу
        for (Coord coord : Ranges.getAllCords()) //перебираем все координаты
            matrix [coord.x] [coord.y] = box;//заполняем координатами матрицу
}
        Box get (Coord coord){
        if (Ranges.inRange(coord))
        return matrix[coord.x] [coord.y];
        return null;
        }

    void set (Coord coord, Box box){
        if (Ranges.inRange(coord))
            matrix[coord.x] [coord.y] = box;

    }
}
