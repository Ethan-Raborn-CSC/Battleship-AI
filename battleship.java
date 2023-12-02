import java.util.Scanner;
import java.util.Random;

public class battleship{
    public static void main(String args[]){

        Scanner kin = new Scanner(System.in);

        //startup and option select
        int startupOption;
        System.out.println("Hello! Welcome to Battleship! Select the options below");
        System.out.printf("Select options 1, 2, or 3:%n\t1) Single Board (For Developer Debugging)%n\t2) Multiplayer%n\t3) Play against AI%n\t4) Watch AI play against eachother\n\t5) quit%n%nSelection: ");
        String optin;
        boolean check = false;
        do{
            optin = kin.nextLine();
            
            switch (optin) {
                case "1":
                case "2":
                case "3":
                case "4":
                check = false;                    
                    break;
                case "5":
                    return;
                default:
                    System.out.printf("Did not understand selection. Select again.\n\tSelection:");
                    check = true;
                    break;
            }
        }while(check);
        
        int selection = Integer.parseInt(optin);
        
        //run game based on selection
        switch(selection){
            case 1:
                singleTableRun();
                break;
            case 2:
                multiplayerRun();
                break;
            case 3:
                playAgainstAI();
                break;
            case 4:
                watchAI();
                break;
            case 5:
                return;
        }
    }

    public static void singleTableRun(){
        Scanner kin = new Scanner(System.in);

        boolean randomPlacement = false;
        String input;
        System.out.printf("Would you like to choose your ship placement?(y/n) (yes by default)\n\t>: ");
        input = kin.nextLine();
        input = input.toLowerCase();
        if(input.charAt(0) == 'n')
            randomPlacement = true;
        shipyard shipsO = new shipyard(createNewTable());
        boolean [][] sea = createNewTable();
        //boolean [][] ships = createNewTable();

        if(randomPlacement)
            randomShipPlacer(shipsO);
        else
            shipSelection(shipsO);

        printSingle(shipsO, sea);
        boolean quit = false;
        int x = 0;
        int y = 0;
        while(true)
        {
            System.out.printf("Give firing coordinate or input q to quit.\nx: ");

            input = kin.nextLine();
            input = input.toLowerCase();
            if(input.charAt(0) == 'q')
                return;
            x = Integer.parseInt(input);
            System.out.printf("y: ");
            input = kin.nextLine();
            input = input.toLowerCase();
            if(input.charAt(0) == 'q')
                return;
            y = Integer.parseInt(input);


            if((x >= 0 && x <=9) && (y >= 0 && y <= 9))
            {
                sea[x][y] = true;
                printSingle(shipsO, sea);
            }
            else
                System.out.printf("Coordinates out of bound!\n");

        }
    }


    public static void multiplayerRun(){
        //System.out.println("Multiplayer");
        Scanner kin = new Scanner(System.in);
        Random rand = new Random();

        boolean randomPlacement = false;
        String input;

        //player1 ship placement
        System.out.printf("Would you like to choose your ship placement, Player1?(y/n) (yes by default)\n\t>: ");
        input = kin.nextLine();
        input = input.toLowerCase();
        if(input.charAt(0) == 'n')
            randomPlacement = true;
        
        boolean [][] sea1 = createNewTable();
        battleship.shipyard ships1 = new shipyard(createNewTable());
        boolean acc1, bts1, sub1, spd1;
        acc1 = true;
        bts1 = true;
        sub1 = true;
        spd1 = true;

        if(randomPlacement)
        {
            randomShipPlacer(ships1);
            //printShips(ships1.getGrid());
        }
        else
        {
            System.out.printf("Begin Ship Selection for Player1!\n");
            shipSelection(ships1);
        }
        System.out.printf("Player1 Ship placement complete.\n");

        //Player 2 ship placement
        System.out.printf("Ready Player2? Would you  like to choose your ship placement?(y/n) (yes by default)\n\t>: ");
        randomPlacement = false;
        input = kin.nextLine();
        input = input.toLowerCase();
        if(input.charAt(0) == 'n')
            randomPlacement = true;
        
        boolean [][] sea2 = createNewTable();
        shipyard ships2 = new shipyard(createNewTable());
        boolean acc2, bts2, sub2, spd2;
        acc2 = true;
        bts2 = true;
        sub2 = true;
        spd2 = true;

        if(randomPlacement)
        {
            randomShipPlacer(ships2);
           //printShips(ships2.getGrid());
        }
        else
        {
            System.out.printf("Begin Ship Selection for Player2!\n");
            shipSelection(ships2);
        }
        

        //randomly decide who goes first
        int first;
        if(rand.nextBoolean())
            first = 1;
        else
            first = 2;
        System.out.printf("It has been randomly decided that Player%d will go first!\nPlayer%d, when you're ready, press return.\n", first, first);
        kin.nextLine();

        //print out initial Board
        if(first == 1)
        {
            System.out.printf("Player1, here is your initial board.\n");
            printMuliplayer(ships1, sea1, ships2, sea2);
            System.out.printf("Press return before you hand off to player 2!");
            kin.nextLine();
            for(int i = 0; i < 50; i++)
                System.out.println();
            System.out.printf("Player2, press return to see your initial board.");
            kin.nextLine();
            printMuliplayer(ships2, sea2, ships1, sea1);
            System.out.printf("Press return when done and hand off to player 1.");
            //System.out.printf("-P1-\nACC R: %d\nx: %d\ny: %d\n-P2-\nACC R: %d\nx: %d\ny: %d\n\n", ships1.getAircraftCarrier()[0], ships1.getAircraftCarrier()[1], ships1.getAircraftCarrier()[2], ships2.getAircraftCarrier()[0], ships2.getAircraftCarrier()[1], ships2.getAircraftCarrier()[2]);
            kin.nextLine();
            for(int i = 0; i < 50; i++)
                System.out.println();
        }
        else
        {
            System.out.printf("Player2, here is your initial board.\n");
            printMuliplayer(ships2, sea2, ships1, sea1);
            System.out.printf("Press return before you hand off to player 2!");
            kin.nextLine();
            for(int i = 0; i < 50; i++)
                System.out.println();
            System.out.printf("Player1, press return to see your initial board.");
            kin.nextLine();
            printMuliplayer(ships1, sea1, ships2, sea2);
            System.out.printf("Press return when done and hand off to player 2.");
            kin.nextLine();
            for(int i = 0; i < 50; i++)
                System.out.println();
        }

        

        int x = 0;
        int y = 0;
        int[] tester;
        boolean idiot;

        boolean unfinished = true;
        while(unfinished)
        {
            idiot = true;

            if(first == 1)
            {
                System.out.printf("Press return when ready, Player1.\n");
                kin.nextLine();

                printMuliplayer(ships1, sea1, ships2, sea2);
                System.out.printf("Ready to fire! Keep all x and y coordinates between 0 and 9(inclusive)!\n");
                while(idiot)
                {
                    System.out.printf("x: ");
                    x = kin.nextInt();
                    System.out.printf("y: ");
                    y = kin.nextInt();

                    if((x >=0 && x < 10) && (y >= 0 && y < 10))
                        idiot = false;
                    else
                        System.out.printf("\nCoordinate out of bounds! Try again.\n");
                }

                sea1[x][y] = true;
                unfinished = checkRemainingShips(sea1, ships2);
                if(unfinished)
                {
                    if(ships2.getGrid()[x][y])
                    {
                        System.out.printf("Hit!\n");
                        tester = returnSunken(ships2, sea1);
                        if(acc1)
                        {
                            if(tester[0] == 5)
                            {
                                acc1 = false;
                                System.out.printf("\nYou've sunk the Aircraft Carrier!\n\n xx\n xxx\n\n");
                            }
                        }
                        if(bts1)
                        {
                            if(tester[1] == 4)
                            {
                                bts1 = false;
                                System.out.printf("\nYou've sunk the Battleship!\n\n xxxx\n\n");
                            }
                        }
                        if(sub1)
                        {
                            if(tester[2] == 3)
                            {
                                sub1 = false;
                                System.out.printf("\nYou've sunk the Submarine!\n\n xxx\n\n");
                            }
                        }
                        if(spd1)
                        {
                            if(tester[3] == 2)
                            {
                                spd1 = false;
                                System.out.printf("\nYou've sunk the Speedboat!\n\n xx\n\n");
                            }
                        }
                        

                        
                        System.out.printf("\nShips left: \n");
                        if(acc1)
                            System.out.printf("\tAircraft Carrier\n");
                        if(bts1)
                            System.out.printf("\tBattleship\n");
                        if(sub1)
                            System.out.printf("\tSubmarine\n");
                        if(spd1)
                            System.out.printf("\tSpeedBoat\n");
                        
                        
                        System.out.printf("Press return and then hand over to player 2!");
                    }
                    else
                    {
                        System.out.printf("Miss!\n");
                        System.out.printf("\nShips left: \n");
                        if(acc1)
                            System.out.printf("\tAircraft Carrier\n");
                        if(bts1)
                            System.out.printf("\tBattleship\n");
                        if(sub1)
                            System.out.printf("\tSubmarine\n");
                        if(spd1)
                            System.out.printf("\tSpeedBoat\n");
                        System.out.printf(" Press return and then hand over to player 2!");
                        
                    }

                    kin.nextLine();
                    kin.nextLine();
                    for(int i = 0; i < 50; i++)
                        System.out.println();
                }

                
                

                
                first = 2;
            }
            else
            {
                System.out.printf("Press return when ready, Player2.\n");
                kin.nextLine();

                printMuliplayer(ships2, sea2, ships1, sea1);
                System.out.printf("Ready to fire! Keep all x and y coordinates between 0 and 9(inclusive)!\n");
                while(idiot)
                {
                    System.out.printf("x: ");
                    x = kin.nextInt();
                    System.out.printf("y: ");
                    y = kin.nextInt();

                    if((x >=0 && x < 10) && (y >= 0 && y < 10))
                        idiot = false;
                    else
                        System.out.printf("\nCoordinate out of bounds! Try again.\n");
                }

                sea2[x][y] = true;
                unfinished = checkRemainingShips(sea2, ships1);
                if(unfinished)
                {
                    if(ships1.getGrid()[x][y])
                    {
                        System.out.printf("Hit!\n");
                        tester = returnSunken(ships1, sea2);
                        if(acc2)
                        {
                            if(tester[0] == 5)
                            {
                                acc2 = false;
                                System.out.printf("\nYou've sunk the Aircraft Carrier!\n\n xx\n xxx\n\n");
                            }
                        }
                        if(bts2)
                        {
                            if(tester[1] == 4)
                            {
                                bts2 = false;
                                System.out.printf("\nYou've sunk the Battleship!\n\n xxxx\n\n");
                            }
                        }
                        if(sub2)
                        {
                            if(tester[2] == 3)
                            {
                                sub2 = false;
                                System.out.printf("\nYou've sunk the Submarine!\n\n xxx\n\n");
                            }
                        }
                        if(spd2)
                        {
                            if(tester[3] == 2)
                            {
                                spd2 = false;
                                System.out.printf("\nYou've sunk the Speedboat!\n\n xx\n\n");
                            }
                        }
                        
                        System.out.printf("\nShips left: \n");
                        if(acc2)
                            System.out.printf("\tAircraft Carrier\n");
                        if(bts2)
                            System.out.printf("\tBattleship\n");
                        if(sub2)
                            System.out.printf("\tSubmarine\n");
                        if(spd2)
                            System.out.printf("\tSpeedBoat\n");
                        
                        
                        System.out.printf("Press return and then hand over to player 1!");
                    }
                    else
                    {
                        System.out.printf("Miss!\n");
                        System.out.printf("\nShips left: \n");
                        if(acc2)
                            System.out.printf("\tAircraft Carrier\n");
                        if(bts2)
                            System.out.printf("\tBattleship\n");
                        if(sub2)
                            System.out.printf("\tSubmarine\n");
                        if(spd2)
                            System.out.printf("\tSpeedBoat\n");
                        System.out.printf(" Press return and then hand over to player 1!");
                        
                    }
                        
                    kin.nextLine();
                    kin.nextLine();
                    for(int i = 0; i < 50; i++)
                        System.out.println();
                }

                


                first = 1;
            }
        }

        //end of game!
        if(first == 2)
        {
            System.out.printf("Hit! That's it! Good job Player1, You Won!\n Here is the your final board!\n");
            printMuliplayer(ships1, sea1, ships2, sea2);
        }
        else
        {
            System.out.printf("Hit! That's it! Good job Player2, You Won!\n Here is the your final board!\n");
            printMuliplayer(ships2, sea2, ships1, sea1);
        }
        System.out.printf("Press return 5 times to quit.");
        kin.nextLine();
        kin.nextLine();
        kin.nextLine();
        kin.nextLine();
        kin.nextLine();
    }


    public static void playAgainstAI(){
        Scanner kin = new Scanner(System.in);

        //System.out.println("Play against AI");
        System.out.printf("You've chosen to play against an AI!\nChoose which AI you'd like to play against:\n\t1) Random Ranger\n\t2) Hunt and Target\n\t3) Paridy\n\t4) Heat Map\n\t5) quit\nNote: Higher numbers are typically more effective at playing the game!\n\n\t\tSelection: ");

        String optin;
        boolean check = false;
        do{
            optin = kin.nextLine();
            
            switch (optin) {
                case "1":
                case "2":
                case "3":
                case "4":
                check = false;                    
                    break;
                case "5":
                    return;
                default:
                    System.out.printf("Did not understand selection. Select again.\n\tSelection:");
                    check = true;
                    break;
            }
        }while(check);
        
        int selection = Integer.parseInt(optin);
        
        //run game based on selection
        switch(selection){
            case 1:
                playAgainstRR();
                break;
            case 2:
                playAgainstHT();
                break;
            case 3:
                playAgainstPT();
                break;
            case 4:
                playAgainstHM();
                break;
            case 5:
                return;
        }
    }

    
    public static void watchAI(){
        System.out.println("Watch AI");
    }

    

    public static boolean checkRemainingShips(boolean[][] mysea, shipyard theyships){
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(theyships.getGrid()[i][j])
                {
                    if(!mysea[i][j])
                        return true;
                }
            }
        }

        return false;
    }



    public static boolean[][] createNewTable(){
        boolean [][] sea = new boolean[10][10];
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                sea[i][j] = false;
        return sea.clone();
    }



    public static void randomShipPlacer(shipyard ships)
    {
        //printSingle(ships, ships.getGrid());
        Random rand = new Random();

        
        int maxy = 0;
        int miny = 0;
        int maxx = 0;
        int minx = 0;
        int x = 0;
        int y =0;
        int rotation = 0;
        boolean placed = false;


        //aircraft carrier
        while(!placed)
        {
            //has shape like:
            //      xx
            //      xxx
            //
            //rotation above is 0 with axis at lower left corner
            //rotation++ = clockwise of rotation

            //choose rotation
            rotation = rand.nextInt(4);
            //System.out.printf("Rotation is: %d\n", rotation);

            //set max and min bounds based on rotation
            switch (rotation){
                case 0:
                    maxy = 9;
                    miny = 0;
                    maxx = 8;
                    minx = 0;
                    break;
                case 1:
                    maxy = 10;
                    miny = 2;
                    maxx = 9;
                    minx = 0;
                    break;
                case 2:
                    maxy = 10;
                    miny = 1;
                    maxx = 10;
                    minx = 2;
                    break;
                case 3:
                    maxy = 8;
                    miny = 0;
                    maxx = 10;
                    minx = 1;
                    break;
            }

            //set location
            x = rand.nextInt(maxx - minx) + minx;
            y = rand.nextInt(maxy - miny) + miny;

            //System.out.printf("ACCX: %d\nACCY: %d\n", x, y);

            //check if space is free
            placed = checkACCplacement(rotation, x, y, ships);
        }
            //place AircraftCarrier
            ships.setGrid(placeACC(rotation, x, y, ships.getGrid()));
            ships.setAircraftCarrier(rotation, x, y);

        //battleship
        placed = false;
        while(!placed)
        {
            //has shape like:
            //      xxxx
            //
            //rotation above is 0 with axis at leftmost point
            //rotation is 0 above
            //      x
            //      x
            //      x
            //      x
            //
            //rotation is 1 above with axis at bottom

            //choose rotation
            rotation = rand.nextInt(2);

            //set max and min bounds based on rotation
            if(rotation == 0)
            {
                maxy = 10;
                miny = 0;
                maxx = 7;
                minx = 0;
            }
            else
            {
                maxy = 7;
                miny = 0;
                maxx = 10;
                minx = 0;
            }

            //set location
            x = rand.nextInt(maxx - minx) + minx;
            y = rand.nextInt(maxy - miny) + miny;

            //check if space is free
            placed = checkBATSplacement(rotation, x, y, ships);
        }
            //place battleship
            ships.setGrid(placeBATS(rotation, x, y, ships.getGrid()));
            ships.setBattleShip(rotation, x, y);

        //submarine
        placed = false;
        while(!placed){
            //has shape like:
            //      xxxx
            //
            //rotation above is 0 with axis at leftmost point
            //rotation is 0 above
            //      x
            //      x
            //      x
            //
            //rotation is 1 above with axis at bottom

            //choose rotation
            rotation = rand.nextInt(2);

            //set max and min bounds based on rotation
            if(rotation == 0)
            {
                maxy = 10;
                miny = 0;
                maxx = 8;
                minx = 0;
            }
            else
            {
                maxy = 8;
                miny = 0;
                maxx = 10;
                minx = 0;
            }

            //set location
            x = rand.nextInt(maxx - minx) + minx;
            y = rand.nextInt(maxy - miny) + miny;

            //check if space is free
            placed = checkSUBplacement(rotation, x, y, ships);
        }
            //place submarine
            ships.setGrid(placeSUB(rotation, x, y, ships.getGrid()));
            ships.setSubMarine(rotation, x, y);

        
        //speedboat
        placed = false;
        while(!placed){
            //has shape like:
            //      xxxx
            //
            //rotation above is 0 with axis at leftmost point
            //rotation is 0 above
            //      x
            //      x
            //
            //rotation is 1 above with axis at bottom

            //choose rotation
            rotation = rand.nextInt(2);

            //set max and min bounds based on rotation
            if(rotation == 0)
            {
                maxy = 10;
                miny = 0;
                maxx = 9;
                minx = 0;
            }
            else
            {
                maxy = 9;
                miny = 0;
                maxx = 10;
                minx = 0;
            }

            //set location
            x = rand.nextInt(maxx - minx) + minx;
            y = rand.nextInt(maxy - miny) + miny;

            //check if space is free
            placed = checkSPDplacement(rotation, x, y, ships);
        }
            //place speedboat
            ships.setGrid(placeSPD(rotation, x, y, ships.getGrid()));
            ships.setSpeedBoat(rotation, x, y);
            
    }

    public static void shipSelection(shipyard ships){
        Scanner kin = new Scanner(System.in);

        boolean aircraftCarrier = false;
        boolean battleship = false;
        boolean submarine = false;
        boolean speedboat = false;
        boolean complete = false;
        while(!complete)
        {
            System.out.printf("Select your ship to place!\n");
            if(!aircraftCarrier)
                System.out.printf("\t1) Aircraft Carrier\n");
            if(!battleship)
                System.out.printf("\t2) Battleship\n");
            if(!submarine)
                System.out.printf("\t3) Submarine\n");
            if(!speedboat)
                System.out.printf("\t4) Speedboat\n");

            System.out.printf("Selection: ");

            String optin;
            boolean check = false;
            do{
                optin = kin.nextLine();
            
                switch (optin) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    check = false;                    
                        break;
                    default:
                        System.out.printf("Did not understand selection. Select again.\n\tSelection:");
                        check = true;
                        break;
                }
            }while(check);

            int selection = Integer.parseInt(optin);
        
            //place ship based on selection
            switch(selection){
                case 1:
                    accSelection(ships);
                    aircraftCarrier = true;
                    break;
                case 2:
                    btsSelection(ships);
                    battleship = true;
                    break;
                case 3:
                    subSelection(ships);
                    submarine = true;
                    break;
             case 4:
                    spdSelection(ships);
                    speedboat = true;
                    break;
            }


            if((aircraftCarrier && battleship && submarine && speedboat))
                complete = true;
        }
    }



    public static void accSelection(shipyard ships){
        Scanner kin = new Scanner(System.in);
        boolean idiot = true;
        boolean retry = true;
        int selection = 0;
        int rotation = 0;
        int xmax = 0;
        int xmin = 0;
        int ymax = 0;
        int ymin = 0;
        int x = 0;
        int y = 0;

        System.out.printf("You've selected to place the Aircraft Carrier!\n");
        System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
        printShips(ships.getGrid());
        while(retry)
        {
            while(idiot)
            {
                System.out.printf("Choose your rotation!\n");
                System.out.printf("0: \t\t1:\t\t2: \t\t3:\n");
                System.out.printf("xx \t\txx\t\txxx\t\t x\nxxx\t\txx\t\t xx\t\txx\n   \t\tx \t\t   \t\txx\n\tSelection: ");
                
                selection = kin.nextInt();
                if(selection < 4 && selection >= 0)
                    idiot = false;
                
                
                if(idiot)
                    System.out.printf("Did not understand selection. Try again.\n");
            }
            
            rotation = selection;

            idiot = true;
            while(idiot)
            {
                System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
                printShips(ships.getGrid());
                System.out.printf("Select your Aircraft Carrier's position!\nYour ship's position is a coordinate on the grid where the 'H' is depending on your rotation\n");
                switch(rotation){
                    case 0:
                        System.out.printf("xx\nHxx\n\nSelect X between 0 and 7(inclusive)\nX: ");
                        xmin = 0;
                        xmax = 7;
                        break;
                    case 1:
                        System.out.printf("Hx\nxx\nx \n\nSelect X between 0 and 8(inclusive)\nx: ");
                        xmin = 0;
                        xmax = 8;
                        break;
                    case 2:
                        System.out.printf("xxH\n xx\n\nSelect x between 2 and 9(inclusive)\nx: ");
                        xmin = 2;
                        xmax = 9;
                        break;
                    case 3:
                        System.out.printf(" x\nxx\nxH\n\nSelect x between 1 and 9(inclusive)\nx: ");
                        xmin = 1;
                        xmax = 9;
                        break;
                }

                
                
                x = kin.nextInt();

                switch(rotation){
                    case 0:
                        System.out.printf("Select y between 0 and 8(inclusive)\ny: ");
                        ymin = 0;
                        ymax = 9;
                        break;
                    case 1:
                        System.out.printf("Select y between 2 and 9(inclusive)\ny: ");
                        ymin = 2;
                        ymax = 9;
                        break;
                    case 2:
                        System.out.printf("Select y between 1 and 9(inclusive)\ny: ");
                        ymin = 1;
                        ymax = 9;
                        break;
                    case 3:
                        System.out.printf("Select y between 0 and 7(inclusive)\ny: ");
                        ymin = 0;
                        ymax = 7;
                        break;
                }

                y = kin.nextInt();

                if((x < xmin || x > xmax) || (y < ymin || y > ymax))
                {
                    System.out.printf("Oops! Looks like you put in coordinates that are out of bounds!\nTry inputing your coordinates again!\n\n");
                }
                else
                    idiot = false;
            }

            if(checkACCplacement(rotation, x, y, ships))
            {
                ships.setGrid(placeACC(rotation, x, y, ships.getGrid()));
                ships.setAircraftCarrier(rotation, x, y);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Aircraft Carrier in a place that was already taken by another ship! Try again!\n\n");

        }
    }

    public static void btsSelection(shipyard ships){
        Scanner kin = new Scanner(System.in);
        boolean idiot = true;
        boolean retry = true;
        int selection = 0;
        int rotation = 0;
        int xmax = 0;
        int xmin = 0;
        int ymax = 0;
        int ymin = 0;
        int x = 0;
        int y = 0;

    
        System.out.printf("You've selected to place the Battleship!\n");

        System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
        printShips(ships.getGrid());

        while(retry)
        {
            while(idiot)
            {
                System.out.printf("Choose your rotation!\n");
                System.out.printf("0:  \t\t1:\n");
                System.out.printf("xxxx\t\tx\n    \t\tx\n    \t\tx\n    \t\tx\n\tSelection: ");
                
                selection = kin.nextInt();
                if(selection < 2 && selection >= 0)
                    idiot = false;
                
                
                if(idiot)
                    System.out.printf("Did not understand selection. Try again.\n");
            }
            
            rotation = selection;

            idiot = true;
            while(idiot)
            {
                System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
                printShips(ships.getGrid());
                System.out.printf("Select your Battleship's position!\nYour ship's position is a coordinate on the grid where the 'H' is depending on your rotation\n");
                if(rotation == 0)
                {
                    System.out.printf("Hxxx\n\nSelect x between 0 and 6(inclusive)\nx: ");
                    xmin = 0;
                    xmax = 6;
                    ymax = 9;
                    ymin = 0;
                }
                else
                {
                    System.out.printf("x\nx\nx\nH\n\nSelect x between 0 and 9(inclusive)\nx: ");
                    xmin = 0;
                    xmax = 9;
                    ymin = 0;
                    ymax = 6;
                }
                
                x = kin.nextInt();

                if(rotation == 0)
                    System.out.printf("Select y between 0 and 9(inclusive)\ny: ");
                else
                    System.out.printf("Select y between 0 and 6(inclusive)\ny: ");

                y = kin.nextInt();

                if((x < xmin || x > xmax) || (y < ymin || y > ymax))
                {
                    System.out.printf("Oops! Looks like you put in coordinates that are out of bounds!\nTry inputing your coordinates again!\n\n");
                }
                else
                    idiot = false;
            }

            if(checkBATSplacement(rotation, x, y, ships))
            {
                ships.setGrid(placeBATS(rotation, x, y, ships.getGrid()));
                ships.setBattleShip(rotation, x, y);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Battleship in a place that was already taken by another ship! Try again!\n\n");

        }

    }

    public static void subSelection(shipyard ships){
        Scanner kin = new Scanner(System.in);
        boolean idiot = true;
        boolean retry = true;
        int selection = 0;
        int rotation = 0;
        int xmax = 0;
        int xmin = 0;
        int ymax = 0;
        int ymin = 0;
        int x = 0;
        int y = 0;

    
        System.out.printf("You've selected to place the Submarine!\n");

        System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
        printShips(ships.getGrid());

        while(retry)
        {
            while(idiot)
            {
                System.out.printf("Choose your rotation!\n");
                System.out.printf("0: \t\t1:\n");
                System.out.printf("xxx\t\tx\n    \t\tx\n    \t\tx\n\tSelection: ");
                
                selection = kin.nextInt();
                if(selection < 2 && selection >= 0)
                    idiot = false;
                
                
                if(idiot)
                    System.out.printf("Did not understand selection. Try again.\n");
            }
            
            rotation = selection;

            idiot = true;
            while(idiot)
            {
                System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
                printShips(ships.getGrid());

                System.out.printf("Select your Battleship's position!\nYour ship's position is a coordinate on the grid where the 'H' is depending on your rotation\n");
                if(rotation == 0)
                {
                    System.out.printf("Hxx\n\nSelect x between 0 and 7(inclusive)\nx: ");
                    xmin = 0;
                    xmax = 7;
                    ymax = 9;
                    ymin = 0;
                }
                else
                {
                    System.out.printf("x\nx\nH\n\nSelect x between 0 and 9(inclusive)\nx: ");
                    xmin = 0;
                    xmax = 9;
                    ymin = 0;
                    ymax = 7;
                }
                
                x = kin.nextInt();

                if(rotation == 0)
                    System.out.printf("Select y between 0 and 9(inclusive)\ny: ");
                else
                    System.out.printf("Select y between 0 and 7(inclusive)\ny: ");

                y = kin.nextInt();

                if((x < xmin || x > xmax) || (y < ymin || y > ymax))
                {
                    System.out.printf("Oops! Looks like you put in coordinates that are out of bounds!\nTry inputing your coordinates again!\n\n");
                }
                else
                    idiot = false;
            }

            if(checkSUBplacement(rotation, x, y, ships))
            {
                ships.setGrid(placeSUB(rotation, x, y, ships.getGrid()));
                ships.setSubMarine(rotation, x, y);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Submarine in a place that was already taken by another ship! Try again!\n\n");
            
            printShips(ships.getGrid());
        }

    }
    
    public static void spdSelection(shipyard ships){
        Scanner kin = new Scanner(System.in);
        boolean idiot = true;
        boolean retry = true;
        int selection = 0;
        int rotation = 0;
        int xmax = 0;
        int xmin = 0;
        int ymax = 0;
        int ymin = 0;
        int x = 0;
        int y = 0;

    
        System.out.printf("You've selected to place the Speedboat!\n");

        System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
        printShips(ships.getGrid());

        while(retry)
        {
            while(idiot)
            {
                System.out.printf("Choose your rotation!\n");
                System.out.printf("0:\t\t1:\n");
                System.out.printf("xx\t\tx\n    \t\tx\n\tSelection: ");
                
                selection = kin.nextInt();
                if(selection < 2 && selection >= 0)
                    idiot = false;
                
                
                if(idiot)
                    System.out.printf("Did not understand selection. Try again.\n");
            }
            
            rotation = selection;

            idiot = true;
            while(idiot)
            {
                System.out.printf("Here's your current ships. Be sure not to try and put one ship on top of another!\n");
                printShips(ships.getGrid());

                System.out.printf("Select your Speedboat's position!\nYour ship's position is a coordinate on the grid where the 'H' is depending on your rotation\n");
                if(rotation == 0)
                {
                    System.out.printf("Hx\n\nSelect x between 0 and 8(inclusive)\nx: ");
                    xmin = 0;
                    xmax = 8;
                    ymax = 9;
                    ymin = 0;
                }
                else
                {
                    System.out.printf("x\nH\n\nSelect x between 0 and 9(inclusive)\nx: ");
                    xmin = 0;
                    xmax = 9;
                    ymin = 0;
                    ymax = 8;
                }
                
                x = kin.nextInt();

                if(rotation == 0)
                    System.out.printf("Select y between 0 and 9(inclusive)\ny: ");
                else
                    System.out.printf("Select y between 0 and 8(inclusive)\ny: ");

                y = kin.nextInt();

                if((x < xmin || x > xmax) || (y < ymin || y > ymax))
                {
                    System.out.printf("Oops! Looks like you put in coordinates that are out of bounds!\nTry inputing your coordinates again!\n\n");
                }
                else
                    idiot = false;
            }

            if(checkSPDplacement(rotation, x, y, ships))
            {
                ships.setGrid(placeSPD(rotation, x, y, ships.getGrid()));
                ships.setSpeedBoat(rotation, x, y);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Speedboat in a place that was already taken by another ship! Try again!\n\n");

        }

    }





    public static boolean checkACCplacement(int rotation, int x, int y, shipyard ships){
        //System.out.printf("ACC\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        
        
        switch(rotation){
                case 0:
                    for(int i = x; i < x+3; i++)
                    {
                        if(ships.getGrid()[i][y])
                            return false;
                    }
                    for(int i = x; i < x+3; i++)
                    {
                        if(ships.getGrid()[i][y+1])
                            return false;
                    }
                    break;
                case 1:
                    for(int i = y; i >= y-2; i--)
                    {
                        if(ships.getGrid()[x][i])
                            return false;
                    }
                    for(int i = y; i >= y-1; i--)
                    {
                        if(ships.getGrid()[x+1][i])
                            return false;
                    }
                    break;
                case 2:
                    for(int i = x; i>=x-2; i--)
                    {
                        if(ships.getGrid()[i][y])
                            return false;
                    }
                    for(int i = x; i>=x-1; i--)
                    {
                        if(ships.getGrid()[i][y-1])
                            return false;
                    }
                    break;
                case 3:
                    for(int i = y; i < y+3; i++)
                    {
                        if(ships.getGrid()[x][i])
                            return false;
                    }
                    for(int i = y; i < y+2; i++)
                    {
                        if(ships.getGrid()[x-1][i])
                            return false;
                    }
                    break;
            }
        return true;
    }

    public static boolean checkBATSplacement(int rotation, int x, int y, shipyard ships){
        //System.out.printf("BAT\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        if(rotation == 0)
        {
            for(int i = x; i < x + 4; i++)
            {
                if(ships.getGrid()[i][y])
                    return false;
            }
        }
        else
        {
            for(int i = y; i < y+4; i++)
            {
                if(ships.getGrid()[x][i])
                    return false;
            }
        }
        return true;
    }

    public static boolean checkSUBplacement(int rotation, int x, int y, shipyard ships){ 
        //System.out.printf("SUB\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        if(rotation == 0)
        {
            for(int i = x; i < x + 3; i++)
            {
                if(ships.getGrid()[i][y])
                    return false;
            }
        }
        else
        {
            for(int i = y; i < y + 3; i++)
            {
                if(ships.getGrid()[x][i])
                    return false;
            }
        }
        return true;
    }

    public static boolean checkSPDplacement(int rotation, int x, int y, shipyard ships){
        //System.out.printf("SPD\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        if(rotation == 0)
        {
            for(int i = x; i < x + 2; i++)
            {
                if(ships.getGrid()[i][y])
                    return false;
            }
        }
        else
        {
            for(int i = y; i < y + 2; i++)
            {
                //System.out.println(i);
                if(ships.getGrid()[x][i])
                    return false;
            }
        }
        return true;
    }




    public static boolean[][] placeACC(int rotation, int x, int y, boolean [][] ships){
        //System.out.printf("PlACC:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);
        switch(rotation){
                case 0:
                    for(int i = x; i < x+3; i++)
                        ships[i][y] = true;
                    for(int i = x; i < x+2; i++)
                        ships[i][y+1] = true;
                    break;
                case 1:
                    for(int i = y; i >= y-2; i--)
                        ships[x][i] = true;
                    for(int i = y; i >= y-1; i--)
                        ships[x+1][i] = true;
                    break;
                case 2:
                    for(int i = x; i>=x-2; i--)
                        ships[i][y] = true;
                    for(int i = x; i>=x-1; i--)
                        ships[i][y-1] = true;
                    break;
                case 3:
                    for(int i = y; i < y+3; i++)
                        ships[x][i] = true;
                    for(int i = y; i < y+2; i++)
                        ships[x-1][i] = true;
                    break;
            }
        return ships;
    }

    public static boolean[][] placeBATS(int rotation, int x, int y, boolean [][] ships){
        //System.out.printf("PlBATS:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);

        if(rotation == 0)
        {
            for(int i = x; i < x + 4; i++)
                ships[i][y] = true;
        }
        else
        {
            for(int i = y; i < y + 4; i++)
                ships[x][i] = true;
        }
        return ships;
    }

    public static boolean[][] placeSUB(int rotation, int x, int y, boolean [][] ships){
        //System.out.printf("PlSUB:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);
        if(rotation == 0)
        {
            for(int i = x; i < x + 3; i++)
                ships[i][y] = true;
        }
        else
        {
            for(int i = y; i < y + 3; i++)
                ships[x][i] = true;
        }
        return ships;
    }

    public static boolean[][] placeSPD(int rotation, int x, int y, boolean [][] ships){
        //System.out.printf("PlSPD:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);
        if(rotation == 0)
        {
            for(int i = x; i < x + 2; i++)
                ships[i][y] = true;
        }
        else
        {
            for(int i = y; i < y + 2; i++)
                ships[x][i] = true;
        }
        return ships;
    }


    public static void printSingle(shipyard ships, boolean[][] sea)
    {
        System.out.printf("Ships:\t\t\tSea:\n");
        
        for(int i = 9; i >= 0; i--)
        {
            System.out.print(i);
            for(int j = 0; j < 10; j++)
            {
                System.out.print(" ");
                if(ships.getGrid()[j][i])
                    System.out.print("X");
                else
                    System.out.print(" ");
            }
            System.out.printf("\t%d", i);
            for(int j = 0; j < 10; j++)
            {
                System.out.print(" ");
                if(sea[j][i])
                    System.out.print("X");
                else
                    System.out.print(" ");
            }
            System.out.printf("\n");
        }
        System.out.printf("  0 1 2 3 4 5 6 7 8 9\t  0 1 2 3 4 5 6 7 8 9\n");

        System.out.printf("\n\n");
    }

    public static void printShips(boolean[][] ships)
    {
        System.out.printf("Ships:\n");
        
        for(int i = 9; i >= 0; i--)
        {
            System.out.print(i);
            for(int j = 0; j < 10; j++)
            {
                System.out.print(" ");
                if(ships[j][i])
                    System.out.print("X");
                else
                    System.out.print(" ");
            }
            System.out.printf("\n");
        }
        System.out.printf("  0 1 2 3 4 5 6 7 8 9\n");

        System.out.printf("\n\n");
    }

    public static void printMuliplayer(shipyard myships, boolean[][] mysea, shipyard theyships, boolean[][] theysea)
    {
        System.out.printf("Ships:\t\t\tSea:\n");
        
        for(int i = 9; i >= 0; i--)
        {
            System.out.print(i);
            for(int j = 0; j < 10; j++)
            {
                System.out.print(" ");
                if(myships.getGrid()[j][i])
                {
                    if(theysea[j][i])
                        System.out.print("H");
                    else
                        System.out.print("X");
                }
                else
                {
                    if(theysea[j][i])
                        System.out.print("O");
                    else
                        System.out.print(" ");
                }
            }
            System.out.printf("\t%d", i);
            for(int j = 0; j < 10; j++)
            {
                System.out.print(" ");
                if(mysea[j][i])
                {
                    if(theyships.getGrid()[j][i])
                        System.out.print("H");
                    else
                        System.out.print("X");
                }
                else
                    System.out.print(" ");
            }
            System.out.printf("\n");
        }
        System.out.printf("  0 1 2 3 4 5 6 7 8 9\t  0 1 2 3 4 5 6 7 8 9\n");

        System.out.printf("\n\n");
    }





    public static int[] returnSunken(shipyard myships, boolean[][] theysea)
    {
        //System.out.printf("recieved!\n");
        int x, y;
        int[] roster = new int[4];

        for(int i = 0; i<4; i++)
            roster[i] = 0;

        //ACC
        x = myships.getAircraftCarrier()[1];
        y = myships.getAircraftCarrier()[2];
        switch(myships.getAircraftCarrier()[0])
        {
            case 0:
                for(int i = x; i < x + 3; i++)
                {
                    if(theysea[i][y])
                        roster[0]++;
                }
                for(int i = x; i < x + 2; i++)
                {
                    if(theysea[i][y+1])
                        roster[0]++;
                }
                break;
            case 1:
                for(int i = y; i >= y - 2; i--)
                {
                    if(theysea[x][i])
                        roster[0]++;
                }
                for(int i = y; i >= y-1; i--)
                {
                    if(theysea[x+1][i])
                        roster[0]++;
                }
                break;
            case 2:
                for(int i = x; i >= x - 2; i--)
                {
                    if(theysea[i][y])
                        roster[0]++;
                }
                for(int i = x; i < x - 1; i--)
                {
                    if(theysea[i][y-1])
                        roster[0]++;
                }
                break;
            case 3:
                for(int i = y; i < y + 3; i++)
                {
                    if(theysea[x][i])
                        roster[0]++;
                }
                for(int i = y; i < y + 2; i++)
                {
                    if(theysea[x-1][i])
                        roster[0]++;
                }
                break;
        }


        //BTS
        x = myships.getBattleShip()[1];
        y = myships.getBattleShip()[2];
        if(myships.getBattleShip()[0] == 0)
        {
            for(int i = x; i < x + 4; i++)
            {
                if(theysea[i][y])
                    roster[1]++;
            }
        }
        else
        {
            for(int i = y; i < y + 4; i++)
            {
                if(theysea[x][i])
                    roster[1]++;
            }
        }


        //SUB
        x = myships.getSubMarine()[1];
        y = myships.getSubMarine()[2];
        if(myships.getSubMarine()[0] == 0)
        {
            for(int i = x; i < x + 3; i++)
            {
                if(theysea[i][y])
                    roster[2]++;
            }
        }
        else
        {
            for(int i = y; i < y + 3; i++)
            {
                if(theysea[x][i])
                    roster[2]++;
            }
        }


        //SPD
        x = myships.getSpeedBoat()[1];
        y = myships.getSpeedBoat()[2];
        if(myships.getSpeedBoat()[0] == 0)
        {
            for(int i = x; i < x + 2; i++)
            {
                if(theysea[i][y])
                    roster[3]++;
            }
        }
        else
        {
            for(int i = y; i < y + 2; i++)
            {
                if(theysea[x][i])
                    roster[3]++;
            }
        }

        //System.out.printf("Return:\n%d\n%d\n%d\n%d\nDone!\n", roster[0], roster[1], roster[2], roster[3]);
        return roster;
    }


    public static void playAgainstRR(){
        System.out.println("Play Against Random Ranger\nNot yet implemented\n");
    }

    public static void playAgainstHT(){
        System.out.println("Play Against Hunt and Target\nNot yet implemented\n");
    }

    public static void playAgainstPT(){
        System.out.println("Play Against Parity\nNot yet implemented\n");
    }

    public static void playAgainstHM(){
        System.out.printf("\nYou've chosen to play against the Heat Map AI!\n\n");
        Scanner kin = new Scanner(System.in);
        Random rand = new Random();
        

        boolean randomPlacement = false;
        String input;
        System.out.printf("Would you like to choose your ship placement?(y/n) (yes by default)\n\t>: ");
        input = kin.nextLine();
        input = input.toLowerCase();
        if(input.charAt(0) == 'n')
            randomPlacement = true;
        shipyard pships = new shipyard(createNewTable());
        boolean [][] psea = createNewTable();
        boolean accp, btsp, subp, spdp;
        accp = true;
        btsp = true;
        subp = true;
        spdp = true;

        if(randomPlacement)
            randomShipPlacer(pships);
        else
            shipSelection(pships);

        shipyard aships = new shipyard(createNewTable());
        //boolean [][] asea = createNewTable();
        boolean acaa, btsa, suba, spda;
        acaa = true;
        btsa = true;
        suba = true;
        spda = true;
        battleship.HeatMapAI hm = new HeatMapAI();

        randomShipPlacer(aships);

        //randomly decide who goes first
        int first;
        if(rand.nextBoolean())
            first = 1;
        else
            first = 2;
        
        if(first == 1)
            System.out.printf("It has been randomly decided that the player will go first.\nPlayer, press return when you're ready to begin.\n");
        else
            System.out.printf("It has been randomly decided that the AI will go first.\nThe AI has alreay gone.\nPlayer, press return when you're ready to begin.\n");
        

        int[] aicoords;
        boolean found = false;
        if(first == 2)
        {

            aicoords = HeatMapAI.find(accp, btsa, suba, spda);
            if(pships.getGrid()[aicoords[0]][aicoords[1]])
            {
                found = true;
                HeatMapAI.setHuntingTrue();
            }
        }

        boolean unfinished = true;
        while(unfinished)
        {
            
            printMuliplayer(pships, psea, aships, HeatMapAI.getSea());
            System.out.printf("Ready to fire! Keep all x and y coordinates between 0 and 9(inclusive)!\n");
            boolean idiot = true;
            int x = -1;
            int y = -1;
            int[] tester = new int[4];
            while(idiot)
            {
                System.out.printf("x: ");
                x = kin.nextInt();
                System.out.printf("y: ");
                y = kin.nextInt();

                if((x >=0 && x < 10) && (y >= 0 && y < 10))
                    idiot = false;
                else
                    System.out.printf("\nCoordinate out of bounds! Try again.\n");
            }

            psea[x][y] = true;
            unfinished = checkRemainingShips(psea, aships);
            if(unfinished)
            {
                if(aships.getGrid()[x][y])
                {
                    System.out.printf("Hit!\n");
                    tester = returnSunken(aships, psea);
                    if(accp)
                    {
                        if(tester[0] == 5)
                        {
                            accp = false;
                            System.out.printf("\nYou've sunk the Aircraft Carrier!\n\n xx\n xxx\n\n");
                        }
                    }
                    if(btsp)
                    {
                        if(tester[1] == 4)
                        {
                            btsp = false;
                            System.out.printf("\nYou've sunk the Battleship!\n\n xxxx\n\n");
                        }
                    }
                    if(subp)
                    {
                        if(tester[2] == 3)
                        {
                            subp = false;
                            System.out.printf("\nYou've sunk the Submarine!\n\n xxx\n\n");
                        }
                    }
                    if(spdp)
                    {
                        if(tester[3] == 2)
                        {
                            spdp = false;
                            System.out.printf("\nYou've sunk the Speedboat!\n\n xx\n\n");
                        }
                    }
                    
                    System.out.printf("\nShips left: \n");
                    if(accp)
                        System.out.printf("\tAircraft Carrier\n");
                    if(btsp)
                        System.out.printf("\tBattleship\n");
                    if(subp)
                        System.out.printf("\tSubmarine\n");
                    if(spdp)
                        System.out.printf("\tSpeedBoat\n");
                    
                    
                    System.out.printf("Press return to end your turn!");
                }
                else
                {
                    System.out.printf("Miss!\n");
                    System.out.printf("\nShips left: \n");
                    if(accp)
                        System.out.printf("\tAircraft Carrier\n");
                    if(btsp)
                        System.out.printf("\tBattleship\n");
                    if(subp)
                        System.out.printf("\tSubmarine\n");
                    if(spdp)
                        System.out.printf("\tSpeedBoat\n");
                    System.out.printf(" Press return to end your turn!");
                }
                for(int i = 0; i < 10; i++)
                    System.out.println();
            }
            
            if(found)
            {
                aicoords = HeatMapAI.hunt(acaa, btsa, suba, spda);
                tester = returnSunken(pships, HeatMapAI.getSea());
                if(acaa)
                {
                    if(tester[0] == 5)
                    {
                        acaa = false;
                        //System.out.printf("\nYou've sunk the Aircraft Carrier!\n\n xx\n xxx\n\n");
                        if(aicoords[2] == 4)
                            found = false;
                    }
                }
                if(btsa)
                {
                    if(tester[1] == 4)
                    {
                        btsp = false;
                        //System.out.printf("\nYou've sunk the Battleship!\n\n xxxx\n\n");
                        if(aicoords[2] == 3)
                            found = false;
                    }
                }
                if(subp)
                {
                    if(tester[2] == 3)
                    {
                        subp = false;
                        //System.out.printf("\nYou've sunk the Submarine!\n\n xxx\n\n");
                        if(aicoords[2] == 1)
                            found = false;
                    }
                }
                if(spdp)
                {
                    if(tester[3] == 2)
                    {
                        spdp = false;
                        //System.out.printf("\nYou've sunk the Speedboat!\n\n xx\n\n");
                        if(aicoords[2] == 1)
                            found = false;
                    }
                }
            }
            else
            {
                aicoords = HeatMapAI.find(acaa, btsa, suba, spda);
                tester = returnSunken(pships, HeatMapAI.getSea());
                if(acaa)
                {
                    if(tester[0] == 5)
                    {
                        acaa = false;
                        //System.out.printf("\nYou've sunk the Aircraft Carrier!\n\n xx\n xxx\n\n");
                        if(aicoords[2] == 4)
                            found = false;
                    }
                }
                if(btsa)
                {
                    if(tester[1] == 4)
                    {
                        btsp = false;
                        //System.out.printf("\nYou've sunk the Battleship!\n\n xxxx\n\n");
                        if(aicoords[2] == 3)
                            found = false;
                    }
                }
                if(subp)
                {
                    if(tester[2] == 3)
                    {
                        subp = false;
                        //System.out.printf("\nYou've sunk the Submarine!\n\n xxx\n\n");
                        if(aicoords[2] == 1)
                            found = false;
                    }
                }
                if(spdp)
                {
                    if(tester[3] == 2)
                    {
                        spdp = false;
                        //System.out.printf("\nYou've sunk the Speedboat!\n\n xx\n\n");
                        if(aicoords[2] == 1)
                            found = false;
                    }
                }
            }
        }
        unfinished = checkRemainingShips(HeatMapAI.getSea(), pships);

        
    }
    


    // public static int[] takeHMTurn(boolean[][] aisea, shipyard playerSea, boolean acc, boolean bts, boolean sub, boolean spd, boolean found){
    //     int[] chosenCoordinates;
    //     if(!found)
    //         chosenCoordinates = heatMapLogic(aisea, acc, bts, sub, spd);
    //     else
    //         chosenCoordinates = foundShip(aisea,  playerSea, acc, bts, sub, spd);


    // }

    

    public static int[] foundShip(boolean[][] aisea, shipyard playerShips, boolean acc, boolean bts, boolean sub, boolean spd)
    {
        int[] coords = new int[3];

        int expectedHits = 0;
        if(!acc)
            expectedHits += 5;
        if(!bts)
            expectedHits += 4;
        if(!sub)
            expectedHits += 3;
        if(!spd)
            expectedHits +=2;

        int numHits = 0;
        boolean[][] pGridCopy = playerShips.getGrid();
        boolean[][] hits = new boolean[10][10];
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if((pGridCopy[i][j]) && aisea[i][j])
                {
                    hits[i][j] = true;
                    numHits++;
                }
            }
        }
        coords[2] = numHits - expectedHits;


        int[] accData = playerShips.getAircraftCarrier();
        int[] btsData = playerShips.getBattleShip();
        int[] subData = playerShips.getSubMarine();
        int[] spdData = playerShips.getSpeedBoat();


        boolean[][] sunkShips = new boolean[10][10];
        if(!acc)
            sunkShips = placeACC(accData[0], accData[1], accData[2], sunkShips);
        if(!bts)
            sunkShips = placeBATS(btsData[0], btsData[1], btsData[2], sunkShips);
        if(!sub)
            sunkShips = placeSUB(subData[0], subData[1], subData[2], sunkShips);
        if(!spd)
            sunkShips = placeSPD(spdData[0], spdData[1], spdData[2], sunkShips);


        boolean[][] activeHits = aisea.clone();
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(sunkShips[i][j])
                    activeHits[i][j] = false;
            }
        }
        
        return coords;
    }


    public static int[][] mapACC(int rotation, int x, int y, int[][] map){
        //System.out.printf("PlACC:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);
        switch(rotation){
                case 0:
                    for(int i = x; i < x+3; i++)
                        map[i][y]++;
                    for(int i = x; i < x+2; i++)
                        map[i][y+1]++;
                    break;
                case 1:
                    for(int i = y; i >= y-2; i--)
                        map[x][i]++;
                    for(int i = y; i >= y-1; i--)
                        map[x+1][i]++;
                    break;
                case 2:
                    for(int i = x; i>=x-2; i--)
                        map[i][y]++;
                    for(int i = x; i>=x-1; i--)
                        map[i][y-1]++;
                    break;
                case 3:
                    for(int i = y; i < y+3; i++)
                        map[x][i]++;
                    for(int i = y; i < y+2; i++)
                        map[x-1][i]++;
                    break;
            }
        return map;
    }

    public static int[][] mapBAT(int rotation, int x, int y, int[][] map){
        //System.out.printf("PlBATS:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);

        if(rotation == 0)
        {
            for(int i = x; i < x + 4; i++)
                map[i][y]++;
        }
        else
        {
            for(int i = y; i < y + 4; i++)
                map[x][i]++;
        }
        return map;
    }

    public static int[][] mapSUB(int rotation, int x, int y, int[][] map){
        //System.out.printf("PlSUB:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);
        if(rotation == 0)
        {
            for(int i = x; i < x + 3; i++)
                map[i][y]++;
        }
        else
        {
            for(int i = y; i < y + 3; i++)
                map[x][i]++;
        }
        return map;
    }

    public static int[][] mapSPD(int rotation, int x, int y, int[][] map){
        //System.out.printf("PlSPD:\nrotation: %d\nx: %d\ny: %d\nclear\n", rotation, x, y);
        if(rotation == 0)
        {
            for(int i = x; i < x + 2; i++)
                map[i][y]++;
        }
        else
        {
            for(int i = y; i < y + 2; i++)
                map[x][i]++;
        }
        return map;
    }





    public static boolean checkMAPACCplacement(int rotation, int x, int y, boolean[][] ships){
        //System.out.printf("ACC\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        
        
        switch(rotation){
                case 0:
                    for(int i = x; i < x+3; i++)
                    {
                        if(ships[i][y])
                            return false;
                    }
                    for(int i = x; i < x+3; i++)
                    {
                        if(ships[i][y+1])
                            return false;
                    }
                    break;
                case 1:
                    for(int i = y; i >= y-2; i--)
                    {
                        if(ships[x][i])
                            return false;
                    }
                    for(int i = y; i >= y-1; i--)
                    {
                        if(ships[x+1][i])
                            return false;
                    }
                    break;
                case 2:
                    for(int i = x; i>=x-2; i--)
                    {
                        if(ships[i][y])
                            return false;
                    }
                    for(int i = x; i>=x-1; i--)
                    {
                        if(ships[i][y-1])
                            return false;
                    }
                    break;
                case 3:
                    for(int i = y; i < y+3; i++)
                    {
                        if(ships[x][i])
                            return false;
                    }
                    for(int i = y; i < y+2; i++)
                    {
                        if(ships[x-1][i])
                            return false;
                    }
                    break;
            }
        return true;
    }

    public static boolean checkMAPBATSplacement(int rotation, int x, int y, boolean[][] ships){
        //System.out.printf("BAT\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        if(rotation == 0)
        {
            for(int i = x; i < x + 4; i++)
            {
                if(ships[i][y])
                    return false;
            }
        }
        else
        {
            for(int i = y; i < y+4; i++)
            {
                if(ships[x][i])
                    return false;
            }
        }
        return true;
    }

    public static boolean checkMAPSUBplacement(int rotation, int x, int y, boolean[][] ships){ 
        //System.out.printf("SUB\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        if(rotation == 0)
        {
            for(int i = x; i < x + 3; i++)
            {
                if(ships[i][y])
                    return false;
            }
        }
        else
        {
            for(int i = y; i < y + 3; i++)
            {
                if(ships[x][i])
                    return false;
            }
        }
        return true;
    }

    public static boolean checkMAPSPDplacement(int rotation, int x, int y, boolean[][] ships){
        //System.out.printf("SPD\nrotation: %d\nx: %d\ny: %d\nlength: %d\n1: %d\n", rotation, x, y, ships.getGrid().length, ships.getGrid()[1].length);
        if(rotation == 0)
        {
            for(int i = x; i < x + 2; i++)
            {
                if(ships[i][y])
                    return false;
            }
        }
        else
        {
            for(int i = y; i < y + 2; i++)
            {
                //System.out.println(i);
                if(ships[x][i])
                    return false;
            }
        }
        return true;
    }


    public static class shipyard
    {
        private boolean[][] grid = new boolean[10][10];
        private int[] acc = new int[3];
        private int[] bts = new int[3];
        private int[] sub = new int[3];
        private int[] spd = new int[3];


        public shipyard(boolean[][] newGrid)
        {
            grid = newGrid;
        }


        public  void setAircraftCarrier(int rotation, int x, int y)
        {
            acc[0] = rotation;
            acc[1] = x;
            acc[2] = y;
        }

        public void setBattleShip(int rotation, int x, int y)
        {
            bts[0] = rotation;
            bts[1] = x;
            bts[2] = y;
        }

        public void setSubMarine(int rotation, int x, int y)
        {
            sub[0] = rotation;
            sub[1] = x;
            sub[2] = y;
        }

        public void setSpeedBoat(int rotation, int x, int y)
        {
            spd[0] = rotation;
            spd[1] = x;
            spd[2] = y;
        }

        public void setGrid(boolean[][] newGrid)
        {
            grid = newGrid;
        }


        public int[] getAircraftCarrier()
        {
            return acc;
        }

        public int[] getBattleShip()
        {
            return bts;
        }

        public int[] getSubMarine()
        {
            return sub;
        }

        public int[] getSpeedBoat()
        {
            return spd;
        }

        public boolean[][] getGrid()
        {
            return grid;
        }
    }

    public static class HeatMapAI{
        static int[] acc = new int[3];
        static int[] bts = new int[3];
        static int[] sub = new int[3];
        static int[] spd = new int[3];
        static boolean hunting = false;
        static boolean haveSuspiciousHits = false;

        static boolean sHitsACC = false;
        static int saccnum;
        static int[] saccx;
        static int[] saccy;
        static int[] saccr;

        static boolean sHitsBTS = false;
        static int sbtsnum;
        static int[] sbtsx;
        static int[] sbtsy;
        static int[] sbtsr;

        static boolean sHitsSUB = false;
        static int ssubnum;
        static int[] ssubx;
        static int[] ssuby;
        static int[] ssubr;

        static boolean sHitsSPD = false;
        static int sspdnum;
        static int[] sspdx;
        static int[] sspdy;
        static int[] sspdr;

        static boolean[][] sunkenShips = createNewTable();
        static boolean[][] activeHits = createNewTable();
        static boolean[][] suspiciousHits = createNewTable();
        static boolean[][] misses  = createNewTable();
        static boolean[][] sea = createNewTable();
        static boolean[][] lineYErrors = createNewTable();
        static boolean[][] lineXErrors = createNewTable();

        static int previousX;
        static int previousY;

        public HeatMapAI(){
            for(int i= 0; i < 3; i++)
            {
                acc[i] = -1;
                bts[i] = -1;
                sub[i] = -1;
                spd[i] = -1;
            }


            previousX = -1;
            previousY = -1;
        }


        public static int[] find(boolean accb, boolean btsb, boolean subb, boolean spdb)
        {
            int[] coords = heatMapLogic(sea, accb, btsb, subb, spdb);
            previousX = coords[0];
            previousY = coords[1];
            sea[previousX][previousY] = true;
            return coords;
        }

        private static int[] heatMapLogic(boolean acc, boolean bts, boolean sub, boolean spd){
        Random rand = new Random();

        int[] coords = new int[3];
        int[][] map = new int[10][10];

        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
                map[i][j] = 0;
        }


        //ACC
        if(acc)
        {
            //rotation 0
            for(int x = 0; x < 8; x++)
            {
                for(int y = 0; y < 9; y++)
                {
                    if(checkMAPACCplacement(0, x, y, sea))
                        map = mapACC(0, x, y, map);
                }
            }

            //rotation 1
            for(int x = 0; x < 9; x++)
            {
                for(int y = 2; y < 10; y++)
                {
                    if(checkMAPACCplacement(1, x, y, sea))
                        map = mapACC(0, x, y, map);
                }
            }

            //rotation 2
            for(int x = 2; x < 10; x++)
            {
                for(int y = 1; y < 10; y++)
                {
                    if(checkMAPACCplacement(2, x, y, sea))
                        map = mapACC(2, x, y, map);
                }
            }

            //rotation 3
            for(int x = 1; x < 10; x++)
            {
                for(int y = 0; y < 8; y++)
                {
                    if(checkMAPACCplacement(3, x, y, sea))
                        map = mapACC(3, x, y, map);
                }
            }


        }

        //BTS
        if(bts)
        {
            //rotation 0
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 7; y++)
                {
                    if(checkMAPBATSplacement(0, x, y, sea))
                        map = mapBAT(0, x, y, map);
                }
            }
            //rotation 1
            for(int x = 0; x < 7; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(checkMAPBATSplacement(1, x, y, sea))
                        map = mapBAT(1, x, y, map);
                }
            }
        }

        //SUB
        if(sub)
        {
            //rotation 0
            for(int x = 0; x < 8; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(checkMAPSUBplacement(0, x, y, sea))
                        map = mapSUB(y, x, y, map);
                }
            }
            //rotation 1
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 8; y++)
                {
                    if(checkMAPSUBplacement(1, x, y, sea))
                        map = mapSUB(1, x, y, map);
                }
            }
        }

        
        //SPD
        if(spd)
        {
            //rotation 0
            for(int x = 0; x < 9; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(checkMAPSPDplacement(0, x, y, sea))
                        map = mapSPD(0, x, y, map);
                }
            }
            //rotation 1
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 9; y++)
                {
                    if(checkMAPSPDplacement(1, x, y, sea))
                        map = mapSPD(1, x, y, map);
                }
            }
        }




        int maxvalue = 0;
        int nummax = -1;
        int temp;
        int[] xcs = new int[100];
        int[] ycs = new int[100];

        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                temp = map[i][j];
                if(temp > maxvalue)
                {
                    nummax = 0;
                    maxvalue = temp;
                    xcs[nummax] = i;
                    ycs[nummax] = j;
                }
                else if(temp == maxvalue)
                {
                    nummax++;
                    xcs[nummax] = i;
                    ycs[nummax] = j;
                }
            }
        }

        int selection = rand.nextInt(nummax+1);
        coords[0] = xcs[selection];
        coords[1] = ycs[selection];
        coords[2] = 0;


        return coords;
    }
    
        public static void setHuntingTrue(){
            hunting = true;
        }

        public static boolean[][] getSea(){
            return sea;
        }

        public static int[] hunt(boolean accb, boolean btsb, boolean subb, boolean spdb){
            int expectedHits = 0;
            int hits = 0;
            int[] coords = new int[3];

            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(activeHits[x][y])
                        hits++;
                }
            }

            
            
            
            if(!accb)
            {
                expectedHits += 5;
                if(acc[0] == -1)
                    findACC();
            }
            if(!btsb)
            {
                expectedHits += 4;
                if(bts[0] == -1)
                    findBTS();
            }
            if(!subb)
            {
                expectedHits += 3;
                if(sub[0] == -1)
                    findSUB();
            }
            if(!spdb)
            {
                expectedHits +=2;
                if(spd[0] == -1)
                    findSPD();
            }

            if(!haveSuspiciousHits)
            {
                int[] line = isline();
                if(line[0] != -1)
                {
                    if(line[0] == line[2])
                    {
                        //up
                        if((line[3]+1 < 10) && !sea[line[2]][line[3]+1])
                        {
                            coords[0]=line[2];
                            coords[1]=line[3]+1;
                            coords[2] = hits - expectedHits;
                            previousX = coords[0];
                            previousY = coords[1];
                            return coords;
                        }
                        else if((line[1]-1 >= 0) && !sea[line[0]][line[1]-1])
                        {
                            coords[0] = line[0];
                            coords[1] = line[1] - 1;
                            coords[2] = hits - expectedHits;
                            previousX = coords[0];
                            previousY = coords[1];
                            return coords;
                        }
                        else if(line[0]-1 >= 0)
                        {
                            if(!sea[line[0]-1][line[3]])
                            {
                                coords[0] = line[0] - 1;
                                coords[1] = line[3];
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                            else if(!sea[line[0]-1][line[1]])
                            {
                                coords[0] = line[0] - 1;
                                coords[1] = line[1];
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                        }
                        if(line[0]+1 < 10)
                        {
                            if(!sea[line[0]+1][line[3]])
                            {
                                coords[0] = line[0] + 1;
                                coords[1] = line[3];
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                            else if(!sea[line[0]-1][line[1]])
                            {
                                coords[0] = line[0] + 1;
                                coords[1] = line[1];
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                        }
                        else
                        {
                            for(int y = line[1]; y <= line[3]; y++)
                            {
                                lineYErrors[line[0]][y] = true;
                                return hunt(accb, btsb, subb, spdb);
                            }
                        }

                    }
                    else
                    {
                        //horizontal
                        if((line[2]+1 < 10) && !sea[line[2]+1][line[3]])
                        {
                            coords[0]=line[2]+1;
                            coords[1]=line[3];
                            coords[2] = hits - expectedHits;
                            previousX = coords[0];
                            previousY = coords[1];
                            return coords;
                        }
                        else if((line[0]-1 >= 0) && !sea[line[0]-1][line[1]])
                        {
                            coords[0] = line[0] - 1;
                            coords[1] = line[1];
                            coords[2] = hits - expectedHits;
                            previousX = coords[0];
                            previousY = coords[1];
                            return coords;
                        }
                        else if(line[1]-1 >= 0)
                        {
                            if(!sea[line[0]][line[3]-1])
                            {
                                coords[0] = line[0];
                                coords[1] = line[3] - 1;
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                            else if(!sea[line[0]][line[1]-1])
                            {
                                coords[0] = line[0];
                                coords[1] = line[1] - 1;
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                        }
                        if(line[3]+1 < 10)
                        {
                            if(!sea[line[0]][line[3]+1])
                            {
                                coords[0] = line[0];
                                coords[1] = line[3] + 1;
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                            else if(!sea[line[0]][line[1]+1])
                            {
                                coords[0] = line[0];
                                coords[1] = line[1] + 1;
                                coords[2] = hits - expectedHits;
                                previousX = coords[0];
                                previousY = coords[1];
                                return coords;
                            }
                        }
                        else
                        {
                            for(int x = line[0]; x <= line[2]; x++)
                            {
                                lineXErrors[line[x]][line[1]] = true;
                                return hunt(accb, btsb, subb, spdb);
                            }
                        }

                    }


                }
                else
                {
                    int[] rval = searchAroundHit();
                    coords[0] = rval[0];
                    coords[1] = rval[1];
                    coords[2] = hits - expectedHits;
                    previousX = coords[0];
                    previousY = coords[1];
                    return coords;

                }
            }
            else
            {
                System.out.printf("Help! an ambiguous ship!\n");
                coords[0] = -1;
                coords[1] = -1;
                coords[2] = -1;
            }

            return coords;

        }


        public static int[] searchAroundHit()
        {
            int[] values = new int[2];
            boolean minx = false;
            boolean miny = false;
            boolean maxx = false;
            boolean maxy = false;
            if(previousX == 0)
                minx = true;
            else if(previousX == 9)
                maxx = true;
            if(previousY == 0)
                miny = true;
            else if(previousY == 9)
                maxy = true;

            if(!(minx || sea[previousX - 1][previousY]))
            {
                values[0] = previousX -1;
                values[1] = previousY;
                return values;
            }
            else if(!(maxx || sea[previousX + 1][previousY]))
            {
                values[0] = previousX +1;
                values[1] = previousY;
                return values;
            }
            else if(!(maxy || sea[previousX][previousY + 1]))
            {
                values[0] = previousX;
                values[1] = previousY + 1;
                return values;
            }
            else if(!(miny || sea[previousX][previousY - 1]))
            {
                values[0] = previousX;
                values[1] = previousY - 1;
                
            }
            else
            {
                System.out.printf("Lone point error\n");
                values[0] = -1;
                values[1] = -1;
            }
            return values;
        }

        public static int[] isline()
        {
            int coords[] = new int[4];
            for(int y = 0; y < 10; y++)
            {
                for(int x = 0; x < 9; x++)
                {
                    if(activeHits[x][y] && !lineXErrors[x][y])
                    {
                        if(activeHits[x+1][y])
                        {
                            int i = x;
                            boolean moreline = true;
                            while(moreline)
                            {
                                i++;
                                if(!(i < 10 && activeHits[i][y]))
                                    moreline = false;
                            }
                            coords[0] = x;
                            coords[1] = y;
                            coords[2] = i;
                            coords[3] = y;
                            return coords;
                        }
                    }
                }
            }
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 9; y++)
                {
                    if(activeHits[x][y] && !lineYErrors[x][y])
                    {
                        if(activeHits[x][y+1])
                        {
                            int i = y;
                            boolean moreline = true;
                            while(moreline)
                            {
                                i++;
                                if(!(i < 10 && activeHits[x][i]))
                                    moreline = false;
                            }
                            coords[0] = x;
                            coords[1] = y;
                            coords[2] = x;
                            coords[3] = i;
                            return coords;
                        }
                    }
                }
            }

            coords[0] = -1;
            coords[1] = -1;
            coords[2] = -1;
            coords[3] = -1;
            
            return coords;
        }


        public static void findACC()
        {
            boolean[][] negativeActiveHits = activeHits.clone();
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(negativeActiveHits[x][y])
                        negativeActiveHits[x][y] = false;
                    else
                        negativeActiveHits[x][y] = true;
                }
            }

            int[] xcors = new int[100];
            int[] ycors = new int[100];
            int[] rotations = new int[100];
            int numFound = -1;
            //rotation 0
            for(int x = 0; x < 8; x++)
            {
                for(int y = 0; y < 9; y++)
                {
                    if(checkMAPACCplacement(0, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 0;
                    }
                }
            }
            //rotation 1
            for(int x = 0; x < 9; x++)
            {
                for(int y = 2; y < 10; y++)
                {
                    if(checkMAPACCplacement(1, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 1;
                    }
                }
            }
            //rotation 2
            for(int x = 2; x < 10; x++)
            {
                for(int y = 1; y < 10; y++)
                {
                    if(checkMAPACCplacement(2, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 2;
                    }
                }
            }
            //rotation 3
            for(int x = 1; x < 10; x++)
            {
                for(int y = 0; y < 8; y++)
                {
                    if(checkMAPACCplacement(3, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 3;
                    }
                }
            }

            int temp = -1;
            int[] arot = new int[100];
            int[] ax = new int[100];
            int[] ay = new int[100];
            for(int i = 0; i <= numFound; i++)
            {
                switch(rotations[i])
                {
                    case 0:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i]+j == previousX && ycors[i] == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        for(int j = 0; j < 2; j++)
                        {
                            if(xcors[i]+j == previousX && ycors[i]+1 == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                    case 1:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i] == previousX && ycors[i]-j == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        for(int j = 0; j < 2; j++)
                        {
                            if(xcors[i]+1 == previousX && ycors[i]-j == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                    case 2:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i]-j == previousX && ycors[i] == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        for(int j = 0; j < 2; j++)
                        {
                            if(xcors[i]-j == previousX && ycors[i]-1 == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                    case 3:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i] == previousX && ycors[i]+j == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        for(int j = 0; j < 2; j++)
                        {
                            if(xcors[i]-1 == previousX && ycors[i]+j == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;

                }
            }

            if(temp > 0)
            {
                

                



                haveSuspiciousHits = true;
                sHitsACC = true;
                saccnum = numFound;
                saccx = ax;
                saccy = ay;
                saccr = arot;
            }
            else
            {
                acc[0] = arot[0];
                acc[1] = ax[0];
                acc[2] = ay[0];
                negativeActiveHits = placeACC(arot[0], ax[0], ay[0], negativeActiveHits);

                for(int x = 0; x < 10; x++)
                {
                    for(int y = 0; y < 10; y++)
                    {
                        if(negativeActiveHits[x][y])
                            negativeActiveHits[x][y] = false;
                        else
                            negativeActiveHits[x][y] = true;
                    }
                }

                activeHits = negativeActiveHits.clone();
            }
        }

        public static void findBTS(){
            boolean[][] negativeActiveHits = activeHits.clone();
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(negativeActiveHits[x][y])
                        negativeActiveHits[x][y] = false;
                    else
                        negativeActiveHits[x][y] = true;
                }
            }

            int[] xcors = new int[100];
            int[] ycors = new int[100];
            int[] rotations = new int[100];
            int numFound = -1;

            //rotation 0
            for(int x = 0; x < 7; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(checkMAPBATSplacement(0, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 0;
                    }
                }

            }
            //rotation 1
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 7; y++)
                {
                    if(checkMAPBATSplacement(1, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 1;
                    }
                }
            }

            int temp = -1;
            int[] arot = new int[100];
            int[] ax = new int[100];
            int[] ay = new int[100];
            for(int i = 0; i <= numFound; i++)
            {
                switch(rotations[i])
                {
                    case 0:
                        for(int j = 0; j < 4; j++)
                        {
                            if(xcors[i]+j == previousX && ycors[i] == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                    case 1:
                        for(int j = 0; j < 4; j++)
                        {
                            if(xcors[i] == previousX && ycors[i]+j == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                }
            }

            if(temp > 0)
            {
                haveSuspiciousHits = true;
                sHitsBTS = true;
                sbtsnum = temp;
                sbtsx = ax;
                sbtsy = ay;
                sbtsr = arot;
            }
            else
            {
                bts[0] = arot[0];
                bts[1] = ax[0];
                bts[2] = ay[0];
                negativeActiveHits = placeBATS(arot[0], ax[0], ay[0], negativeActiveHits);

                for(int x = 0; x < 10; x++)
                {
                    for(int y = 0; y < 10; y++)
                    {
                        if(negativeActiveHits[x][y])
                            negativeActiveHits[x][y] = false;
                        else
                            negativeActiveHits[x][y] = true;
                    }
                }

                activeHits = negativeActiveHits;
            }
        }

        public static void findSUB(){
            boolean[][] negativeActiveHits = activeHits.clone();
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(negativeActiveHits[x][y])
                        negativeActiveHits[x][y] = false;
                    else
                        negativeActiveHits[x][y] = true;
                }
            }

            int[] xcors = new int[100];
            int[] ycors = new int[100];
            int[] rotations = new int[100];
            int numFound = -1;

            //rotation 0
            for(int x = 0; x < 8; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(checkMAPSUBplacement(0, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 0;
                    }
                }

            }
            //rotation 1
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 8; y++)
                {
                    if(checkMAPSUBplacement(1, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 1;
                    }
                }
            }

            int temp = -1;
            int[] arot = new int[100];
            int[] ax = new int[100];
            int[] ay = new int[100];
            for(int i = 0; i <= numFound; i++)
            {
                switch(rotations[i])
                {
                    case 0:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i]+j == previousX && ycors[i] == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                    case 1:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i] == previousX && ycors[i]+j == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                }
            }

            if(numFound > 0)
            {
                haveSuspiciousHits = true;
                sHitsSUB = true;
                ssubnum = numFound;
                ssubx = ax;
                ssuby = ay;
                ssubr = arot;
            }
            else
            {
                sub[0] = arot[0];
                sub[1] = ax[0];
                sub[2] = ay[0];
                negativeActiveHits = placeSUB(arot[0], ax[0], ay[0], negativeActiveHits);

                for(int x = 0; x < 10; x++)
                {
                    for(int y = 0; y < 10; y++)
                    {
                        if(negativeActiveHits[x][y])
                            negativeActiveHits[x][y] = false;
                        else
                            negativeActiveHits[x][y] = true;
                    }
                }

                activeHits = negativeActiveHits;
            }
        }

        public static void findSPD(){
            boolean[][] negativeActiveHits = activeHits.clone();
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(negativeActiveHits[x][y])
                        negativeActiveHits[x][y] = false;
                    else
                        negativeActiveHits[x][y] = true;
                }
            }

            int[] xcors = new int[100];
            int[] ycors = new int[100];
            int[] rotations = new int[100];
            int numFound = -1;

            //rotation 0
            for(int x = 0; x < 9; x++)
            {
                for(int y = 0; y < 10; y++)
                {
                    if(checkMAPSPDplacement(0, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 0;
                    }
                }

            }
            //rotation 1
            for(int x = 0; x < 10; x++)
            {
                for(int y = 0; y < 9; y++)
                {
                    if(checkMAPSPDplacement(1, x, y, negativeActiveHits))
                    {
                        numFound++;
                        xcors[numFound] = x;
                        ycors[numFound] = y;
                        rotations[numFound] = 1;
                    }
                }
            }

            int temp = -1;
            int[] arot = new int[100];
            int[] ax = new int[100];
            int[] ay = new int[100];
            for(int i = 0; i <= numFound; i++)
            {
                switch(rotations[i])
                {
                    case 0:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i]+j == previousX && ycors[i] == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                    case 1:
                        for(int j = 0; j < 3; j++)
                        {
                            if(xcors[i] == previousX && ycors[i]+j == previousY)
                            {
                                temp++;
                                arot[temp] = rotations[i];
                                ax[temp] = xcors[i];
                                ay[temp] = ycors[i];
                                break;
                            }
                        }
                        break;
                }
            }

            if(numFound > 0)
            {
                haveSuspiciousHits = true;
                sHitsSPD = true;
                sspdnum = numFound;
                sspdx = ax;
                sspdy = ay;
                sspdr = arot;
            }
            else
            {
                spd[0] = arot[0];
                spd[1] = ax[0];
                spd[2] = ay[0];
                negativeActiveHits = placeSPD(arot[0], ax[0], ay[0], negativeActiveHits);

                for(int x = 0; x < 10; x++)
                {
                    for(int y = 0; y < 10; y++)
                    {
                        if(negativeActiveHits[x][y])
                            negativeActiveHits[x][y] = false;
                        else
                            negativeActiveHits[x][y] = true;
                    }
                }

                activeHits = negativeActiveHits;
            }
        }
    }
}
