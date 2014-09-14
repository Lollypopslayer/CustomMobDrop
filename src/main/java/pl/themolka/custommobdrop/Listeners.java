package pl.themolka.custommobdrop;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import pl.themolka.custommobdrop.api.CMD;
import pl.themolka.custommobdrop.api.Item;

public class Listeners implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (CMD.getMob(e.getEntityType()) != null) {
            e.getDrops().clear();
            for (Item item : CMD.getMob(e.getEntityType()).getDrops()) {
                if (item.drop()) {
                    Location location = e.getEntity().getLocation();
                    location.getWorld().dropItemNaturally(location, item.getItem());
                }
            }
        }
    }
}
