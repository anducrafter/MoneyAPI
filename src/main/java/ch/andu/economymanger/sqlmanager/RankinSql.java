package ch.andu.economymanger.sqlmanager;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Hier wird nunja halt das Ranking von einem Spieler bestummen xD
 */
public class RankinSql {

    HikariDataSource source;
    public  RankinSql(HikariDataSource source){
        this.source = source;
    }
    public static  double maxeconomy = 0;
    public static int amount = 0;
    static HashMap<Integer, String> money = new HashMap<>();

    public  void setRanking() {
        amount = 0;
        Connection connection = null;
        String update = "SELECT player_uuid FROM economy ORDER BY money DESC";
        PreparedStatement p = null;
        try {
            connection = source.getConnection();

            p = connection.prepareStatement(update);
            ResultSet rs = p.executeQuery();
            while (rs.next()){
                amount++;
                money.put(amount,rs.getString("player_uuid"));


            }
            p.execute();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cUUID von eimer Spieler konnte vom MoneySystem nicht gefunden werden");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            } if (p != null) {
                try {
                    p.close();
                } catch (SQLException e) {

                }
            }
        }


    }

    public  void getRanking(Player p, int it) {
        Sql eco = new Sql(source);
        if(it == 1) {
            for(int i = 0; i< 10; i++) {
                try {

                    int id = i+1;
                    String name = Bukkit.getOfflinePlayer(UUID.fromString(money.get(id))).getName();
                    p.sendMessage(id+".§e"+name+" §c"+ BigDecimal.valueOf(eco.getPlayerMoney(money.get(id))));

                }catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }else {
            for(int i = it*10-10; i< it*10; i++) {
                try {
                    int id = i+1;
                    String name = Bukkit.getOfflinePlayer(UUID.fromString(money.get(id))).getName();
                    p.sendMessage(id+".§e"+name+" §c"+BigDecimal.valueOf(eco.getPlayerMoney(money.get(id))));
                }catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        }


    }

/*
Hier möchte ich nun einfach schauen was dieses Maxeconomy was ich vor langer zeit programmiert habe eigentlich bewierkt!
 */


    public  void getmaxeconomy(Player p, int it) {
        Sql eco = new Sql(source);
        maxeconomy = 0;
        if(it == 1) {
            for(int i = 0; i< amount; i++) {
                try {
                    int id = i+1;
                    maxeconomy +=eco.getPlayerMoney(money.get(id));

                }catch (Exception e) {

                }

            }
        }else {
         /*   for(int i = it*10-10; i< it*10; i++) {
                try {
                    int id = i+1;
                    maxeconomy +=eco.getPlayerMoney(money.get(id));
                }catch (Exception e1) {

                }
            }*/

       }

    }



}
