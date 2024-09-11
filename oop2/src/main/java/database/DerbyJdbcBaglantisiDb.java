package database;

import domain.TelefonDomain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DerbyJdbcBaglantisiDb {

    private final String URL = "jdbc:derby:MyDbTest;create=true";
    private final String USER = "";
    private final String PASSWORD = "";

    Connection connection = null;

    public Connection getConnection() {


        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("baqlandı");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public DerbyJdbcBaglantisiDb(){
        initTablo();
    }


    public void initTablo(){

        Connection baglanti = getConnection();

        try {
            Statement sorgu = baglanti.createStatement();
            sorgu.executeUpdate("CREATE TABLE kisiler (id NOT NULL GENERATED ALWAYS AS IDENTITY"
                    +"(START WITH 1 , INCREMENT BY 1),"
                    +"name VARCHAR(50),"
                    +"surname VARCHAR(50),"
                    +"phoneNumber VARCHAR(50))");


            sorgu.close();
            baglanti.close();

        } catch (SQLException e) {

            System.out.println("Tablo zaten Mevcut");
        }

    }

    public void yeniKayıt(TelefonDomain telefonDomain){
        Connection baglanti = getConnection();
        try {
            Statement sorgu = baglanti.createStatement();
            // Eksik olan kapama tırnağı ('...') eklendi.
            sorgu.executeUpdate("INSERT INTO kisiler (name, surname, phoneNumber) VALUES ('"
                    + telefonDomain.getName() + "','"
                    + telefonDomain.getSurname() + "','"
                    + telefonDomain.getPhoneNumber() + "')");


            sorgu.close();
            baglanti.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<TelefonDomain> listele(){

        List<TelefonDomain> list = new ArrayList<>();
        Connection baglanti = getConnection();
        try {
            Statement sorgu = baglanti.createStatement();
            ResultSet rs = sorgu.executeQuery("SELECT * FROM kisiler");
            while (rs.next()){
                TelefonDomain yeniListe = new TelefonDomain();

                yeniListe.setId(rs.getInt("id"));
                yeniListe.setName(rs.getString("name"));
                yeniListe.setSurname(rs.getString("surname"));
                yeniListe.setPhoneNumber(rs.getString("phoneNumber"));

                list.add(yeniListe);
            }

            sorgu.close();
            baglanti.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


            return list;
    }


    public TelefonDomain bul (int id){

        TelefonDomain bulunacakKisi = new TelefonDomain();

        Connection baglanti = getConnection();
        try {
            Statement sorgu = baglanti.createStatement();

            ResultSet rs = sorgu.executeQuery("SELECT * FROM kisiler WHERE id = "+id+"");

            while (rs.next()){

                bulunacakKisi.setId(rs.getInt("id"));
                bulunacakKisi.setName(rs.getString("name"));
                bulunacakKisi.setSurname(rs.getString("surname"));
                bulunacakKisi.setPhoneNumber(rs.getString("phoneNumber"));

            }

            sorgu.close();
            baglanti.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return bulunacakKisi;
    }


    public void sil (TelefonDomain telefonDomain){

        Connection baglanti = getConnection();

        try {
            Statement sorgu = baglanti.createStatement();

            sorgu.executeUpdate("DELETE FROM kisiler WHERE id = "+telefonDomain.getId()+"");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void duzenle(TelefonDomain telefonDomain){

        Connection baglanti = getConnection();

        try {
            Statement sorgu = baglanti.createStatement();

            sorgu.executeUpdate("UPDATE kisiler SET name = '"+telefonDomain.getName()+"', surname = '"+telefonDomain.getSurname()+"', phoneNumber = '"+telefonDomain.getPhoneNumber()+"' WHERE id = "+telefonDomain.getId()+"");

            sorgu.close();
            baglanti.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




}
