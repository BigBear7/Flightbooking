/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooking;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author User
 */
public class Config {

/*private String filename;

Config (String filename) {
    this.filename = filename
}*/
    
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        String fileName = "flightbooking.txt";
        InputStream is = new FileInputStream(fileName);

        prop.load(is);

        System.out.println(prop.getProperty("name"));
        System.out.println(prop.getProperty("version"));

        System.out.println(prop.getProperty("app.vendor", "Java"));
    }
}
