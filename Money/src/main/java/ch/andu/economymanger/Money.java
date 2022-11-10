package ch.andu.economymanger;

import ch.andu.economymanger.commands.EconomyCommand;
import ch.andu.economymanger.datasource.HirackleCP;
import ch.andu.economymanger.datasource.datasourceconfig;
import ch.andu.economymanger.lisstener.JoinLisstener;
import ch.andu.economymanger.sqlmanager.Sql;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Money extends JavaPlugin {


    private HikariDataSource hikari;
    public static Money plugin;
    public Sql sql;
    public datasourceconfig config = new datasourceconfig();

    @Override
    public void onEnable() {
        plugin  = this;
        config.loadConfig();
        hikari = new HirackleCP().intHikariConfig();
        sql = new Sql(hikari);

        Bukkit.getConsoleSender().sendMessage("§b§lHirakConfig Test wurde erfolgreich geladen xD");
        createTable();
        loadCommands();
        loadLisstenr();
        Bukkit.getConsoleSender().sendMessage("§e§lMoneySystem §aby anducrafter has sucfulle loaded");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§e§lMoneySystem §aby anducrafter has sucfulle unloded");
    }
private void loadCommands() {
    getCommand("money").setExecutor(new EconomyCommand(hikari));
    getCommand("eco").setExecutor(new EconomyCommand(hikari));
    getCommand("pay").setExecutor(new EconomyCommand(hikari));
    getCommand("moneytop").setExecutor(new EconomyCommand(hikari));
 //   getCommand("erstellen").setExecutor(new EconomyCommand(hikari));
}
private  void loadLisstenr(){
    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(new JoinLisstener(hikari),this);

}

    public static Money getInstance() {
        return plugin;
    }


    private void createTable() {
        String update = "CREATE TABLE IF NOT EXISTS economy(player_uuid varchar(64), money double(30,2));";
        Connection connection = null;
        PreparedStatement p = null;
        try {
            connection =  hikari.getConnection();
            p = connection.prepareStatement(update);
            p.execute();
        } catch (SQLException e) {
           Bukkit.getConsoleSender().sendMessage("§cDatasource verbindung fehlgeschlagen!");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (p != null) {
                try {
                    p.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}