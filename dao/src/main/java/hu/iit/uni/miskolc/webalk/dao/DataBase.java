package hu.iit.uni.miskolc.webalk.dao;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DataBase {

    private static final String con = "jdbc:sqlite:";
    private static final String connection = "./database/glassShop.db";
    private static boolean dbCreated = false;
    private static Exception cause = null;

    private DataBase() {
    }

    @NotNull
    @Contract(pure = true)
    static String getConnection() {
        return con + connection;
    }

    @Contract(pure = true)
    public static boolean isDBNotCreated() {
        return !dbCreated;
    }

    @Contract(pure = true)
    public static Exception getCause() {
        return cause;
    }

    static void createDataBase() {
        File f = new File(connection);
        if (f.exists() && !f.isDirectory()) {
            dbCreated = true;
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS `Shop` (\n" +
                "\t`SNAME`\tCHAR ( 50 ) NOT NULL,\n" +
                "\t`LOCATION`\tCHAR ( 50 ) NOT NULL,\n" +
                "\tPRIMARY KEY(`SNAME`));";

        Connection c = null;
        PreparedStatement ps = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(getConnection());
            c.setAutoCommit(false);

            ps = c.prepareStatement(sql);
            ps.executeUpdate();
            sql = "CREATE TABLE IF NOT EXISTS `Employee` (\n" +
                    "\t`IDNUM`\tINT NOT NULL,\n" +
                    "\t`NAME`\tCHAR ( 50 ) NOT NULL,\n" +
                    "\t`GENDER`\tCHAR ( 10 ) NOT NULL,\n" +
                    "\t`SALARY`\tFLOAT NOT NULL,\n" +
                    "\t`POST`\tCHAR ( 20 ) NOT NULL,\n" +
                    "\t`ShopName`\tCHAR ( 50 ) NOT NULL,\n" +
                    "\tFOREIGN KEY(`ShopName`) REFERENCES `Shop`(`NAME`),\n" +
                    "\tPRIMARY KEY(`IDNUM`));";
            ps = c.prepareStatement(sql);
            ps.executeUpdate();
            sql = "CREATE TABLE IF NOT EXISTS `Glasses` (\n" +
                    "\t`Brand`\tCHAR ( 30 ) NOT NULL,\n" +
                    "\t`Model`\tCHAR ( 30 ) NOT NULL,\n" +
                    "\t`Price`\tFLOAT NOT NULL,\n" +
                    "\t`Gender`\tCHAR ( 30 ) NOT NULL,\n" +
                    "\t`Sunglasses`\tINT NOT NULL,\n" +
                    "\t`AvailableAt`\tCHAR ( 50 ) NOT NULL,\n" +
                    "\tFOREIGN KEY(`AvailableAt`) REFERENCES `Shop`(`NAME`)\n);";
            ps = c.prepareStatement(sql);
            ps.executeUpdate();
            sql = "CREATE TABLE IF NOT EXISTS `Accessories` (\n" +
                    "\t`Appellation`\tCHAR ( 30 ) NOT NULL,\n" +
                    "\t`Brand`\tCHAR ( 25 ) NOT NULL,\n" +
                    "\t`Price`\tFLOAT NOT NULL);";
            ps = c.prepareStatement(sql);
            ps.executeUpdate();

            sql = "INSERT INTO `Shop` (SNAME,LOCATION) VALUES ('Optiris','Tiszaujvaros'),\n" +
                    " ('Trend Optika','Miskolc');";
            ps = c.prepareStatement(sql);
            ps.executeUpdate();

            sql = "INSERT INTO `Employee` (IDNUM,NAME,GENDER,SALARY,POST,ShopName) VALUES (1,'Horvat Rozi','FEMALE',100000.0,'Optician','Optiris'),\n" +
                    " (2,'Kubuk Matyi','MALE',135000.0,'Mechanist','Trend Optika'),\n" +
                    " (3,'Teszt Elek','MALE',90000.0,'Seller','Trend Optika'),\n" +
                    " (4,'Mester Eszter','FEMALE',85000.0,'Optician','Optiris');";
            ps = c.prepareStatement(sql);
            ps.executeUpdate();

            sql = "INSERT INTO `Glasses` (Brand,Model,Price,Gender,Sunglasses,AvailableAt) VALUES ('Oakley','Batwolf',30000.0,'MALE',1,'Optiris'),\n" +
                    " ('Ray-Ban','Dinesh',25000.0,'FEMALE',0,'Trend Optika'),\n" +
                    " ('Levis','Eyewear',10000.0,'FEMALE',0,'Trend Optika');";
            ps = c.prepareStatement(sql);
            ps.executeUpdate();

            sql = "INSERT INTO `Accessories` (Appellation,Brand,Price) VALUES ('Tok','Oakley',1000.0),\n" +
                    " ('Torlokendo','Hoya',800.0),\n" +
                    " ('Tok','Optiris',200.0);";
            ps = c.prepareStatement(sql);
            ps.executeUpdate();
            c.commit();
            dbCreated = true;
        } catch (SQLException e) {
            cause = new Exception(e);
            dbCreated = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
