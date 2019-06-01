package sweeper;

 class Flag {

    private Matrix flagMap;



    private int totalFlaged; //всего флагов
    private int totalClosed; //всего закрытых ячеек

    void start(){ //создание матрицы

        flagMap = new Matrix(Box.CLOSED); //начальное состояние у поля закрыто
        totalFlaged = 0;
        totalClosed = Ranges.getSquare();


    }

    Box get (Coord coord){
        return flagMap.get(coord);
    }

     void setOpenedtoBox(Coord coord) {
        flagMap.set (coord, Box.OPENED);
        totalClosed --;
     }

      int getTotalFlaged() {
         return totalFlaged;
     }

      int getTotalClosed() {
         return totalClosed;
     }



     private void setFlagedToBox(Coord coord) {
         flagMap.set (coord, Box.FLAGED);
         totalFlaged ++;
     }

     private void setClosedToBox(Coord coord) {
         flagMap.set (coord, Box.CLOSED);
         totalFlaged --;
     }


     void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){ //проверка ячейки
            case FLAGED: setClosedToBox(coord); break; //если стоит флаг, то закрыть
            case CLOSED: setFlagedToBox(coord); break; //если закрыта, то поставить флаг

        }
     }

     void setFlagedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCords())
            if (Box.CLOSED == flagMap.get(coord))
                setFlagedToBox(coord);
     }

     void setBombedToBox(Coord coord) {
        flagMap.set (coord, Box.BOMBED );
     }

      void setOpenedToClosedBox(Coord coord) { //открыть закрытые бомбы
        if (Box.CLOSED == flagMap.get(coord))
            flagMap.set(coord, Box.OPENED);
     }

     void setNobombToFlagetBox(Coord coord) { //поставить значок нет бомб, если на поле стоит флаг
        if (Box.FLAGED == flagMap.get(coord))
            flagMap.set(coord, Box.NOBOMB);
     }

     int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count++;
            return count;
     }
 }
