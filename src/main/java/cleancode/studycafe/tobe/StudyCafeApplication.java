package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobe.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobe.provier.LockerPassProvider;
import cleancode.studycafe.tobe.provier.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();
        SeatPassProvider seatPassProvider = new SeatPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);
        studyCafePassMachine.run();
    }

}
