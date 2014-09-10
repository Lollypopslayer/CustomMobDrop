package pl.themolka.custommobdrop.api;

import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import pl.themolka.custommobdrop.CustomMobDrop;

public class CMD {
    protected static List<Mob> mobs;
    
    public static Mob getMob(EntityType entity) {
        for (Mob mob : CMD.getMobs()) {
            if (mob.getType().equals(entity)) {
                return mob;
            }
        }
        return null;
    }
    
    public static List<Mob> getMobs() throws NullPointerException, IndexOutOfBoundsException {
        if (CMD.mobs == null) {
            throw new NullPointerException("List if the mobs can not be null");
        } else if (CMD.mobs.isEmpty()) {
            throw new IndexOutOfBoundsException("No mobs defined in the list");
        }
        return CMD.mobs;
    }
    
    public static int getAmountOfLoadedDrops() throws NullPointerException, IndexOutOfBoundsException{
        int drops = 0;
        for (Mob mob : CMD.getMobs()) {
            drops = drops + mob.getDrops().size();
        }
        return drops;
    }
    
    public static void registerMob(Mob mob) throws IllegalArgumentException {
        if (mob.getType() == null) {
            throw new IllegalArgumentException("entity can not be null");
        } else if (mob.getDrops() == null) {
            throw new IllegalArgumentException("drops can not be null");
        }
        CMD.mobs.add(mob);
    }
    
    public static FileConfiguration getConfigFile() {
        return CustomMobDrop.getInstance().getConfig();
    }
    
    public static String getVersion() {
        return CustomMobDrop.getInstance().version;
    }
}
