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
        
        boolean [][] sea = createNewTable();
        boolean [][] ships = createNewTable();

        if(randomPlacement)
            ships = randomShipPlacer(ships);
        else
            ships = shipSelection(ships);

        printSingle(ships, sea);
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
                printSingle(ships, sea);
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
        boolean [][] ships1 = createNewTable();

        if(randomPlacement)
            ships1 = randomShipPlacer(ships1);
        else
        {
            System.out.printf("Begin Ship Selection for Player1!\n");
            ships1 = shipSelection(ships1);
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
        boolean [][] ships2 = createNewTable();

        if(randomPlacement)
            ships2 = randomShipPlacer(ships2);
        else
        {
            System.out.printf("Begin Ship Selection for Player2!\n");
            ships2 = shipSelection(ships2);
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
                    if(ships2[x][y])
                        System.out.printf("Hit! Press return and then hand over to player 2!");
                    else
                        System.out.printf("Miss! Press return and then hand over to player 2!");

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
                    if(ships1[x][y])
                        System.out.printf("Hit! Press return and then hand over to player 2!");
                    else
                        System.out.printf("Miss! Press return and then hand over to player 2!");
                        
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
        System.out.println("Play against AI");
    }


    public static void watchAI(){
        System.out.println("Watch AI");
    }

    

    public static boolean checkRemainingShips(boolean[][] mysea, boolean[][]theyships)
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(theyships[i][j])
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
        return sea;
    }



    public static boolean[][] randomShipPlacer(boolean[][] ships)
    {
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
            ships = placeACC(rotation, x, y, ships);


        //battleship
        placed = false;
        while(!placed)
        {
            //has shape like:
            //      xxxx
            //
            //rotation above is 0 with axis at leftmost point
            //rotation++ = clockwise of rotation

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
                maxy = 10;
                miny = 3;
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
            ships = placeBATS(rotation, x, y, ships);

        //submarine
        placed = false;
        while(!placed){
            //has shape like:
            //      xxx
            //
            //rotation above is 0 with axis at leftmost point
            //rotation++ = clockwise of rotation

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
                maxy = 10;
                miny = 2;
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
            ships = placeSUB(rotation, x, y, ships);

        
        //speedboat
        placed = false;
        while(!placed){
            //has shape like:
            //      xx
            //
            //rotation above is 0 with axis at leftmost point
            //rotation++ = clockwise of rotation

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
                maxy = 10;
                miny = 1;
                maxx = 10;
                minx = 0;
            }

            //set location
            x = rand.nextInt(maxx - minx) + minx;
            y = rand.nextInt(maxy - miny) + miny;

            //check if space is free
            placed = checkSPDplacement(rotation, x, y, ships);
        }
            //place submarine
            ships = placeSPD(rotation, x, y, ships);
            
        return ships;
    }

    public static boolean[][] shipSelection(boolean[][] ships){
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
                    ships = accSelection(ships);
                    aircraftCarrier = true;
                    break;
                case 2:
                    ships = btsSelection(ships);
                    battleship = true;
                    break;
                case 3:
                    ships = subSelection(ships);
                    submarine = true;
                    break;
             case 4:
                    ships = spdSelection(ships);
                    speedboat = true;
                    break;
            }


            if((aircraftCarrier && battleship && submarine && speedboat))
                complete = true;
        }


        return ships;
    }



    public static boolean[][] accSelection(boolean[][] ships){
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
        printShips(ships);
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
                printShips(ships);
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
                placeACC(rotation, x, y, ships);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Aircraft Carrier in a place that was already taken by another ship! Try again!\n\n");

        }
        return ships;
    }

    public static boolean[][] btsSelection(boolean[][] ships){
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
        printShips(ships);

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
                printShips(ships);
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
                placeBATS(rotation, x, y, ships);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Battleship in a place that was already taken by another ship! Try again!\n\n");

        }

        return ships;
    }

    public static boolean[][] subSelection(boolean[][] ships){
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
        printShips(ships);

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
                printShips(ships);

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
                placeSUB(rotation, x, y, ships);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Submarine in a place that was already taken by another ship! Try again!\n\n");

        }

        return ships;
    }
    
    public static boolean[][] spdSelection(boolean[][] ships){
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
        printShips(ships);

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
                printShips(ships);

                System.out.printf("Select your Speedboat's position!\nYour ship's position is a coordinate on the grid where the 'H' is depending on your rotation\n");
                if(rotation == 0)
                {
                    System.out.printf("Hxx\n\nSelect x between 0 and 8(inclusive)\nx: ");
                    xmin = 0;
                    xmax = 8;
                    ymax = 9;
                    ymin = 0;
                }
                else
                {
                    System.out.printf("x\nx\nH\n\nSelect x between 0 and 9(inclusive)\nx: ");
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

            if(checkSUBplacement(rotation, x, y, ships))
            {
                placeSUB(rotation, x, y, ships);
                retry = false;
            }
            else
                System.out.printf("Oops! Looks like you chose to put your Speedboat in a place that was already taken by another ship! Try again!\n\n");

        }

        return ships;
    }





    public static boolean checkACCplacement(int rotation, int x, int y, boolean [][] ships){
        //System.out.printf("Check ACC\nRotation: %d\nx: %d\ny: %d\nend check\n", rotation, x, y);
        
        
        switch(rotation){
                case 0:
                    for(int i = x; i < x+3; i++)
                        if(ships[i][y])
                            return false;
                    for(int i = x; i < x+3; i++)
                        if(ships[i][y+1])
                            return false;
                    break;
                case 1:
                    for(int i = y; i >= y-2; i--)
                        if(ships[x][i])
                            return false;
                    for(int i = y; i >= y-1; i--)
                        if(ships[x+1][i])
                            return false;
                    break;
                case 2:
                    for(int i = x; i>=x-2; i--)
                        if(ships[i][y])
                            return false;
                    for(int i = x; i>=x-1; i--)
                        if(ships[i][y-1])
                            return false;
                    break;
                case 3:
                    for(int i = y; i < y+3; i++)
                        if(ships[x][i])
                            return false;
                    for(int i = y; i < y+2; i++)
                        if(ships[x-1][i])
                            return false;
                    break;
            }
        return true;
    }

    public static boolean checkBATSplacement(int rotation, int x, int y, boolean[][] ships){
        if(rotation == 0)
        {
            for(int i = x; i < x + 4; i++)
                if(ships[i][y])
                    return false;
        }
        else
        {
            for(int i = y; i >= y-3; i--)
                if(ships[x][i])
                    return false;
        }
        return true;
    }

    public static boolean checkSUBplacement(int rotation, int x, int y, boolean [][] ships){
        if(rotation == 0)
        {
            for(int i = x; i < x + 3; i++)
                if(ships[i][y])
                    return false;
        }
        else
        {
            for(int i = y; i >= y-2; i--)
                if(ships[x][i])
                    return false;
        }
        return true;
    }

    public static boolean checkSPDplacement(int rotation, int x, int y, boolean [][] ships){
        if(rotation == 0)
        {
            for(int i = x; i < x + 2; i++)
                if(ships[i][y])
                    return false;
        }
        else
        {
            for(int i = y; i >= y-1; i--)
                if(ships[x][i])
                    return false;
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
            for(int i = y; i >= y-3; i--)
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
            for(int i = y; i >= y-2; i--)
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
            for(int i = y; i >= y-1; i--)
                ships[x][i] = true;
        }
        return ships;
    }


    public static void printSingle(boolean[][] ships, boolean[][] sea)
    {
        System.out.printf("Ships:\t\t\tSea:\n");
        
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

    public static void printMuliplayer(boolean[][] myships, boolean[][] mysea, boolean[][] theyships, boolean[][] theysea)
    {
        System.out.printf("Ships:\t\t\tSea:\n");
        
        for(int i = 9; i >= 0; i--)
        {
            System.out.print(i);
            for(int j = 0; j < 10; j++)
            {
                System.out.print(" ");
                if(myships[j][i])
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
                    if(theyships[j][i])
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
}
