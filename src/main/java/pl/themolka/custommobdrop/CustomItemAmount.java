package pl.themolka.custommobdrop;

import java.util.Random;
import pl.themolka.custommobdrop.api.ItemAmount;

public class CustomItemAmount implements ItemAmount {
    private int min;
    private int max = -1;
    private boolean random;
    
    public CustomItemAmount(int amount) {
        this(amount, -1);
    }
    
    public CustomItemAmount(int min, int max) {
        boolean r = max > min;
        this.min = min;
        this.random = r;
        if (r) {
            this.max = max;
        } else {
            this.max = -1;
        }
    }
    
    @Override
    public int getMin() {
        return this.min;
    }
    
    @Override
    public int getMax() {
        return this.max;
    }
    
    @Override
    public int getRandom() {
        if (!this.isRandom()) {
            return this.getMin();
        } else {
            Random r = new Random();
            int diff = this.getMax() - this.getMin();
            return r.nextInt(diff) + this.getMin();
        }
    }
    
    @Override
    public boolean isRandom() {
        return this.random;
    }
}
