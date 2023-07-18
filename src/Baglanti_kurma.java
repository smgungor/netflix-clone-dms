

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;






/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sefa Mert
 */
public class Baglanti_kurma {
    
   /* private String kullanici_adi = "root";
    private String parola = "";
    
    private String db_ismi = "netflix";
    
    private String host = "localhost";
    
    private int port = 3306;
    
    private Statement statement = null;
    
    private Connection con = null;*/
    
    private PreparedStatement preparedStatement = null;
    
    private Connection con=null;

    private String url="jdbc:sqlite";

    private String dbName=":db/netflix.db";

    private String driver="org.sqlite.JDBC";

    private Statement statement = null;
    
    ResultSet rs = null;
    ResultSet rt = null;
    ResultSet ry = null;
    
    public ArrayList programAdiBul(String ara){
        
    ArrayList<program_tutma> programlar = new ArrayList<program_tutma>();
     
    String sorgu = "SELECT DISTINCT program.program_id, program.program_ismi, program.program_tipi, program.program_bolum, program.program_uzunlugu, program.program_puan FROM "
            + " program_tur,program,tur WHERE ( tur_ismi LIKE '%"+ara+"%'"
            + " AND tur.tur_id = program_tur.tur_id  AND  program_tur.program_id = "
            + " program.program_id ) OR ( program.program_ismi LIKE '%"+ara+"%')";
      try {
          statement =con.createStatement();
          rs=null;
          
          rs=statement.executeQuery(sorgu);
           while (rs.next()) {
             
                int id = rs.getInt("program_id");
                String isim = rs.getString("program_ismi");
                String tip = rs.getString("program_tipi");
                int bolum = rs.getInt("program_bolum");
                int uzunlug = rs.getInt("program_uzunlugu");
                int puan = rs.getInt("program_puan");
                programlar.add(new program_tutma(id, isim, tip, bolum, uzunlug, puan));
             
          }
          
      } catch (Exception e) {
          System.out.println("Basarisiz program adi bulma işlemi");
      }finally{
          try {
              statement.close();
              rs.close();
          } catch (Exception e) {
          }
      }
      
    
        return programlar;
    }
    
    
    public String kullanici_isim_dondur(int id){
        
        String sorgu = "select kullanici_adi from kullanici where kullanici_id = " + id;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sorgu);
            while(rs.next()){
                String isim = rs.getString("kullanici_adi");
                return isim;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                statement.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        return "isim yok";
    }
    
    //burasi
    public String film_adi_dondur(int id){
        
        String sorgu = "select program_ismi from program where program_id = " + id;
        String isim = "";
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sorgu);
            while(rs.next()){
                isim = rs.getString("program_ismi");
            }
            return isim;
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            try {
                statement.close();
                rs.close();
            } catch (Exception e) {
            }
        }
    }
    //burasi
    public void puanDegistir(int program_id){

            int a=0;

            int ortalama = 0;
            String sorgu1 = "SELECT program_id,AVG(puan) FROM kullanici_program WHERE program_id = " + program_id +" GROUP BY program_id";
            //String sorgu2= "Update program set program_id ="+;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sorgu1);
            while(rs.next()){
                ortalama  = rs.getInt("AVG(puan)");
                
            }

            } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            String sorgu2 = "Update program Set program_puan = '"+ ortalama +"' where program_id = "+ program_id;
            try {
                statement.executeUpdate(sorgu2);
            } catch (SQLException ex) {
                Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    statement.close();
                    rs.close();
                } catch (Exception e) {
                }
            }
           
                
                

            /*System.out.println("puan : "+puan[0]);
            System.out.println("puan2 : "+puan[1]);*/

    }
    
    
    public void puan_guncelle(int kullanici_id ,int program_id , int bolum ,int yeni_puan){
        
        String sorgu = "update kullanici_program set puan = " + yeni_puan + " where kullanici_id = " + kullanici_id + " and program_id = " + program_id + " and bolum = " + bolum;
        try {
            statement = con.createStatement();
            statement.executeUpdate(sorgu);
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }
    
    public void sure_guncelle(int kullanici_id ,int program_id ,int bolum ,int yeni_sure){
        String sorgu = "update kullanici_program set monitoring_time = " + yeni_sure + " where kullanici_id = " + kullanici_id + " and program_id = " + program_id + " and bolum = " + bolum;
        try {
            statement = con.createStatement();
            statement.executeUpdate(sorgu);
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
                
    }
    
    
    
    public int izleme_suresi_bul(int kullanici_id ,int program_id ,int bolum){
        
        String sorgu = "select * from kullanici_program where kullanici_id = " + kullanici_id + " and program_id = " + program_id + " and bolum = " + bolum;    
        int sure;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sorgu);
            
            while(rs.next()){
                sure = rs.getInt("monitoring_time");return sure;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            try {
                statement.close();
                rs.close();
            } catch (Exception e) {
            }
        }
             return 1;
    
                
    }
    
    
    
    
    
    
    public void kullanici_program_ekle(int kullanici_id ,int program_id ,String monitoring_date ,int monitoring_time, int bolum, int puan){
        System.out.println(kullanici_id);
        System.out.println(program_id);
        System.out.println(monitoring_time);
        System.out.println(monitoring_date);
        System.out.println(bolum);
        System.out.println(puan);
        
        
        String sorgu = "insert into kullanici_program (kullanici_id ,program_id ,monitoring_date ,monitoring_time ,bolum ,puan) values(? ,? ,? ,? ,? ,?)";
     
        try {
     
            preparedStatement = (PreparedStatement) con.prepareStatement(sorgu);
            
            preparedStatement.setInt(1, kullanici_id);
            preparedStatement.setInt(2, program_id);
            preparedStatement.setString(3, monitoring_date);
            preparedStatement.setInt(4, monitoring_time);
            preparedStatement.setInt(5, bolum);
            preparedStatement.setInt(6, puan);
            
            
            preparedStatement.executeUpdate();
            
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
    }
    
    

    
    
    public boolean program_id_kullanici_id_kontrol( int kullanici_id ,int program_id ,int bolum){
       
        
       String sorgu = "select * from kullanici_program where program_id = ? and kullanici_id = ? and bolum = ?";
       
       
        try {
            preparedStatement = (PreparedStatement) con.prepareStatement(sorgu);
            
            preparedStatement.setInt(1, program_id);
            preparedStatement.setInt(2, kullanici_id);
            preparedStatement.setInt(3, bolum);
            
            rs = preparedStatement.executeQuery();
            
            
            return rs.next();
            
        } catch (SQLException ex) {
            return false;
        }finally{
            try {
                rs.close();
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
          
    }
    
    
    
    
    
    /*public ArrayList<program_tutma> ture_gore_bul(String tur){
        ArrayList<program_tutma> programlar = new ArrayList<program_tutma>();
        String sorgu = "select * from program where program_id in (select program_id from program_tur where tur_id = (select tur_id from tur where tur_ismi = '" + tur + "'))";
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sorgu);
            
            while(rs.next()){
                
                int id = rs.getInt("program_id");
                String isim = rs.getString("program_ismi");
                String tip = rs.getString("program_tipi");
                int bolum = rs.getInt("program_bolum");
                int uzunlug = rs.getInt("program_uzunlugu");
                int puan = rs.getInt("program_puan");
                programlar.add(new program_tutma(id, isim, tip, bolum, uzunlug, puan));
                
            }
            
            return programlar;
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            try {
                rs.close();
                statement.close();
            } catch (Exception e) {
            }
        }
        
    }*/
    
    /*
    public ArrayList<program_tutma> programlar_getirme(String program){
        ArrayList<program_tutma> programlar = new ArrayList<program_tutma>();
        String sorgu = "select * from program";
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sorgu);
            
            while(rs.next()){
                if(rs.getString("program_ismi").equals(program)){
                int id = rs.getInt("program_id");
                String isim = rs.getString("program_ismi");
                String tip = rs.getString("program_tipi");
                int bolum = rs.getInt("program_bolum");
                int uzunlug = rs.getInt("program_uzunlugu");
                int puan = rs.getInt("program_puan");
                programlar.add(new program_tutma(id, isim, tip, bolum, uzunlug, puan));
                }
            }
            
            return programlar;
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            try {
                rs.close();
                statement.close();
            } catch (Exception e) {
            }
        }
        
    }*/
    
    
    //sevilen tür adinda bir tabloya kayit getimeye calistim ama buna gerek kalmayack herhalde!!!!
    /*public void sevilenTurEkle(String bir ,String iki ,String uc ,String kullaniciId){
        
        String sorgu = "insert into kullanici_stur (sevilentur ,kullanici_id) values(? ,?)";
        
        
        try {
            preparedStatement = (PreparedStatement) con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, bir);
            preparedStatement.setString(2, kullaniciId);
            
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            preparedStatement = (PreparedStatement) con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, iki);
            preparedStatement.setString(2, kullaniciId);
            
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            preparedStatement = (PreparedStatement) con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, uc);
            preparedStatement.setString(2, kullaniciId);
            
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }*/
    
    //burasi
    public ArrayList<sevilen_tur> sevilen_turler_getir(String birincitur, String ikincitur, String ucunucutur){
        
        ArrayList<sevilen_tur> bulunan_tur = new ArrayList<sevilen_tur>();
        
               
        try {
            
            statement = con.createStatement();
            String sorgu1 = "select * from program where program_id in (select program_id from program_tur where tur_id = (select tur_id from tur where tur_ismi = '" + birincitur + "')) order by program_puan desc limit 2";
            rs = statement.executeQuery(sorgu1);
            
            while(rs.next()){
                int id = rs.getInt("program_id");
                String isim = rs.getString("program_ismi");
                String tip = rs.getString("program_tipi");
                int bolum = rs.getInt("program_bolum");
                int puan = rs.getInt("program_puan");
                bulunan_tur.add(new sevilen_tur(id, isim, tip, bolum, puan));
            }
            
            String sorgu2 = "select * from program where program_id in (select program_id from program_tur where tur_id = (select tur_id from tur where tur_ismi = '" + ikincitur + "')) order by program_puan desc limit 2";
             rt = statement.executeQuery(sorgu2);
            
            while(rt.next()){
                int id = rt.getInt("program_id");
                String isim = rt.getString("program_ismi");
                String tip = rt.getString("program_tipi");
                int bolum = rt.getInt("program_bolum");
                int puan = rt.getInt("program_puan");
                bulunan_tur.add(new sevilen_tur(id, isim, tip, bolum, puan));
            }
            
            String sorgu3 = "select * from program where program_id in (select program_id from program_tur where tur_id = (select tur_id from tur where tur_ismi = '" + ucunucutur + "')) order by program_puan desc limit 2";
             ry = statement.executeQuery(sorgu3);
            
            while(ry.next()){
                int id = ry.getInt("program_id");
                String isim = ry.getString("program_ismi");
                String tip = ry.getString("program_tipi");
                int bolum = ry.getInt("program_bolum");
                int puan = ry.getInt("program_puan");
                bulunan_tur.add(new sevilen_tur(id, isim, tip, bolum, puan));
            }
            
            
            
            return bulunan_tur;
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            try {
                rs.close();
                ry.close();
                rt.close();
                statement.close();
            } catch (Exception e) {
            }
        }
        
        
    }
    
    
    //Kullanici mailini gönderdigimizde o maile sahip olan kullanicinin id sini dödüren fonksiyn...
    public int kullaniciIdBul(String mail){
        
        String sorgu = "select * from kullanici";
        
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sorgu);
            int id;
            while(rs.next()){
                
                if(rs.getString("mail").equals(mail) ){
                    id = rs.getInt("kullanici_id");return id;
                }
               
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            try {
                rs.close();
                statement.close();
            } catch (Exception e) {
            }
        }
        
        return 0;     
        
    }
    
    //Kayit sayfasindan gelenbilgilerle veri tabanindaki kullnaici tablosuna bir kayit ekliyor...
    public void kullaniciEkle(String kullaniciAdi ,String parola ,String mail ,String dogumTarihi){
        String sorgu = "insert into kullanici (kullanici_adi ,parola ,mail ,dogum_tarihi) values(? ,? ,? ,?)";
        
        try {
            preparedStatement = (PreparedStatement) con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, parola);
            preparedStatement.setString(3, mail);
            preparedStatement.setString(4, dogumTarihi);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
    }
    
    
    //Bu fonksiyonu kullanarak mail adresi ve parolayi kontrol edebiliriz...
    public boolean kontrol(String yeniMail ,String parola){
       
       String sorgu = "select * from kullanici where mail = ? and parola = ?";
       
       
        try {
            preparedStatement = (PreparedStatement) con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, yeniMail);
            preparedStatement.setString(2, parola);
            
            rs = preparedStatement.executeQuery();
           
            
            return rs.next();
            
        } catch (SQLException ex) {
            return false;
        }finally{
            try {
                rs.close();
                preparedStatement.close();
            } catch (Exception e) {
            }
        }
          
    }
    
    
    
    //Buradaki constructer bizimveri tabanimizla baglanti kurmamizi sagliyor...
   /* public Baglanti_kurma(){
        // "jdbc:mysql://localhost:3306/demo"
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db_ismi;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Direver bulunamadi...");
        }
        
        try {
            con = (Connection) DriverManager.getConnection(url, kullanici_adi, parola);
            System.out.println("Baglanti basarili...");
        } catch (SQLException ex) {
            System.out.println("Baglanti basarisiz...");
        }
                
    }*/
    
    
    public Baglanti_kurma() {


          try {
        Class.forName(driver).newInstance();
        con=DriverManager.getConnection(url+dbName);
             System.out.println("Basarili");
        } catch (Exception ex) {
        Logger.getLogger(Baglanti_kurma.class.getName()).log(Level.SEVERE, null, ex);
              System.out.println("Basarisiz");
        }
       }
          
 

    }
    
    
    
    

   
    

