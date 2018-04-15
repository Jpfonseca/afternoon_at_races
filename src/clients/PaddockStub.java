package clients;

import shared_regions.PaddockInterface;

public class PaddockStub implements PaddockInterface {
    @Override
    public boolean proceedToPaddock1() {
        return false;
    }

    @Override
    public void proceedToPaddock2() {

    }

    @Override
    public boolean goCheckHorses1() {
        return false;
    }

    @Override
    public void goCheckHorses2(boolean last) {

    }
}
