package ch.andu.economymanger.lisstener;

import ch.andu.economymanger.Money;
import ch.andu.economymanger.sqlmanager.Sql;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class JoinLisstener implements Listener {

    HikariDataSource source;

    public JoinLisstener(HikariDataSource source){
        this.source = source;
    }
    @EventHandler
    public void  onJoin(PlayerJoinEvent e){
        Sql data = new Sql(source);
        data.createPlayer(e.getPlayer().getUniqueId().toString());


            //das ist nur für cubisima für sksysucht für kein anderer server XD


      //  if(player.hasPermission("*")){
           // player.sendMessage("§b§lSkySucht §cGuten Tag cubisima meister das Economy System läuft auf die Version "+ Money.getInstance().getDescription().getVersion());
    //    }

    }
}
