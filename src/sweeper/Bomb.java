package sweeper;

class Bomb {
    private Matrix bombMap; //матрица для хранения бомб
    private int totalBombs; //кол-во бомб

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int j =0; j < totalBombs; j++)
        placeBomb();
    }

    Box get(Coord coord){
        return bombMap.get(coord);
    }

    int getTotalBombs(){
        return totalBombs;
    }

    private void fixBombsCount(){ //максимальное кол-во бомб равное площади окна

        int maxBombs = Ranges.getSize().x * Ranges.getSize().y /2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;

    }
    private void placeBomb(){ //метод для размещения бомб
        while(true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord)) //если в ячейке есть бомба, ищем другую координату
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    private void incNumbersAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord)) //обработать все клетки вокруг бомб
            if (Box.BOMB !=bombMap.get(around))//проверка, что в клетке нет бомб
            bombMap.set (around, bombMap.get(around).nextNumberBox()); //утановка цифр вокруг бомб
    }
}
