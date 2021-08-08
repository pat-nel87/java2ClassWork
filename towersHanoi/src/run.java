public class run {

    public static void main(String[] args) {

        hTower pegOne = new hTower();
        hTower pegTwo = new hTower();
        hTower pegThree = new hTower();

        pegOne.addRing(1);
        pegOne.addRing(2);
        pegOne.addRing(3);
        pegTwo.addRing(0);
        pegThree.addRing(0);
        pegOne.moveRings(3, pegThree, pegTwo);




    }


}
