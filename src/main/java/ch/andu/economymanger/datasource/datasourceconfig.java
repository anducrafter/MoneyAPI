package ch.andu.economymanger.datasource;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class datasourceconfig {

    File file = new File("plugins//MoneyAPI//mysql.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);


    public void loadConfig(){
        if(!cfg.isSet("database")){
            cfg.set("database","skysuchttest");
            cfg.set("username","anducrafter");
            cfg.set("passwort","anducrafter");
            cfg.set("address","localhost");
            cfg.set("port","3306");
            cfg.set("DataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            saveCfg();
        }
    }

    public String getDatabase(){
        return  cfg.getString("database");
    }
    public String getUserName(){
        return  cfg.getString("username");
    }
    public String getPasswort(){
        return  cfg.getString("passwort");
    }
    public String getAddress(){
        return  cfg.getString("address");
    }
    public String getPort(){
        return  cfg.getString("port");
    }
    public String getDataSourceClassName(){
        return  cfg.getString("DataSourceClassName");
    }



    public void saveCfg() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
