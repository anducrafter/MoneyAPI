package ch.andu.economymanger.commands;

import ch.andu.economymanger.sqlmanager.RankinSql;
import ch.andu.economymanger.sqlmanager.Sql;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.UUID;

public class EconomyCommand implements CommandExecutor {

    HikariDataSource source;
    public EconomyCommand(HikariDataSource source){
        this.source = source;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Sql eco = new Sql(source);
            Player p = (Player)sender;
            if(command.getName().equalsIgnoreCase("eco")) {

                if(args.length == 3) {
                    if(p.hasPermission("skysucht.admin")) {

                        OfflinePlayer tt =  Bukkit.getOfflinePlayer(args[1]);
                        if(!eco.playerExist(tt.getUniqueId().toString())) {
                            p.sendMessage("§aDer User §c"+tt.getName()+" §aist nicht im §b§lSkySucht §aSystem vorhanden");
                            return true;
                        }
                        Player t =  Bukkit.getPlayer(args[1]);
                        try {
                            double d =  Double.valueOf(args[2]);



                            if(args[0].equalsIgnoreCase("set")) {
                                eco.setPlayerMoney(t.getUniqueId().toString(), d);
                                p.sendMessage("§aDu hast den Kontostand von §e"+t.getDisplayName()+" §aauf §b"+d+" §agesetzt");
                            }

                            if(args[0].equalsIgnoreCase("give")) {

                                eco.addPlayerMoney(p.getUniqueId().toString(),d);
                                p.sendMessage("§aDu hast den Kontostand von §e"+t.getDisplayName()+" §b"+d+" §agutgeschrieben");
                                t.sendMessage("§aDir wurden auf dein Konto §e"+d+" §agutgeschreiben");
                            }

                            if(args[0].equalsIgnoreCase("remouve")) {
                                eco.takePlayerMoney(p.getUniqueId().toString(), d);
                                p.sendMessage("§aDu hast den Kontostand von §e"+t.getDisplayName()+" §b"+d+" §aabgezogen");
                            }
                        }catch (Exception e) {

                        }

                    }

                }


                if(args.length == 2) {

                    if(p.hasPermission("skysucht.admin")) {


                        if(args[0].equalsIgnoreCase("reset")) {

                            OfflinePlayer tt =  Bukkit.getOfflinePlayer(args[1]);
                            if(!eco.playerExist(tt.getUniqueId().toString())) {
                                p.sendMessage("§aDer User §c"+tt.getName()+" §aist nicht im §b§lSkySucht §aSystem vorhanden");
                                return true;
                            }

                            eco.setPlayerMoney(tt.getUniqueId().toString(), 1000);
                            p.sendMessage("§aDu hast den Kontostand von §e"+ tt.getName()+" §ageresetet");
                        }
                    }else{
                        p.sendMessage("§cKeine Berechtigung");
                    }
                }

                if(args.length == 0){
                    p.sendMessage("§aWie Benutzt man das neue Economy system?");
                    p.sendMessage("§7/eco set <Playername> amount");
                    p.sendMessage("§7/eco give <Playername> amount");
                    p.sendMessage("§7/eco remouve <Playername> amount");
                    p.sendMessage("§7/eco reset <Playername> ");

                }


            }

            if(command.getName().equalsIgnoreCase("money")){

                if(args.length == 1) {
                    if(p.hasPermission("skysucht.mod")) {
                        OfflinePlayer tt =  Bukkit.getOfflinePlayer(args[0]);
                        if(!eco.playerExist(tt.getUniqueId().toString())) {
                            p.sendMessage("§aDer User §c"+tt.getName()+" §aist nicht im §b§lSkySucht §aSystem vorhanden");
                            return true;
                        }

                        p.sendMessage("§aDer Kontostand von §e"+tt.getName()+ " §c"+eco.getPlayerMoney(tt.getUniqueId().toString()));
                    }
                }

                if(args.length == 0) {
                    p.sendMessage("§aDein Kontostand §c"+eco.getPlayerMoney(p.getUniqueId().toString()));

                }
            }

            if(command.getName().equalsIgnoreCase("pay")) {
                if(args.length == 2) {
                    OfflinePlayer t =  Bukkit.getOfflinePlayer(args[0]);


                    if(Bukkit.getOnlinePlayers().contains(t)) {
                        try {
                            double d = Double.parseDouble(args[1]);
                            Player tt = (Player) t;
                            if(eco.hasgenougmoney(p.getUniqueId().toString(), d)) {

                                eco.takePlayerMoney(p.getUniqueId().toString(), d);
                                eco.addPlayerMoney(t.getUniqueId().toString(), d);
                                p.sendMessage("§aDu hast "+tt.getDisplayName()+" §e"+d+" §agegeben");
                                tt.sendMessage("§a"+p.getDisplayName()+" §ahat dir §e"+d+" §agegeben");
                            }else {
                                p.sendMessage("§4Du hast zuwenig Geld");
                            }

                        }catch (Exception e) {

                        }
                    }else {
                        p.sendMessage("§4"+t.getName()+" §aIst nicht online");
                    }

                }
                if(args.length == 0) {
                    p.sendMessage(" ");
                    p.sendMessage("§7Anderen Spieler Geld überweissen");
                    p.sendMessage("§e/pay <player name> <amount>");
                    p.sendMessage(" ");
                }
            }



            if(command.getName().equalsIgnoreCase("moneytop")) {

                if(p.hasPermission("skysucht.mod")) {
                    RankinSql rank = new RankinSql(source);
                    if(args.length == 0) {
                        rank.setRanking();;
                        int regestriert = 10528+RankinSql.amount;
                        p.sendMessage(" ");
                        p.sendMessage("§b§lSkySucht §aBalance");
                        rank.getmaxeconomy(p, 1);
                        double d =  RankinSql.maxeconomy/1000000;
                        BigDecimal big = BigDecimal.valueOf(d);
                        p.sendMessage("§aIn Der Wirtschaft sind §e§l"+big+"§cMilionen");
                        p.sendMessage("§aInsgesamt auf dem Netzwerk Regestriert §c§l"+regestriert);
                        p.sendMessage("§aSeit dem neuem Economy Regestriert §6§l"+RankinSql.amount);
                        rank.getRanking(p,1);
                    }
                    if(args.length == 1) {

                        if(args[0].isEmpty()) {

                            return true;
                        }
                        int it = Integer.parseInt(args[0]);
                        if(it >0){
                            rank.setRanking();
                            int regestriert = 10528+RankinSql.amount;
                            p.sendMessage(" ");
                            p.sendMessage("Datenbank wird Sotiert");
                            p.sendMessage("§b§lSkySucht §aBalance");
                            rank.getmaxeconomy(p, 1);
                            double d =  RankinSql.maxeconomy/1000000;;
                            BigDecimal big = BigDecimal.valueOf(d);
                            p.sendMessage("§aIn Der Wirtschaft sind §e§l"+big+"§cMilionen");
                            p.sendMessage("§aInsgesamt auf dem Netzwerk Regestriert §c§l"+regestriert);
                            p.sendMessage("§aSeit dem neuem Economy Regestriert §6§l"+RankinSql.amount);
                            rank.getRanking(p, it);
                        }
                    }
                }

            }

            if(command.getName().equalsIgnoreCase("erstellen")) {
                if(args.length == 1){
                    Integer it = Integer.parseInt(args[0]);
                    Long l = System.currentTimeMillis();
                    for(int i = 0; i<it; i++){

                        UUID uuid = UUID.randomUUID();

                        new Sql(source).createPlayer(uuid.toString());

                    }
                    Long l2 = System.currentTimeMillis();
                    Long lfinish = l2-l;
                    p.sendMessage("Es wurden insgessamt "+it+" erstellst");
                    p.sendMessage("Und das in insgessamt §c"+lfinish+" §ams");

                }


            }
        }else {

            sender.sendMessage("§cNur ein Spieler kann Economy benutzen");
        }


        return false;
    }
}
