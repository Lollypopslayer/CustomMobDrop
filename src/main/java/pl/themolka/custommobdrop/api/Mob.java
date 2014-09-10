package pl.themolka.custommobdrop.api;

import java.util.List;
import org.bukkit.entity.EntityType;

public interface Mob {
    List<Item> getDrops();
    
    EntityType getType();
}
