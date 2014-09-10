package pl.themolka.custommobdrop;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.themolka.custommobdrop.api.Item;

public class CustomItem implements Item {
    private final Material material;
    private final int amount;
    private ItemStack item;
    
    public CustomItem(Material material) {
        this.material = material;
        this.amount = 1;
    }
    
    public CustomItem(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }
    
    @Override
    public ItemStack getItem() {
        if (this.item == null) {
            this.item = new ItemStack(material, amount);
        }
        return this.item;
    }
    
    @Override
    public Material getType() {
        return this.material;
    }
    
    @Override
    public int getAmount() {
        return this.amount;
    }
    
    @Override
    public boolean hasName() {
        return this.getItem().getItemMeta().hasDisplayName();
    }
    
    @Override
    public String getName() throws NullPointerException {
        if (this.hasName()) {
           return this.getItem().getItemMeta().getDisplayName();
        } else {
            throw new NullPointerException("name of the item can not be null");
        }
    }
    
    @Override
    public void setName(String name) {
        ItemMeta meta = this.getItem().getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
    }
    
    @Override
    public boolean hasDescription() {
        return this.getItem().getItemMeta().hasLore();
    }
    
    @Override
    public List<String> getDescription() throws NullPointerException {
        if (this.hasDescription()) {
            return this.getItem().getItemMeta().getLore();
        } else {
            throw new NullPointerException("description of the item can not be null");
        }
    }
    
    @Override
    public void setDescription(List<String> description) {
        ItemMeta meta = this.getItem().getItemMeta();
        meta.setLore(description);
        this.item.setItemMeta(meta);
    }
}
