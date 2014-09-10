package pl.themolka.custommobdrop.api;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Item {
    ItemStack getItem();
    
    Material getType();
    
    int getAmount();
    
    boolean hasName();
    
    String getName() throws NullPointerException;
    
    void setName(String name);
    
    boolean hasDescription();
    
    List<String> getDescription() throws NullPointerException;
    
    void setDescription(List<String> name);
}
