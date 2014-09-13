package pl.themolka.custommobdrop.api;

public interface ItemAmount {
    int getMin();
    
    int getMax();
    
    int getRandom();
    
    boolean isRandom();
}
