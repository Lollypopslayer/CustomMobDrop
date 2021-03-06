package pl.themolka.custommobdrop.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import pl.themolka.custommobdrop.CustomItem;
import pl.themolka.custommobdrop.CustomItemAmount;
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
                String path = "mob-drops." + mob + "." + item + ".";
                String type = file.getString(path + "type", "STONE");
                type = type.toUpperCase();
                int typeInteger = 0;
                try {
                    typeInteger = Integer.valueOf(type);
                } catch (NumberFormatException ex) {}
                
                Material material = null;
                if (typeInteger != 0) { // By item's ID
                    try {
                        material = Material.getMaterial(typeInteger);
                    } catch (Throwable ex) {
                        material = Material.getMaterial(1);
                        MobsConfigLoader.logger.log(Level.INFO, "Failed to read config.yml: {0}",
                                new Object[] {ex.getStackTrace()});
                        return;
                    }
                } else { // By item's name
                    try {
                        material = Material.valueOf(type);
                    } catch (Throwable ex) {
                        material = Material.getMaterial(1);
                        MobsConfigLoader.logger.log(Level.INFO, "Failed to read config.yml: {0}",
                                new Object[] {ex.getStackTrace()});
                        return;
                    }
                }
                
                boolean random = file.getBoolean(path + "random", false);
                String amountString = file.getString(path + "amount", "1");
                ItemAmount amount = MobsConfigLoader.getItemAmount(amountString);
                Item newItem = new CustomItem(material, amount);
                
                String namePath = path + "name";
                String descriptionPath = path + "description";
                
                newItem.setRandom(random);
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
    
    private static ItemAmount getItemAmount(String string) {
        ItemAmount amount = new CustomItemAmount(1);
        if (string.contains("-")) {
            int split = string.indexOf("-");
            int min = 1;
            int max = 2;
            try {
                min = Integer.valueOf(string.substring(0, split));
                split++;
                max = Integer.valueOf(string.substring(split, string.length()));
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            amount = new CustomItemAmount(min, max);
        } else {
            int integer = 1;
            try {
                integer = Integer.valueOf(string);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            amount = new CustomItemAmount(integer);
        }
        return amount;
    }
}
