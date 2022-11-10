package ch.andu.economymanger.sqlmanager;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Sql {
    HikariDataSource source;
    public Sql(HikariDataSource source){
      this.source = source;
    }

    public boolean playerExist(String uuid){
        Connection connection = null;
        String update = "SELECT player_uuid FROM economy WHERE player_uuid=?";
        PreparedStatement p = null;
        try {
            //Initialise hikari connection, by getting the hikari connect if established
            connection = source.getConnection();
            //Preparing statement - INSERT INTO...
            p = connection.prepareStatement(update);
            //Setting parameters in MySQL query: i.e the question marks(?), where the first one has the index of 1.
            p.setString(1, uuid);
            ResultSet rs = p.executeQuery();
            if(rs.next()){
                return true;
            }
            //Executes the statement
            p.execute();
        } catch (SQLException e) {
            //Print out any exception while trying to prepare statement
            e.printStackTrace();
        }
        finally {
            //After catching the statement, close connection if connection is established
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // If connection is established, close connection after query
            if (p != null) {
                try {
                    p.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public void createPlayer(String uuid){
        if(!playerExist(uuid)){
            Connection connection = null;
            String update = "INSERT INTO economy(player_uuid, money) VALUES (?, ?)";
            PreparedStatement p = null;
            try {
                connection = source.getConnection();
                //Preparing statement - INSERT INTO...
                p = connection.prepareStatement(update);
                p.setString(1, uuid);
                p.setDouble(2, 1000);

                p.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
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


    //Hier alles noch reseten xD


    public double getPlayerMoney(String uuid){
        double d = 0.0;
        if(playerExist(uuid)) {
            Connection connection = null;
            String update = "SELECT money FROM economy WHERE player_uuid=?";
            PreparedStatement p = null;
            try {
                connection = source.getConnection();
                //Preparing statement - INSERT INTO...
                p = connection.prepareStatement(update);
                p.setString(1, uuid);

                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    d = rs.getDouble("money");
                }
                p.execute();
            } catch (SQLException e) {
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
        return d;
    }


    public  void setPlayerMoney(String uuid,double money){

        if(playerExist(uuid)) {
            Connection connection = null;
            String update = "UPDATE economy set money = ? where player_uuid= ?";
            PreparedStatement p = null;
            try {
                connection = source.getConnection();
                //Preparing statement - INSERT INTO...
                p = connection.prepareStatement(update);
                p.setDouble(1,money );
                p.setString(2, uuid);
                p.execute();
            } catch (SQLException e) {
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

    public void addPlayerMoney(String uuid,double money){

        if(playerExist(uuid)) {
            Connection connection = null;
            String update = "UPDATE economy set money = ? where player_uuid= ?";
            PreparedStatement p = null;
            try {
                connection = source.getConnection();
                //Preparing statement - INSERT INTO...
                p = connection.prepareStatement(update);
                double hat = getPlayerMoney(uuid);
                double geben = hat+money;
                p.setDouble(1,geben );
                p.setString(2, uuid);
                p.execute();
            } catch (SQLException e) {
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

    public void takePlayerMoney(String uuid,double money){

        if(playerExist(uuid)) {
            Connection connection = null;
            String update = "UPDATE economy set money = ? where player_uuid= ?";
            PreparedStatement p = null;
            try {
                connection = source.getConnection();
                //Preparing statement - INSERT INTO...
                p = connection.prepareStatement(update);
                double hat = getPlayerMoney(uuid);
                double geben = hat-money;
                p.setDouble(1,geben );
                p.setString(2, uuid);
                p.execute();
            } catch (SQLException e) {
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

    public  Boolean hasgenougmoney(String uuid,double min) {
        double hadmoney = getPlayerMoney(uuid);
        if(hadmoney > min || hadmoney == min && min<0) {
            return true;
        }else  {
            return false;
        }




    }

}
