package ch.andu.economymanger.datasource;

import ch.andu.economymanger.Money;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import javax.sql.DataSource;

public class HirackleCP {

    private HikariDataSource hikari;
    public HikariDataSource intHikariConfig(){
        HikariDataSource source;

        //Hier kommt dan ip, Benutzer und Passwort für den User. ?!?!?!?! xD
        String address = Money.getInstance().config.getAddress();
        String name = Money.getInstance().config.getDatabase();
        String username = Money.getInstance().config.getUserName();
        String password = Money.getInstance().config.getPasswort();
        String port = Money.getInstance().config.getPort();
        String DataSourceClass = Money.getInstance().config.getDataSourceClassName();
        //Initialise hikari instace
        hikari = new HikariDataSource();
        //Setting Hikari properties
        hikari.setMaximumPoolSize(10);
        //Das hier währe eigentlich richtig
    //    config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    //    hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.setDataSourceClassName(DataSourceClass);
        hikari.addDataSourceProperty("serverName", address);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", name);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);
     //   hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");

        source = hikari;
        Bukkit.getConsoleSender().sendMessage("§c§lWichtig §rDataSource wurde erfolgreich geladen!");
        return source;
    }


}
