package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;

import java.util.Optional;

public class StudyCafePassOrder {
    private final StudyCafeSeatPass seatPass;
    private final StudyCafeLockerPass lockerPass;

    public StudyCafePassOrder(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        this.seatPass = seatPass;
        this.lockerPass = lockerPass;
    }

    public static StudyCafePassOrder create(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass){
        return new StudyCafePassOrder(seatPass,lockerPass);
    }

    public StudyCafeSeatPass getSeatPass() {
        return this.seatPass;
    }

    public Optional<StudyCafeLockerPass> getLockerPass() {
        return Optional.of(lockerPass);
    }

    public int getDiscountPrice() {
        return seatPass.getDiscountPrice();
    }
    public int getTotalPrice(){
        return seatPass.getPrice() - getDiscountPrice() + (lockerPass != null ? lockerPass.getPrice() : 0);
    }
}
