package cleancode.studycafe.tobe.model;

import java.util.List;
import java.util.Optional;

public class StudyCafeLockerPasses {
    private final List<StudyCafeLockerPass> passes;

    public StudyCafeLockerPasses(List<StudyCafeLockerPass> passes) {
        this.passes = passes;
    }
    public static StudyCafeLockerPasses create(List<StudyCafeLockerPass> passes){
        return new StudyCafeLockerPasses(passes);
    }

    public Optional<StudyCafeLockerPass> findPassBy(StudyCafePass pass) {
        return passes.stream()
                .filter(lockerPass -> pass.isSameDurationType(lockerPass))
                .findFirst();
    }
}
