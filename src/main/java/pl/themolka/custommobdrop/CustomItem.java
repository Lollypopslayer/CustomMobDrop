package pl.themolka.custommobdrop;

import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.themolka.custommobdrop.api.Item;
import pl.themolka.custommobdrop.api.ItemAmount;

public class CustomItem implements Item {
    private Material material;
    private int minAmount;
    private int maxAmount;
    private boolean random;
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
    public void setType(Material material) {
        this.material = material;
    }
    
    @Override
    public ItemAmount getAmount() {
        return new CustomItemAmount(minAmount, maxAmount);
    }
    
    @Override
    public void setAmount(ItemAmount amount) {
        this.minAmount = amount.getMin();
        this.maxAmount = amount.getMax();
    }
    
    @Override
    public boolean drop() {
        if (!this.isRandom()) {
            return true;
        } else {
            return new Random().nextBoolean();
        }
    }
    
    @Override
    public boolean isRandom() {
        return this.random;
    }
    
    @Override
    public void setRandom(boolean random) {
        this.random = random;
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
