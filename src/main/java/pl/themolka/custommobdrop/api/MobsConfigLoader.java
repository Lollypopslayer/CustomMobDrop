package pl.themolka.custommobdrop.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import pl.themolka.custommobdrop.CustomItem;
import pl.themolka.custommobdrop.CustomMob;
import pl.themolka.custommobdrop.CustomMobDrop;

public class MobsConfigLoader {
    private static final Logger logger = CustomMobDrop.getInstance().getLogger();
    
    public static void load() {
        CMD.mobs = new ArrayList<>();
        FileConfiguration file = CMD.getConfigFile();
        for (String mob : file.getConfigurationSection("mob-drops").getKeys(false)) {
            List<Item> drops = new ArrayList<>();
            
            mob = mob.toUpperCase();
            EntityType entity = null;
            try {
                entity = EntityType.valueOf(mob);
            } catch (NullPointerException ex) {
                MobsConfigLoader.logger.log(Level.INFO, "Failed to read config.yml: {0}",
                        new Object[] {ex.getStackTrace()});
                return;
            } catch (IllegalArgumentException ex) {
                MobsConfigLoader.logger.log(Level.INFO, "Failed to read config.yml: {0}",
                        new Object[] {ex.getStackTrace()});
                return;
            }
            
            for (String item : file.getConfigurationSection("mob-drops." + mob).getKeys(false)) {
                String type = file.getString("mob-drops." + mob + "." + item + ".type", "STONE");
                type = type.toUpperCase();
                Material material = null;
                try {
                    material = Material.valueOf(type);
                } catch (NullPointerException ex) {
                    MobsConfigLoader.logger.log(Level.INFO, "Failed to read config.yml: {0}",
                            new Object[] {ex.getStackTrace()});
                    return;
                } catch (IllegalArgumentException ex) {
                    MobsConfigLoader.logger.log(Level.INFO, "Failed to read config.yml: {0}",
                            new Object[] {ex.getStackTrace()});
                    return;
                }
                
                int amount = file.getInt("mob-drops." + mob + "." + item + ".amount", 1);
                Item newItem = new CustomItem(material, amount);
                
                String namePath = "mob-drops." + mob + "." + item + ".name";
                String descriptionPath = "mob-drops." + mob + "." + item + ".description";
                
                if (file.getString(namePath) != null) {
                    newItem.setName(MobsConfigLoader.color(file.getString(namePath)));
                }
                if (file.getStringList(descriptionPath) != null) {
                    newItem.setDescription(MobsConfigLoader.color(file.getStringList(descriptionPath)));
                }
                drops.add(newItem);
            }
            CMD.registerMob(new CustomMob(drops, entity));
        }
    }
    
    private static String color(String string) {
        return string.replace("&", "§");
    }
    
    private static List<String> color(List<String> list) {
        List<String> array = new ArrayList<>();
        for(String string : list) {
            array.add(MobsConfigLoader.color(string));
        }
        return array;
    }
}