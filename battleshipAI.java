import java.util.Scanner;
import java.util.Random;

public class battleshipAI 
{
    public static void main(String args[])
    {
        Scanner kin = new Scanner(System.in);
        Random rand = new Random();


        System.out.printf("Hello User! How would you like to play?\n\t1) Play Multiplayer\n\t2) Play against AI\n>");
        int selection = kin.nextInt();
        kin.nextLine();
        if(selection > 2 || selection < 1)
        {
            boolean stupid = true;
            while(stupid)
            {
                System.out.printf("Oh no! That selection isn't an option! Try a number between 1 and 2 (inclusive)\n\t>");
                selection = kin.nextInt();
                kin.nextLine();
                if(selection > 0 && selection < 3)
                    stupid = false;
            }
        }

        if(selection == 1)
            runMultiplayer(kin, rand);
        else if(selection == 2);
            //aI();


        kin.close();
    }


    public static void runMultiplayer(Scanner kin, Random rand)
    {

        battleshipAI.player player1 = new player(1);
        turnScroll();
        battleshipAI.player player2 = new player(2);
        continueScroll();

        
        int first = rand.nextInt(2);
        System.out.printf("It has been randomly decided that player %d will go first!\n", first+1);
        continueScroll();

        boolean unfinishedGame = true;
        int coordx = -1;
        int coordy = -1;
        while(unfinishedGame)
        {
            if(first == 0)
            {
                player1.multTurnTake(player2);
                player2.checkShips(player1.getSea());
                unfinishedGame = checkIfGameUnfinished(player2.getShipsLeft());
                first = 1;
            }
            else
            {
                player2.multTurnTake(player1);
                player1.checkShips(player1.getSea());
                unfinishedGame = checkIfGameUnfinished(player1.getShipsLeft());
                first = 0;
            }
            
            if(unfinishedGame)
                turnScroll();
            else
                continueScroll();
        }
    }


    //returns grid of FALSE booleans
    public static boolean[][] makeBooleanGrid()
    {
        boolean[][] grid = new boolean[10][10];

        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                grid[x][y] = false;
            }
        }

        return grid.clone();
    }

    //returns grid of 0s
    public static int[][] makeIntGrid()
    {
        int[][] grid = new int[10][10];

        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                grid[x][y] = 0;
            }
        }

        return grid.clone();
    }

    //inverts a grid
    public static boolean[][] invertGrid(boolean[][] grid)
    {
        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[x].length; y++)
            {
                grid[x][y] = !grid[x][y];
            }
        }

        return grid;
    }



    //place funcitons
    public static boolean[][] placeACC(boolean[][] grid, int rotation, int xCoordinate, int yCoordinate)
    {
        int x = xCoordinate;
        int y = yCoordinate;
        int r = rotation;

        if(r > 3 || r < 0)
        {
            System.out.println("ACC Invalid rotation: "+rotation);
            return grid;
        }

        switch(r)
        {
            case 0:
                if( x < 0 || x > 7)
                {
                    System.out.printf("ACC X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                    break;
                }
                if( y < 0 || y > 8)
                {
                    System.out.printf("ACC Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                    break;
                }
                for(int i = x; i < x+3; i++)
                {
                    grid[i][y] = true;
                }
                for(int i = x; i < x+2; i++)
                {
                    grid[i][y+1] = true;
                }
                break;
            case 1:
                if( x < 0 || x > 8)
                {
                    System.out.printf("ACC X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                    break;
                }
                if( y < 2 || y > 9)
                {
                    System.out.printf("ACC Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                    break;
                }
                for(int i = y; i > y-3; i--)
                {
                    grid[x][i] = true;
                }
                for(int i = y; i > y-2; i--)
                {
                    grid[x+1][i] = true;
                }
                break;
            case 2:
                if( x < 2 || x > 9)
                {
                    System.out.printf("ACC X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                    break;
                }
                if( y < 0 || y > 8)
                {
                    System.out.printf("ACC Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                    break;
                }
                for(int i = x; i > x-3; i--)
                {
                    grid[i][y] = true;
                }
                for(int i = x; i > x-2; i--)
                {
                    grid[i][y-1] = true;;
                }
                break;
            case 3:
                if( x < 1 || x > 9)
                {
                    System.out.printf("ACC X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                    break;
                }
                if( y < 0 || y > 7)
                {
                    System.out.printf("ACC Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                    break;
                }
                for(int i = y; i < y+3; i++)
                {
                    grid[x][i] = true;
                }
                for(int i = y; i < y+2; i++)
                {
                    grid[x-1][i] = true;
                }
                break;
        }

        return grid;
    }
    
    public static boolean[][] placeBTS(boolean[][] grid, int rotation, int xCoordinate, int yCoordinate)
    {
        int x = xCoordinate;
        int y = yCoordinate;
        int r = rotation;

        if(r > 1 || r < 0)
        {
            System.out.println("BTS Invalid rotation: "+rotation);
            return grid;
        }
        if(r == 0)
        {
            if(x < 0 || x > 6)
            {
                System.out.printf("BTS X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                return grid;
            }
            if(y < 0 || y > 9)
            {
                System.out.printf("BTS Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                return grid;
            }
            for(int i = x; i < x+4; i++)
            {
                grid[i][y] = true;
            }
        }
        else
        {
            if(x < 0 || x > 9)
            {
                System.out.printf("BTS X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                return grid;
            }
            if(y < 0 || y > 6)
            {
                System.out.printf("BTS Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                return grid;
            }
            for(int i = y; i < y + 4; i++)
            {
                grid[x][i] = true;
            }
        }

        return grid;
    }

    public static boolean[][] placeSUB(boolean[][] grid, int rotation, int xCoordinate, int yCoordinate)
    {
        int x = xCoordinate;
        int y = yCoordinate;
        int r = rotation;

        if(r > 1 || r < 0)
        {
            System.out.println("SUB Invalid rotation: "+rotation);
            return grid;
        }
        if(r == 0)
        {
            if(x < 0 || x > 7)
            {
                System.out.printf("SUB X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                return grid;
            }
            if(y < 0 || y > 9)
            {
                System.out.printf("SUB Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                return grid;
            }
            for(int i = x; i < x+3; i++)
            {
                grid[i][y] = true;
            }
        }
        else
        {
            if(x < 0 || x > 9)
            {
                System.out.printf("SUB X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                return grid;
            }
            if(y < 0 || y > 7)
            {
                System.out.printf("SUB Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                return grid;
            }
            for(int i = y; i < y + 3; i++)
            {
                grid[x][i] = true;
            }
        }

        return grid;
    }

    public static boolean[][] placeSPD(boolean[][] grid, int rotation, int xCoordinate, int yCoordinate)
    {
        int x = xCoordinate;
        int y = yCoordinate;
        int r = rotation;

        if(r > 1 || r < 0)
        {
            System.out.println("SUB Invalid rotation: "+rotation);
            return grid;
        }
        if(r == 0)
        {
            if(x < 0 || x > 8)
            {
                System.out.printf("SPD X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                return grid;
            }
            if(y < 0 || y > 9)
            {
                System.out.printf("SPD Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                return grid;
            }
            for(int i = x; i < x+2; i++)
            {
                grid[i][y] = true;
            }
        }
        else
        {
            if(x < 0 || x > 9)
            {
                System.out.printf("SPD X coordinate is out of bounds! X: %d\nR: %d\n", x, r);
                return grid;
            }
            if(y < 0 || y > 8)
            {
                System.out.printf("SPD Y coordinate is out of bounds! Y: %d\nR: %d\n", y, r);
                return grid;
            }
            for(int i = y; i < y + 2; i++)
            {
                grid[x][i] = true;
            }
        }

        return grid;
    }



    //check functions
    public static boolean checkACCPlacement(boolean[] shipsLeft, int[][] shipLocations, int rotation, int xCoordinate, int yCoordinate)
    {
        boolean[][] grid = makeBooleanGrid();

        if(shipsLeft[0])
            System.out.printf("Attempted to check ACC after data shows ACC is placed");
        else
        {
            if(shipsLeft[1])
            {
                grid = placeBTS(grid, shipLocations[1][0], shipLocations[1][1], shipLocations[1][2]);
            }
            if(shipsLeft[2])
            {
                grid = placeSUB(grid, shipLocations[2][0], shipLocations[2][1], shipLocations[2][2]);
            }
            if(shipsLeft[3])
            {
                grid = placeSPD(grid, shipLocations[3][0], shipLocations[3][1], shipLocations[3][2]);
            }


            int r = rotation;
            int x = xCoordinate;
            int y = yCoordinate;


            if(r < 0 || r > 3)
            {
                System.out.printf("Attempted to check invalid ACC Rotation: %d", r);
                return false;
            }

            switch(r)
            {
                case 0:
                    if(x < 0 || x > 7)
                    {
                        System.out.printf("Attempted to check invalid ACC X value!\nX: %d\nR: %d", x, r);
                        return false;
                    }
                    if(y < 0 || y > 9)
                    {
                        System.out.printf("Attempted to check invalid ACC Y value!\nY: %d\nR: %d", y, r);
                        return false;
                    }
                    for(int i = x; i < x+3; i++)
                    {
                        if(grid[i][y])
                        {
                            return false;
                        }
                        
                    }
                    for(int i = x; i < x+2; i++)
                    {
                        if(grid[i][y+1])
                        {
                            return false;
                        }
                    }
                    break;
                case 1:
                    if(x < 0 || x > 8)
                    {
                        System.out.printf("Attempted to check invalid ACC X value!\nX: %d\nR: %d", x, r);
                        return false;
                    }
                    if(y < 2 || y > 9)
                    {
                        System.out.printf("Attempted to check invalid ACC Y value!\nY: %d\nR: %d", y, r);
                        return false;
                    }
                    for(int i = y; i > y-3; i--)
                    {
                        if(grid[x][i])
                        {
                            return false;
                        }
                        
                    }
                    for(int i = y; i > y-2; i--)
                    {
                        if(grid[x+1][i])
                        {
                            return false;
                        }
                    }
                    break;
                case 2:
                    if(x < 2 || x > 9)
                    {
                        System.out.printf("Attempted to check invalid ACC X value!\nX: %d\nR: %d", x, r);
                        return false;
                    }
                    if(y < 1 || y > 9)
                    {
                        System.out.printf("Attempted to check invalid ACC Y value!\nY: %d\nR: %d", y, r);
                        return false;
                    }
                    for(int i = x; i > x-3; i--)
                    {
                        if(grid[i][y])
                        {
                            return false;
                        }
                        
                    }
                    for(int i = x; i > x-2; i--)
                    {
                        if(grid[i][y-1])
                        {
                            return false;
                        }
                    }
                    break;
                case 3:
                    if(x < 1 || x > 9)
                    {
                        System.out.printf("Attempted to check invalid ACC X value!\nX: %d\nR: %d", x, r);
                        return false;
                    }
                    if(y < 0 || y > 7)
                    {
                        System.out.printf("Attempted to check invalid ACC Y value!\nY: %d\nR: %d", y, r);
                        return false;
                    }
                    for(int i = y; i < y+3; i++)
                    {
                        if(grid[x][i])
                        {
                            return false;
                        }
                        
                    }
                    for(int i = y; i < y+2; i++)
                    {
                        if(grid[x-1][i])
                        {
                            return false;
                        }
                        
                    }
                    break;
            }

        }
        return true;
    }

    public static boolean checkBTSPlacement(boolean[] shipsLeft, int[][] shipLocations, int rotation, int xCoordinate, int yCoordinate)
    {
        boolean[][] grid = makeBooleanGrid();

        if(shipsLeft[1])
            System.out.printf("Attempted to check BTS after data shows BTS is placed");
        else
        {
            if(shipsLeft[0])
            {
                grid = placeACC(grid, shipLocations[0][0], shipLocations[0][1], shipLocations[0][2]);
            }
            if(shipsLeft[2])
            {
                grid = placeSUB(grid, shipLocations[2][0], shipLocations[2][1], shipLocations[2][2]);
            }
            if(shipsLeft[3])
            {
                grid = placeSPD(grid, shipLocations[3][0], shipLocations[3][1], shipLocations[3][2]);
            }

            int r = rotation;
            int x = xCoordinate;
            int y = yCoordinate;

            if(r < 0 || r > 1)
            {
                System.out.printf("Attempted to check invalid BTS Rotation: %d", r);
                return false;
            }

            if(r == 0)
            {
                if(x < 0 || x > 6)
                {
                    System.out.printf("Attempted to check invalid BTS X value!\nX: %d\nR: %d", x, r);
                    return false;
                }
                if(y < 0 || y > 9)
                {
                    System.out.printf("Attempted to check invalid BTS Y value!\nY: %d\nR: %d", y, r);
                    return false;
                }
                for(int i = x; i < x+4; i++)
                {
                    if(grid[i][y])
                    {
                        return false;
                    }
                    
                }
            }
            else
            {
                if(x < 0 || x > 9)
                {
                    System.out.printf("Attempted to check invalid BTS X value!\nX: %d\nR: %d", x, r);
                    return false;
                }
                if(y < 0 || y > 6)
                {
                    System.out.printf("Attempted to check invalid BTS Y value!\nY: %d\nR: %d", y, r);
                    return false;
                }
                for(int i = y; i < y+4; i++)
                {
                    if(grid[x][i])
                    {
                        return false;
                    }
                    
                }
            }

        }
        return true;
    }

    public static boolean checkSUBPlacement(boolean[] shipsLeft, int[][] shipLocations, int rotation, int xCoordinate, int yCoordinate)
    {
        boolean[][] grid = makeBooleanGrid();

        if(shipsLeft[2])
            System.out.printf("Attempted to check SUB after data shows SUB is placed");
        else
        {
            if(shipsLeft[0])
            {
                grid = placeACC(grid, shipLocations[0][0], shipLocations[0][1], shipLocations[0][2]);
            }
            if(shipsLeft[1])
            {
                grid = placeBTS(grid, shipLocations[1][0], shipLocations[1][1], shipLocations[1][2]);
            }
            if(shipsLeft[3])
            {
                grid = placeSPD(grid, shipLocations[3][0], shipLocations[3][1], shipLocations[3][2]);
            }

            int r = rotation;
            int x = xCoordinate;
            int y = yCoordinate;

            if(r < 0 || r > 1)
            {
                System.out.printf("Attempted to check invalid SUB Rotation: %d", r);
                return false;
            }

            if(r == 0)
            {
                if(x < 0 || x > 7)
                {
                    System.out.printf("Attempted to check invalid SUB X value!\nX: %d\nR: %d", x, r);
                    return false;
                }
                if(y < 0 || y > 9)
                {
                    System.out.printf("Attempted to check invalid SUB Y value!\nY: %d\nR: %d", y, r);
                    return false;
                }
                for(int i = x; i < x+3; i++)
                {
                    if(grid[i][y])
                    {
                        return false;
                    }
                    
                }
            }
            else
            {
                if(x < 0 || x > 9)
                {
                    System.out.printf("Attempted to check invalid SUB X value!\nX: %d\nR: %d", x, r);
                    return false;
                }
                if(y < 0 || y > 7)
                {
                    System.out.printf("Attempted to check invalid SUB Y value!\nY: %d\nR: %d", y, r);
                    return false;
                }
                for(int i = y; i < y+3; i++)
                {
                    if(grid[x][i])
                    {
                        return false;
                    }
                    
                }
            }

        }
        return true;
    }

    public static boolean checkSPDPlacement(boolean[] shipsLeft, int[][] shipLocations, int rotation, int xCoordinate, int yCoordinate)
    {
        boolean[][] grid = makeBooleanGrid();

        if(shipsLeft[3])
            System.out.printf("Attempted to check SPD after data shows SUB is placed");
        else
        {
            if(shipsLeft[0])
            {
                grid = placeACC(grid, shipLocations[0][0], shipLocations[0][1], shipLocations[0][2]);
            }
            if(shipsLeft[1])
            {
                grid = placeBTS(grid, shipLocations[1][0], shipLocations[1][1], shipLocations[1][2]);
            }
            if(shipsLeft[2])
            {
                grid = placeSUB(grid, shipLocations[2][0], shipLocations[2][1], shipLocations[2][2]);
            }

            int r = rotation;
            int x = xCoordinate;
            int y = yCoordinate;

            if(r < 0 || r > 1)
            {
                System.out.printf("Attempted to check invalid SPD Rotation: %d", r);
                return false;
            }

            if(r == 0)
            {
                if(x < 0 || x > 8)
                {
                    System.out.printf("Attempted to check invalid SPD X value!\nX: %d\nR: %d", x, r);
                    return false;
                }
                if(y < 0 || y > 9)
                {
                    System.out.printf("Attempted to check invalid SPD Y value!\nY: %d\nR: %d", y, r);
                    return false;
                }
                for(int i = x; i < x+2; i++)
                {
                    if(grid[i][y])
                    {
                        return false;
                    }
                }
            }
            else
            {
                if(x < 0 || x > 9)
                {
                    System.out.printf("Attempted to check invalid SPD X value!\nX: %d\nR: %d", x, r);
                    return false;
                }
                if(y < 0 || y > 8)
                {
                    System.out.printf("Attempted to check invalid SPD Y value!\nY: %d\nR: %d", y, r);
                    return false;
                }
                for(int i = y; i < y+2; i++)
                {
                    if(grid[x][i])
                    {
                        return false;
                    }
                    
                }
            }

        }
        return true;
    }

    public static boolean checkIfGameUnfinished(boolean[] shipsLeft)
    {
        if(shipsLeft[0]||shipsLeft[1]||shipsLeft[2]||shipsLeft[3])
            return true;
        return false;
    }


    //print and scroll functions
    public static void printFleet(boolean[] shipsLeft, int[][] shipLocations)
    {
        System.out.printf("Key:\n\tA: Aircraft Carrier\n\tB: Battleship\n\tU: Submarine\n\tS: Speedboat\nCurrent Fleet:\n");
        int[][] fleet = makeIntGrid();

        if(shipsLeft[0])
        {
            int accx = shipLocations[0][1];
            int accy = shipLocations[0][2];
            switch(shipLocations[0][0])
            {
                case 0:
                    for(int i = accx; i < accx+3; i++)
                    {
                        fleet[i][accy] += 5;
                    }
                    for(int i = accx; i < accx+2; i++)
                    {
                        fleet[i][accy+1] += 5;
                    }
                    break;
                case 1:
                    for(int i = accy; i > accy-3; i--)
                    {
                        fleet[accx][i] += 5;
                    }
                    for(int i = accy; i > accy-2; i--)
                    {
                        fleet[accx+1][i] += 5;
                    }
                    break;
                case 2:
                    for(int i = accx; i > accx -3; i--)
                    {
                        fleet[i][accy] += 5;
                    }
                    for(int i = accx; i > accx -2; i--)
                    {
                        fleet[i][accy-1] += 5;
                    }
                    break;
                case 3:
                    for(int i = accy; i < accy+3; i++)
                    {
                        fleet[accx][i] += 5;
                    }
                    for(int i = accy; i < accy+2; i++)
                    {
                        fleet[accx-1][i] += 5;
                    }
                    break;
            }
        }
        if(shipsLeft[1])
        {
            if(shipLocations[1][0] == 0)
            {
                for(int i = shipLocations[1][1]; i < shipLocations[1][1]+4; i++)
                    fleet[i][shipLocations[1][2]] += 4;
            }
            else
            {
                for(int i = shipLocations[1][2]; i < shipLocations[1][2]+4; i++)
                    fleet[shipLocations[1][1]][i] += 4;
            }
        }
        if(shipsLeft[2])
        {
            if(shipLocations[2][0] == 0)
            {
                for(int i = shipLocations[2][1]; i < shipLocations[2][1]+3; i++)
                    fleet[i][shipLocations[2][2]] += 3;
            }
            else
            {
                for(int i = shipLocations[2][2]; i < shipLocations[2][2]+3; i++)
                    fleet[shipLocations[2][1]][i] += 3;
            }
        }
        if(shipsLeft[3])
        {
            if(shipLocations[3][0] == 0)
            {
                for(int i = shipLocations[3][1]; i < shipLocations[3][1]+2; i++)
                    fleet[i][shipLocations[3][2]] += 2;
            }
            else
            {
                for(int i = shipLocations[3][2]; i < shipLocations[3][2]+2; i++)
                    fleet[shipLocations[3][1]][i] += 2;
            }
        }


        for(int y = 9; y > -1; y--)
        {
            System.out.printf("%d ", y);
            for(int x = 0; x < 10; x++)
            {
                switch(fleet[x][y])
                {
                    case 0:
                        System.out.printf("  ");
                        break;
                    case 2:
                        System.out.printf("S ");
                        break;
                    case 3:
                        System.out.printf("U ");
                        break;
                    case 4:
                        System.out.printf("B ");
                        break;
                    case 5:
                        System.out.printf("A ");
                        break;
                    default:
                        System.out.printf("X ");
                        break;
                }
            }
            System.out.println("");
        }
        System.out.printf("  0 1 2 3 4 5 6 7 8 9\n\n");
        
    }

    public static void printSea(boolean[][] sea)
    {
        for(int y = 9; y > -1; y--)
        {
            System.out.printf("%d", y);
            for(int x = 0; x < 10; x++)
            {
                if(sea[x][y])
                {
                    System.out.printf(" X");
                }
                else
                {
                    System.out.printf("  ");
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("  0 1 2 3 4 5 6 7 8 9\n");
    }

    public static void printTurnBoard(boolean[] shipsLeft, int[][] shipLocations, boolean[][] sea, boolean[][] enemySea, boolean[][] enemyShips)
    {

        System.out.printf("Key:\n\tA: Aircraft Carrier\n\tB: Battleship\n\tU: Submarine\n\tS: Speedboat\n\tX: Hit!\n\tD: Destroyed\n\tO: Miss\n");
        System.out.printf("Here's your current board:\n\nYour Fleet:\t\tYour Sea:\n");

        

        int[][] printGrid = makeIntGrid();

        
        int x;
        int y;


        x = shipLocations[0][1];
        y = shipLocations[0][2];
        switch(shipLocations[0][0])
        {
            case 0:
                for(int i = x; i < x+3; i++)
                    printGrid[i][y] = 5;
                y++;
                for(int i = x; i < x+2; i++)
                    printGrid[i][y] = 5;
                break;
            case 1:
                for(int j = y; j > y-3; j--)
                    printGrid[x][j] = 5;
                x++;
                for(int j = y; j > y-2; j--)
                    printGrid[x][j] = 5;
                break;
            case 2:
                for(int i = x; i > x-3; i--)
                    printGrid[i][y] = 5;
                y--;
                for(int i = x; i > x-2; i--)
                    printGrid[i][y] = 5;
                break;
            case 3:
                for(int j = y; j < y+3; j++)
                    printGrid[x][j] = 5;
                x--;
                for(int j = y; j < y+2; j++)
                    printGrid[x][j] = 5;
                break;
        }

        x = shipLocations[1][1];
        y = shipLocations[1][2];
        if(shipLocations[1][0] == 0)
        {
            for(int i = x; i < x+4; i++)
                printGrid[i][y] = 4;
        }
        else
        {
            for(int j = y; j < y+4; j++)
                printGrid[x][j] = 4;
        }

        x = shipLocations[2][1];
        y = shipLocations[2][2];
        if(shipLocations[2][0] == 0)
        {
            for(int i = x; i < x+3; i++)
                printGrid[i][y] = 3;
        }
        else
        {
            for(int j = y; j < y+3; j++)
                printGrid[x][j] = 3;
        }

        x = shipLocations[3][1];
        y = shipLocations[3][2];
        if(shipLocations[3][0] == 0)
        {
            for(int i = x; i < x+2; i++)
                printGrid[i][y] = 2;
        }
        else
        {
            for(int j = y; j < y+2; j++)
                printGrid[x][j] = 2;
        }


        for(x = 0; x < 10; x++)
        {
            for(y = 0; y < 10; y++)
            {
                if(enemySea[x][y])
                {
                    if(printGrid[x][y] != 0)
                    {
                        printGrid[x][y] = 1;
                    }
                    else
                    {
                        printGrid[x][y] = 10;
                    }
                }
            }
        }

        if(!shipsLeft[0])
        {
            for(x = 0; x < 10; x++)
            {
                for(y = 0; y < 10; y++)
                {
                    if(printGrid[x][y] == 5)
                        printGrid[x][y] = -1;
                }
            }
        }

        if(!shipsLeft[1])
        {
            for(x = 0; x < 10; x++)
            {
                for(y = 0; y < 10; y++)
                {
                    if(printGrid[x][y] == 4)
                        printGrid[x][y] = -1;
                }
            }
        }

        if(!shipsLeft[2])
        {
            for(x = 0; x < 10; x++)
            {
                for(y = 0; y < 10; y++)
                {
                    if(printGrid[x][y] == 3)
                        printGrid[x][y] = -1;
                }
            }
        }

        if(!shipsLeft[3])
        {
            for(x = 0; x < 10; x++)
            {
                for(y = 0; y < 10; y++)
                {
                    if(printGrid[x][y] == 2)
                        printGrid[x][y] = -1;
                }
            }
        }

        for(y = 9; y > -1; y--)
        {
            System.out.printf("%d ", y);
            for(x = 0; x < 10; x++)
            {
                switch(printGrid[x][y])
                {
                    case -1:
                        System.out.print("D ");
                        break;
                    case 0:
                        System.out.print("  ");
                        break;
                    case 1:
                        System.out.print("X ");
                        break;
                    case 2:
                        System.out.print("S ");
                        break;
                    case 3:
                        System.out.print("U ");
                        break;
                    case 4:
                        System.out.print("B ");
                        break;
                    case 5:
                        System.out.print("A ");
                        break;
                    case 10:
                        System.out.print("O ");
                        break;
                    default:
                        System.out.print("P ");
                        break;
                }
            }

            System.out.printf("\t%d ", y);
            for(x = 0; x < 10; x++)
            {
                if(sea[x][y])
                {
                    if(enemyShips[x][y])
                    {
                        System.out.print("X ");
                    }
                    else
                    {
                        System.out.print("O ");
                    }
                }
                else
                {
                    System.out.print("  ");
                }
            }
            System.out.println("");
        }
        System.out.printf("  0 1 2 3 4 5 6 7 8 9\t  0 1 2 3 4 5 6 7 8 9\n");
    }

    public static void turnScroll()
    {
        Scanner kin = new Scanner(System.in);
        System.out.printf("Press return when you're ready to hand off the comptuer to the other player.\n\t>");
        kin.nextLine();

        for(int i = 0; i < 100; i++)
            System.out.printf("\n");
        System.out.printf("Press return when you're ready to begin.\n\t>");
        kin.nextLine();
    }

    public static void continueScroll()
    {
        Scanner kin = new Scanner(System.in);

        System.out.printf("Press return when you're ready.\n");
        kin.nextLine();
        for(int i = 0; i < 100; i++)
            System.out.printf("\n");
    }



    public static class player
    {
        boolean accb = false;
        int accr = -1;
        int accx = -1;
        int accy = -1;

        boolean btsb = false;
        int btsr = -1;
        int btsx = -1;
        int btsy = -1;

        boolean subb = false;
        int subr = -1;
        int subx = -1;
        int suby = -1;
    
        boolean spdb = false;
        int spdr = -1;
        int spdx = -1;
        int spdy = -1;

        boolean[][] fleet;
        boolean[][] sea;
        int playerNumber = -1;

        public player(int number)
        {
            fleet = makeBooleanGrid();
            sea = makeBooleanGrid();
            playerNumber = number;

            startup(number);
        }

        private void startup(int number)
        {
            //greet player
            Scanner kin = new Scanner(System.in);
            Random rand = new Random();
            System.out.print("Hello player");
            if(number > 0)
                System.out.print(" " + number);
            System.out.println("!");


            System.out.printf("Would you like to choose the placement of your ships?(Yes by default)\n\t(y/n)>");
            String input = kin.nextLine();
            
            input = input.toLowerCase();
            if(input.charAt(0) == 'n')
            {
                boolean unfinished = true;
                int selection;
                while(unfinished)
                {
                    selection = rand.nextInt(4);
                    if(selection == 0 && !accb)
                    {
                        randomPlaceACC();
                    }
                    else if(selection == 1 && !btsb)
                    {
                        randomPlaceBTS();
                    }
                    else if(selection == 2 && !subb)
                    {
                        randomPlaceSUB();
                    }
                    else if(selection == 3 && !spdb)
                    {
                        randomPlaceSPD();
                    }
                    else if(accb && btsb && subb && spdb)
                        unfinished = false;
                }
            }
            else
            {
                shipPlacer();
            }
            System.out.printf("Here is your final fleet!\n");
            printFleet(getShipsLeft(), getShipLocations());


        }

        public void multTurnTake(player other)
        {
            Scanner kin = new Scanner(System.in);
            int[] coords = new int[2];
            System.out.printf("It's your turn player %d!\n", playerNumber);

            printTurnBoard(getShipsLeft(), getShipLocations(), sea, other.getSea(), other.getFleet());

            
            int x = -1, y = -1;
            boolean needRepeat = true;
            while(needRepeat)
            {
                System.out.printf("Choose a firing coordinate!\n");
                System.out.printf("\tX: ");
                x = kin.nextInt();
                kin.nextLine();
                System.out.printf("\tY: ");
                y = kin.nextInt();
                kin.nextLine();

                if(x > -1 && x < 10 && y > -1 && y < 10 && (!sea[x][y]))
                {
                    needRepeat = false;
                }
                else
                {
                    System.out.printf("Inputed coordinates are out of bounds or already taken!\nTry again!\n");
                }
            }

            sea[x][y] = true;

            if(other.getFleet()[x][y])
            {
                System.out.printf("HIT!\n");
            }
            else
            {
                System.out.printf("MISS!\n");
            }

        }


        private void randomPlaceACC()
        {
            boolean check;
            Random rand = new Random();
            int rotation;

            int x;
            int xmin = 100;
            int xmax = -100;

            int y;
            int ymin = 100;
            int ymax = -100;

            boolean unfinished = true;
            while(unfinished)
            {
                rotation = rand.nextInt(4);

                switch(rotation)
                {
                    case 0:
                        xmin = 0;
                        xmax = 7;

                        ymin = 0;
                        ymax = 8;
                        break;
                    case 1:
                        xmin = 0;
                        xmax = 8;

                        ymin = 2;
                        ymax = 9;
                        break;
                    case 2:
                        xmin = 2;
                        xmax = 9;

                        ymin = 1;
                        ymax = 9;
                        break;
                    case 3:
                        xmin = 1;
                        xmax = 9;

                        ymin = 0;
                        ymax = 7;
                        break;
                }

                if(xmin > xmax || ymin > ymax)
                    System.out.println("Minmax Error!!");
                
                x = rand.nextInt((xmax-xmin))+xmin;
                y = rand.nextInt((ymax-ymin))+ymin;
                check = checkACCPlacement(getShipsLeft(), getShipLocations(), rotation, x, y);
                if(check)
                {
                    fleet = placeACC(fleet, rotation, x, y);
                    accb = true;
                    accr = rotation;
                    accx = x;
                    accy = y;
                    unfinished = false;
                }
            }
        }

        private void randomPlaceBTS()
        {
            boolean check;
            Random rand = new Random();
            int rotation;

            int x;
            int xmin = 100;
            int xmax = -100;

            int y;
            int ymin = 100;
            int ymax = -100;

            boolean unfinished = true;
            while(unfinished)
            {
                rotation = rand.nextInt(2);

                if(rotation == 0)
                {
                    xmin = 0;
                    xmax = 6;

                    ymin = 0;
                    ymax = 9;
                }
                else
                {
                    xmin = 0;
                    xmax = 9;

                    ymin = 0;
                    ymax = 6;
                }

                if(xmin > xmax || ymin > ymax)
                    System.out.println("BTS RandomPlace Minmax Error!!");
                
                x = rand.nextInt((xmax-xmin)+xmin);
                y = rand.nextInt((ymax-ymin)+ymin);

                check = checkBTSPlacement(getShipsLeft(), getShipLocations(), rotation, x, y);
                if(check)
                {
                    fleet = placeBTS(fleet, rotation, x, y);
                    btsb = true;
                    btsr = rotation;
                    btsx = x;
                    btsy = y;
                    unfinished = false;
                }
            }
        }

        private void randomPlaceSUB()
        {
            boolean check;
            Random rand = new Random();
            int rotation;

            int x;
            int xmin = 100;
            int xmax = -100;

            int y;
            int ymin = 100;
            int ymax = -100;

            boolean unfinished = true;
            while(unfinished)
            {
                rotation = rand.nextInt(2);

                if(rotation == 0)
                {
                    xmin = 0;
                    xmax = 7;

                    ymin = 0;
                    ymax = 9;
                }
                else
                {
                    xmin = 0;
                    xmax = 9;

                    ymin = 0;
                    ymax = 7;
                }

                if(xmin > xmax || ymin > ymax)
                    System.out.println("SUB RandomPlace Minmax Error!!");
                
                x = rand.nextInt((xmax-xmin)+xmin);
                y = rand.nextInt((ymax-ymin)+ymin);

                check = checkSUBPlacement(getShipsLeft(), getShipLocations(), rotation, x, y);
                if(check)
                {
                    fleet = placeSUB(fleet, rotation, x, y);
                    subb = true;
                    subr = rotation;
                    subx = x;
                    suby = y;
                    unfinished = false;
                }
            }
        }

        private void randomPlaceSPD()
        {
            boolean check;
            Random rand = new Random();
            int rotation;

            int x;
            int xmin = 100;
            int xmax = -100;

            int y;
            int ymin = 100;
            int ymax = -100;

            boolean unfinished = true;
            while(unfinished)
            {
                rotation = rand.nextInt(2);

                if(rotation == 0)
                {
                    xmin = 0;
                    xmax = 8;

                    ymin = 0;
                    ymax = 9;
                }
                else
                {
                    xmin = 0;
                    xmax = 9;

                    ymin = 0;
                    ymax = 8;
                }

                if(xmin > xmax || ymin > ymax)
                    System.out.println("SPD RandomPlace Minmax Error!!");
                
                x = rand.nextInt((xmax-xmin)+xmin);
                y = rand.nextInt((ymax-ymin)+ymin);

                check = checkSPDPlacement(getShipsLeft(), getShipLocations(), rotation, x, y);
                if(check)
                {
                    fleet = placeSPD(fleet, rotation, x, y);
                    spdb = true;
                    spdr = rotation;
                    spdx = x;
                    spdy = y;
                    unfinished = false;
                }
            }
        }


        private void chooseACCPlacement()
        {
            Scanner kin = new Scanner(System.in);
            boolean stupid = true;
            while(stupid){
                System.out.printf("You've selected the Aircraft Carrier!\nWould you like to choose the place the aircraft carrier? Selecting NO will randomly place the carrier.(yes by default)\n\t(y/n)>");
                String input = kin.nextLine();
                input = input.toLowerCase();
                if(input.charAt(0) == 'n')
                {
                    randomPlaceACC();
                    return;
                }
                else
                {
                    boolean unselected = true;
                    
                    int rotation = -1;
                    int x = -1;
                    int y = -1;
                    
                    
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("Choose your orientation:\n");
                        System.out.printf("0)    \t\t1) xx\t\t2)    \t\t3)  x\n");
                        System.out.printf("   xx \t\t   xx\t\t   xxx\t\t   xx\n");
                        System.out.printf("   xxx\t\t   x \t\t    xx\t\t   xx\n\n\t>");

                        rotation = kin.nextInt();
                        kin.nextLine();
                        if(rotation > -1 && rotation < 4)
                        {
                            unselected = false;
                        }
                        else
                        {
                            System.out.printf("Oops! You've selected an invalid rotation! Try again!\n");
                        }
                    }
                    unselected = true;
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("According to your selected rotation, your Aircraft Carrier will look like this.\nThe H is the point that determins your Carrier's placement on the map.\n");
                    
                        int xmin = 100;
                        int xmax = -100;

                        int ymin = 100;
                        int ymax = -100;

                        switch(rotation)
                        {
                            case 0:
                                System.out.printf("\txx\n\tHxx\n\n");

                                xmin = 0;
                                xmax = 7;

                                ymin = 0;
                                ymax = 8;
                                break;
                            case 1:
                                System.out.printf("\tHx\n\txx\n\tx\n\n");

                                xmin = 0;
                                xmax = 8;

                                ymin = 2;
                                ymax = 9;
                                break;
                            case 2:
                                System.out.printf("\txxH\n\t xx\n\n");

                                xmin = 2;
                                xmax = 9;

                                ymin = 1;
                                ymax = 9;
                                break;
                            case 3:
                                System.out.printf("\t x\n\txx\n\txH\n\n");

                                xmin = 1;
                                xmax = 9;

                                ymin = 0;
                                ymax = 7;
                                break;
                            default:
                                System.out.printf("Rotation Error!!");
                                break;
                        }

                        System.out.printf("Select an X value between %d and %d(inclusive)\n\t>", xmin, xmax);
                        x = kin.nextInt();
                        kin.nextLine();
                        System.out.printf("Select a Y value between %d and %d(inclusive)\n\t>", ymin, ymax);
                        y = kin.nextInt();
                        kin.nextLine();

                        if(x < xmin || x > xmax || y < ymin || y > ymax)
                        {
                            System.out.printf("Oops! It looks like you've inputed an invalid coordinate!\nStart Over?(No by default)\n\t(y/n)>");
                            input = kin.nextLine();
                            input = input.toLowerCase();
                            if(input.charAt(0) == 'y')
                            {
                                unselected = false;
                            }
                        }
                        else
                        {
                            if(checkACCPlacement(getShipsLeft(), getShipLocations(), rotation, x, y))
                            {
                                unselected = false;
                                stupid = false;
                                accb = true;
                                accr = rotation;
                                accx = x;
                                accy = y;
                                placeACC(fleet, rotation, x, y);
                            }
                            else
                            {
                                System.out.printf("Oops! It looks like you've inputed a coordinate that overlaps another ship!\nStart Over?(No by default)\n\t(y/n)>");
                                input = kin.nextLine();
                                input = input.toLowerCase();
                                if(input.charAt(0) == 'y')
                                {
                                    unselected = false;
                                }
                            }
                        }
                    }
                }
            }
            
        }

        private void chooseBTSPlacement()
        {
            Scanner kin = new Scanner(System.in);
            boolean stupid = true;
            while(stupid){
                System.out.printf("You've selected the Battleship!\nWould you like to choose the place the battleship? Selecting NO will randomly place the battleship.(yes by default)\n\t(y/n)>");
                String input = kin.nextLine();
                input = input.toLowerCase();
                if(input.charAt(0) == 'n')
                {
                    randomPlaceBTS();
                    return;
                }
                else
                {
                    boolean unselected = true;
                    
                    int rotation = -1;
                    int x = -1;
                    int y = -1;
                    
                    
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("Choose your orientation:\n");
                        System.out.printf("0)     \t\t1) x\n");
                        System.out.printf("   Hxxx\t\t   x\n");
                        System.out.printf("       \t\t   x\n");
                        System.out.printf("       \t\t   H \n\n\t>");

                        rotation = kin.nextInt();
                        kin.nextLine();
                        if(rotation > -1 && rotation < 2)
                        {
                            unselected = false;
                        }
                        else
                        {
                            System.out.printf("Oops! You've selected an invalid rotation! Try again!\n");
                        }
                    }
                    unselected = true;
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("According to your selected rotation, your Battleship will look like this.\nThe H is the point that determins your Carrier's placement on the map.\n");
                    
                        int xmin = 100;
                        int xmax = -100;

                        int ymin = 100;
                        int ymax = -100;

                        if(rotation == 0)
                        {
                            xmin = 0;
                            xmax = 6;

                            ymin = 0;
                            ymax = 9;
                        }
                        else
                        {
                            xmin = 0;
                            xmax = 9;

                            ymin = 0;
                            ymax = 6;
                        }

                        System.out.printf("Select an X value between %d and %d(inclusive)\n\t>", xmin, xmax);
                        x = kin.nextInt();
                        kin.nextLine();
                        System.out.printf("Select a Y value between %d and %d(inclusive)\n\t>", ymin, ymax);
                        y = kin.nextInt();
                        kin.nextLine();

                        if(x < xmin || x > xmax || y < ymin || y > ymax)
                        {
                            System.out.printf("Oops! It looks like you've inputed an invalid coordinate!\nStart Over?(No by default)\n\t(y/n)>");
                            input = kin.nextLine();
                            input = input.toLowerCase();
                            if(input.charAt(0) == 'y')
                            {
                                unselected = false;
                            }
                        }
                        else
                        {
                            if(checkBTSPlacement(getShipsLeft(), getShipLocations(), rotation, x, y))
                            {
                                unselected = false;
                                stupid = false;
                                btsb = true;
                                btsr = rotation;
                                btsx = x;
                                btsy = y;
                                placeBTS(fleet, rotation, x, y);
                            }
                            else
                            {
                                System.out.printf("Oops! It looks like you've inputed a coordinate that overlaps another ship!\nStart Over?(No by default)\n\t(y/n)>");
                                input = kin.nextLine();
                                input = input.toLowerCase();
                                if(input.charAt(0) == 'y')
                                {
                                    unselected = false;
                                }
                            }
                        }
                    }
                }
            }
            
        }

        private void chooseSUBPlacement()
        {
            Scanner kin = new Scanner(System.in);
            boolean stupid = true;
            while(stupid){
                System.out.printf("You've selected the Submarine!\nWould you like to choose the place the submarine? Selecting NO will randomly place the submarine.(yes by default)\n\t(y/n)>");
                String input = kin.nextLine();
                input = input.toLowerCase();
                if(input.charAt(0) == 'n')
                {
                    randomPlaceSUB();
                    return;
                }
                else
                {
                    boolean unselected = true;
                    
                    int rotation = -1;
                    int x = -1;
                    int y = -1;
                    
                    
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("Choose your orientation:\n");
                        System.out.printf("0)    \t\t1) x\n");
                        System.out.printf("   Hxx\t\t   x\n");
                        System.out.printf("      \t\t   H \n\n\t>");

                        rotation = kin.nextInt();
                        kin.nextLine();
                        if(rotation > -1 && rotation < 2)
                        {
                            unselected = false;
                        }
                        else
                        {
                            System.out.printf("Oops! You've selected an invalid rotation! Try again!\n");
                        }
                    }
                    unselected = true;
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("According to your selected rotation, your Submarine will look like this.\nThe H is the point that determins your Carrier's placement on the map.\n");
                    
                        int xmin = 100;
                        int xmax = -100;

                        int ymin = 100;
                        int ymax = -100;

                        if(rotation == 0)
                        {
                            xmin = 0;
                            xmax = 7;

                            ymin = 0;
                            ymax = 9;
                        }
                        else
                        {
                            xmin = 0;
                            xmax = 9;

                            ymin = 0;
                            ymax = 7;
                        }

                        System.out.printf("Select an X value between %d and %d(inclusive)\n\t>", xmin, xmax);
                        x = kin.nextInt();
                        kin.nextLine();
                        System.out.printf("Select a Y value between %d and %d(inclusive)\n\t>", ymin, ymax);
                        y = kin.nextInt();
                        kin.nextLine();

                        if(x < xmin || x > xmax || y < ymin || y > ymax)
                        {
                            System.out.printf("Oops! It looks like you've inputed an invalid coordinate!\nStart Over?(No by default)\n\t(y/n)>");
                            input = kin.nextLine();
                            input = input.toLowerCase();
                            if(input.charAt(0) == 'y')
                            {
                                unselected = false;
                            }
                        }
                        else
                        {
                            if(checkSUBPlacement(getShipsLeft(), getShipLocations(), rotation, x, y))
                            {
                                unselected = false;
                                stupid = false;
                                subb = true;
                                subr = rotation;
                                subx = x;
                                suby = y;
                                placeSUB(fleet, rotation, x, y);
                            }
                            else
                            {
                                System.out.printf("Oops! It looks like you've inputed a coordinate that overlaps another ship!\nStart Over?(No by default)\n\t(y/n)>");
                                input = kin.nextLine();
                                input = input.toLowerCase();
                                if(input.charAt(0) == 'y')
                                {
                                    unselected = false;
                                }
                            }
                        }
                    }
                }
            }
            
        }

        private void chooseSPDPlacement()
        {
            Scanner kin = new Scanner(System.in);
            boolean stupid = true;
            while(stupid){
                System.out.printf("You've selected the Speedboat!\nWould you like to choose the place the speedboat? Selecting NO will randomly place the speedboat.(yes by default)\n\t(y/n)>");
                String input = kin.nextLine();
                input = input.toLowerCase();
                if(input.charAt(0) == 'n')
                {
                    randomPlaceSPD();
                    return;
                }
                else
                {
                    boolean unselected = true;
                    
                    int rotation = -1;
                    int x = -1;
                    int y = -1;
                    
                    
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("Choose your orientation:\n");
                        System.out.printf("0)  \t\t1) x\n");
                        System.out.printf("   Hx\t\t   H\n\n\t>");

                        rotation = kin.nextInt();
                        kin.nextLine();
                        if(rotation > -1 && rotation < 2)
                        {
                            unselected = false;
                        }
                        else
                        {
                            System.out.printf("Oops! You've selected an invalid rotation! Try again!\n");
                        }
                    }
                    unselected = true;
                    while(unselected)
                    {
                        printFleet(getShipsLeft(), getShipLocations());
                        System.out.printf("According to your selected rotation, your Speedboat will look like this.\nThe H is the point that determins your Carrier's placement on the map.\n");
                    
                        int xmin = 100;
                        int xmax = -100;

                        int ymin = 100;
                        int ymax = -100;

                        if(rotation == 0)
                        {
                            xmin = 0;
                            xmax = 8;

                            ymin = 0;
                            ymax = 9;
                        }
                        else
                        {
                            xmin = 0;
                            xmax = 9;

                            ymin = 0;
                            ymax = 8;
                        }

                        System.out.printf("Select an X value between %d and %d(inclusive)\n\t>", xmin, xmax);
                        x = kin.nextInt();
                        kin.nextLine();
                        System.out.printf("Select a Y value between %d and %d(inclusive)\n\t>", ymin, ymax);
                        y = kin.nextInt();
                        kin.nextLine();

                        if(x < xmin || x > xmax || y < ymin || y > ymax)
                        {
                            System.out.printf("Oops! It looks like you've inputed an invalid coordinate!\nStart Over?(No by default)\n\t(y/n)>");
                            input = kin.nextLine();
                            input = input.toLowerCase();
                            if(input.charAt(0) == 'y')
                            {
                                unselected = false;
                            }
                        }
                        else
                        {
                            if(checkSPDPlacement(getShipsLeft(), getShipLocations(), rotation, x, y))
                            {
                                unselected = false;
                                stupid = false;
                                spdb = true;
                                spdr = rotation;
                                spdx = x;
                                spdy = y;
                                placeSPD(fleet, rotation, x, y);
                            }
                            else
                            {
                                System.out.printf("Oops! It looks like you've inputed a coordinate that overlaps another ship!\nStart Over?(No by default)\n\t(y/n)>");
                                input = kin.nextLine();
                                input = input.toLowerCase();
                                if(input.charAt(0) == 'y')
                                {
                                    unselected = false;
                                }
                            }
                        }
                    }
                }
            }
            
        }

        
        private  void shipPlacer()
        {
            Scanner kin = new Scanner(System.in);
            boolean unfinished = true;
            while(unfinished)
            {
                System.out.printf("Select the ship that you want to place:\n");
                if(!accb)
                    System.out.printf("\t1) Aircraft Carrier\n");
                if(!btsb)
                    System.out.printf("\t2) Battleship\n");
                if(!subb)
                    System.out.printf("\t3) Submarine\n");
                if(!spdb)
                    System.out.printf("\t4) Speed Boat\n");
                System.out.printf(">");

                int selection = kin.nextInt();
                kin.nextLine();

                if(selection > 0 && selection < 5)
                {
                    switch(selection)
                    {
                        case 1:
                            if(accb)
                            {
                                System.out.printf("Oops! Looks like your selection wasn't available because it was already placed.\nTry again!\n");
                                break;
                            }
                            chooseACCPlacement();
                            break;
                        case 2:
                            if(btsb)
                            {
                                System.out.printf("Oops! Looks like your selection wasn't available because it was already placed.\nTry again!\n");
                                break;
                            }
                            chooseBTSPlacement();
                            break;
                        case 3:
                            if(subb)
                            {
                                System.out.printf("Oops! Looks like your selection wasn't available because it was already placed.\nTry again!\n");
                                break;
                            }
                            chooseSUBPlacement();
                            break;
                        case 4:
                            if(spdb)
                            {
                                System.out.printf("Oops! Looks like your selection wasn't available because it was already placed.\nTry again!\n");
                                break;
                            }
                            chooseSPDPlacement();
                            break;
                    }

                }
                else
                {
                    System.out.printf("Oops! Looks like your selection was out of bounds! Try again!\n");
                }
                if(accb && btsb && subb && spdb)
                    unfinished = false;
            }
            
        }


        public void checkShips(boolean[][] eSea)
        {
            eSea = invertGrid(eSea);

            int x = accx;
            int y = accy;
            boolean sunk = true;


            switch(accr)
            {
                case 0:
                    for(int i = x; i < x+3; i++)
                    {
                        if(eSea[i][y])
                            sunk = false;
                    }
                    y++;
                    for(int i = x; i < x+2; i++)
                    {
                        if(eSea[i][y])
                            sunk = false;
                    }
                    break;
                case 1:
                    for(int j = y; j > y-3; j--)
                    {
                        if(eSea[x][j])
                            sunk = false;
                    }
                    x++;
                    for(int j = y; j > y-2; j--)
                    {
                        if(eSea[x][j])
                            sunk = false;
                    }
                    break;
                case 2:
                    for(int i = x; i > x-3; i--)
                    {
                        if(eSea[i][y])
                            sunk = false;
                    }
                    y--;
                    for(int i = x; i > x-2; i--)
                    {
                        if(eSea[i][y])
                            sunk = false;
                    }
                    break;
                case 3:
                    for(int j = y; j < y+3; j++)
                    {
                        if(eSea[x][j])
                            sunk = false;
                    }
                    x--;
                    for(int j = y; j < y+2; j++)
                    {
                        if(eSea[x][j])
                            sunk = false;
                    }
                    break;
                default:
                    System.out.printf("CHECKSHIPS ACC ROTATION ERROR");
                    break;
            }

            if(sunk)
                accb = false;
            
            sunk = true;
            x = btsx;
            y = btsy;
            if(btsr == 0)
            {
                for(int i = x; i < x+4; i++)
                {
                    if(eSea[i][y])
                        sunk = false;
                }
            }
            else
            {
                for(int j = y; j < y+4; j++)
                {
                    if(eSea[x][j])
                        sunk = false;
                }
            }
            if(sunk)
                btsb = false;

            sunk = true;
            x = subx;
            y = suby;
            if(subr == 0)
            {
                for(int i = x; i < x+3; i++)
                {
                    if(eSea[i][y])
                        sunk = false;
                }
            }
            else
            {
                for(int j = y; j < y+3; j++)
                {
                    if(eSea[x][j])
                        sunk = false;
                }
            }
            if(sunk)
                subb = false;
            
            sunk = true;
            x = spdx;
            y = spdy;
            if(spdr == 0)
            {
                for(int i = x; i < x+2; i++)
                {
                    if(eSea[i][y])
                        sunk = false;
                }
            }
            else
            {
                for(int j = y; j < y+2; j++)
                {
                    if(eSea[x][j])
                        sunk = false;
                }
            }
            if(sunk)
                spdb = false;
        }


        public boolean[] getShipsLeft()
        {
            boolean[] numSunk = new boolean[4];

            numSunk[0] = accb;
            numSunk[1] = btsb;
            numSunk[2] = subb;
            numSunk[3] = spdb;

            return numSunk;
        }
    
        public int[][] getShipLocations()
        {
            int[][] locations = new int[4][3];

            locations[0][0] = accr;
            locations[0][1] = accx;
            locations[0][2] = accy;

            locations[1][0] = btsr;
            locations[1][1] = btsx;
            locations[1][2] = btsy;

            locations[2][0] = subr;
            locations[2][1] = subx;
            locations[2][2] = suby;

            locations[3][0] = spdr;
            locations[3][1] = spdx;
            locations[3][2] = spdy;

            return locations;
        }
    
        public boolean[][] getFleet()
        {
            return fleet.clone();
        }

        public boolean[][] getSea()
        {
            return sea.clone();
        }


        public void shoot(int x, int y)
        {
            sea[x][y] = true;
        }


        public void printFleetForOutput()
        {
            printFleet(getShipsLeft(), getShipLocations());
        }

        public void printSeaForOutput()
        {

        }
    }

    public class HeatmapAI
    {

    }
}
