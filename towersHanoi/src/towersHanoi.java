
public class towersHanoi {

    static void hTower(int n, char starting_peg,
                             char destination_peg, char temporary_peg)
    {

        if (n == 1)
        {
            System.out.println("move ring 1 from peg "+
                    starting_peg+" to peg "+destination_peg);
            return;
        }


        hTower(n - 1, starting_peg, temporary_peg, destination_peg);
        System.out.println("Move ring "+ n + " from peg " +
                starting_peg +" to peg " + destination_peg );
        hTower(n - 1, temporary_peg, destination_peg, starting_peg);
    }


    public static void  main(String args[])
    {
        System.out.println("Running Towers of Hanoi with 4 rings");
        int n = 4; // Number of disks
        hTower(n, '1', '3', '2'); // A, B and C are names of rods

 /*       System.out.println("\n Running Towers of Hanoi with 8 rings");
        int m = 8; // Number of disks
        hTower(m, '1', '3', '2'); // A, B and C are names of rods
/*
        System.out.println("\n Running Towers of Hanoi with 16 rings");
        int o = 16; // Number of disks
        hTower(o, '1', '3', '2'); // A, B and C are names of rods
*/

    }
}
