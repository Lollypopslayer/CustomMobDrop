package pl.themolka.custommobdrop;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.themolka.custommobdrop.api.Item;
import pl.themolka.custommobdrop.api.ItemAmount;

public class CustomItem implements Item {
    private final Material material;
    private final int minAmount;
    private final int maxAmount;
    private String name;
    private List<String> description;
    
    public CustomItem(Material material) {
        this(material, new CustomItemAmount(1));
    }
    
    public CustomItem(Material material, ItemAmount amount) {
        this.material = material;
        this.minAmount = amount.getMin();
        this.maxAmount = amount.getMax();
    }
    
    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(material, this.getAmount().getRandom());
        ItemMeta meta = item.getItemMeta();
        if (this.hasName()) {
            meta.setDisplayName(this.getName());
        }
        if (this.hasDescription()) {
            meta.setLore(this.getDescription());
        }
        item.setItemMeta(meta);
        return item;
    }
    
    @Override
    public Material getType() {
        return this.material;
    }
    
    @Override
    public ItemAmount getAmount() {
        return new CustomItemAmount(minAmount, maxAmount);
    }
    
    @Override
    public boolean hasName() {
        return this.name != null;
    }
    
    @Override
    public String getName() throws NullPointerException {
        if (this.hasName()) {
           return this.name;
        } else {
            throw new NullPointerException("name of the item can not be null");
        }
    }
    
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean hasDescription() {
        return this.description != null;
    }
    
    @Override
    public List<String> getDescription() throws NullPointerException {
        if (this.hasDescription()) {
            return this.description;
        } else {
            throw new NullPointerException("description of the item can not be null");
        }
    }
    
    @Override
    public void setDescription(List<String> description) {
        this.description = description;
    }
}
