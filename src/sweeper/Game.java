package sweeper;

public class Game {
    private Bomb bomb;
    private Flag flag;



    private GameState state;


    public Game(int cols, int rows, int bombs){

        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord){ //получение координат

        if (Box.OPENED == flag.get(coord))//проверяем что сверху
            return bomb.get(coord);//возвращаем, то что находится под ней
        else
            return flag.get(coord);
 }

    public void pressLeftButton (Coord coord){
        if (isGameOver()) return;
            openBox(coord);
            checkWinner();

    }

    public void pressRightButton (Coord coord){
        if (isGameOver()) return;
            flag.toggleFlagedToBox(coord);
        }

    public GameState getState() {
        return state;
    }

    public int getTotalBombs(){ //вернуть кол-во бомб
        return bomb.getTotalBombs();
    }
    public int getTotalFlaged(){ //вернуть кол-во флагов
        return flag.getTotalFlaged();
    }

    private boolean isGameOver(){
        if (GameState.PLAYED != state){
            start();
            return true;
        }
        return false;
    }

    private void checkWinner(){
        if (GameState.PLAYED == state)
            if (flag.getTotalClosed() == bomb.getTotalBombs()){
                state = GameState.WINNER;
                flag.setFlagedToLastClosedBoxes();
            }
    }


    private void openBox(Coord coord) {  //все возможные действия при открытии клетки
        switch (flag.get(coord))
        {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); break;
            case FLAGED: break;
            case CLOSED:
                switch (bomb.get(coord)){ //проверяем нижний слой
                    case ZERO: openBoxesAroundZero(coord); break;
                    case BOMB: openBombs(coord); break;
                    default: flag.setOpenedtoBox(coord); break; //все остальные варианты

                }
        }
  }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (Box.BOMB != bomb.get(coord))
            if (bomb.get(coord).getNumber() == flag.getCountOfFlagedBoxesAround(coord))
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coord bombedCoord){
        flag.setBombedToBox(bombedCoord);
        for (Coord coord : Ranges.getAllCords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBox(coord);
            else
                flag.setNobombToFlagetBox(coord);

        state = GameState.BOMBED;
    }

    private void openBoxesAroundZero(Coord coord){ //открывать все пустые клетки
        System.out.println(coord.x + " " + coord.y);
        flag.setOpenedtoBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

}
