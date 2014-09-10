package pl.themolka.custommobdrop;

import java.util.List;
import org.bukkit.entity.EntityType;
import pl.themolka.custommobdrop.api.Item;
import pl.themolka.custommobdrop.api.Mob;

public class CustomMob implements Mob {
    private final List<Item> drops;
    private final EntityType entity;
    
    public CustomMob(List<Item> drops, EntityType entity) {
        this.drops = drops;
        this.entity = entity;
    }
    
    @Override
    public List<Item> getDrops() {
        return this.drops;
    }
    
    @Override
    public EntityType getType() {
        return this.entity;
    }
    
}
