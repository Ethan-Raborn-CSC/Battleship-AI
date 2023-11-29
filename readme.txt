main
    program starts in main which welcoms the user and gives the option to split into 5 different options.
    main also has some garbage input correction
        1. singlePlayerRun()
        2. multiplayerRun()
        3. playAgainstAI();
        4. watchAI();
        5. quit (not implemented yet)

Single Player Board
    This is an option that was and is mostly for my troubleshooting. It is mostly just there to place ships down and see if everything lines up.
    Good for testing some basic things like ship placement and firing positions.

multiplayerRun
    This is an actual game for multiplayer! Of course, this program only works on 1 machine, so it's meant for the computer to be passed back
        and fourth between 2 players untill 1 wins.
    This mode should be very usefull for implementing and testing the AI.

Play an AI
    Yet to be implemented
    Meant for a human to play against the AI.

Watch AI
    Meant for watching 2 AI play against eachother. Probably good for training/testing.
    Yet to be implemented


Major functions
    singleTableRun
        for Single Player Board
        asks user if they want random ship placement
            if so, randomShipPlacer is uesed
            if not, shipSelection is uesed
        uses createNewTable to create sea and ship tables
        prints talbles
            uses printSingle
        loops untill quit
            gets input
            checks if quit
                if so quit
                if not, continue
            assigns input to x
            gets input
            checks if quit
                if so, quit
                if not, continue
            assigns input to y
            checks if x and y are within bounds
                if so
                    assigns shot sea table
                    pritns sea table
                if not
                    gets user to start again at loop
    
    multiplayerRun
        note
            almost everything is hidded through a bunch of nextlines to hide player's screens from eachother
        for a multiplayer game with 2 humans
        meant to be modified for AI sections of project
        asks player 1 if they want to place their ships
             if so, uses randomShipPlacer
             if not, uses shipSelection
        makes sea1 and ships1 tables
            tables for player 1
        asks player 2 if they want to place their ships
             if so, uses randomShipPlacer
             if not, uses shipSelection
        makes sea2 and ships2 tables
            tables for player 2
        makes int first
        uses random booleant to assign 1 or 2 to first
        based on if first is 1 or 2, player 1 or 2 goes first
        flips between first and second player utnill game over
            player is displayed current game state
            player inputs shot coordinates
            checks and displays if hit or miss
            checks if game over
                if so, break loop and display win screens
                if not, continue
                uses checkRemainingShips
        
    







Here's all the helper functions:
    checkRemainingShips
        iterates through a player's ships and their opponents shots to see if the game is over
    
    createNewTable
        makes 10 x 10 binary tables. Just easier than making a whole bunch of actual new tables.
    
    randomShipPlacer
        meant to place all of a player's ships 'randomly'
        Works by
            Going through each ship and
                assigning a 'random' rotation
                assigning 'random' x and y positions
                    within table according to rotation
                uses ship checker functions to see if another ship is in the way
                uses ship placer functions to place ships
            
    shipSelection
        meant to be an alternative to randomShipPlacer
        allows a user to manually place ships on their grid.
        user selects ships untill no ships remain
        uses ship selection functions
            accSelection
            btsSelection
            subSelection
            spdSelection
    
    ship selection functions
        includes
            accSelection
            btsSelection
            subSelection
            spdSelection
        shows user a map of their current ships
            using printShips
        gives options for ship rotation
        shows user a map of their current ships again
        gives user options to pick ship coordinates
        checks if ship cordinates are on top of other ship and makes user start over if so
            uses ship checker functions
        places ship
            uses ship placer functions
    
    ship checker functions
        uses ship orientation and coordinates to check if another ship is in the way of potential placement
        includes
            checkACCplacement
                slightly different from other functions
                    uses switch for rotation
            checkBATSplacement
            checkSUBplacement
            checkSPDplacement
        has options for each rotation
        checks coordinates based on rotation

    ship placer functions
        includes
            placeACC
                slightly different from other functions
                    uses switch for rotation
            placeBATS
            placeSUB
            placeSPD
        works similar to ship checker functions

    print functions
        includes
            printSingle
                meant for singlePlayerRun
                only displays 1 player's information
                does not include functionality for hits/misses
            printShips
                displays only ships
                meant to assist user during manual ship placement
            printMuliplayer
                designed for multiplayer option
                displays 1 player's play information at a time
                includes functionality for misses and hits







Coded mostly in Java as of 2023-11-28. Version:
openjdk version "11.0.11" 2021-04-20
OpenJDK Runtime Environment AdoptOpenJDK-11.0.11+9 (build 11.0.11+9)
OpenJDK 64-Bit Server VM AdoptOpenJDK-11.0.11+9 (build 11.0.11+9, mixed mode)
(Ethan's Machine)