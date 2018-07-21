/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.editionsdubureau.randomcat;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.swing.*;
import org.json.*;

/**RandomCat - A simple java client for random.cat using Unirest and Maven
 *
 * @author Vincent AUBRIOT
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // This is where the magic happens...
        HttpResponse<JsonNode> cat;
        try {
            cat = Unirest.get("https://aws.random.cat/meow").asJson();
            System.out.println(cat.getBody());
            JSONObject catJson = cat.getBody().getObject();
            String link = catJson.getString("file");
            try {
                URL catURL = new URL(link);
                JFrame main = new JFrame("Cat of the day !");
                main.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                try {
                    BufferedImage catPic = ImageIO.read(catURL);
                    JLabel cLabel = new JLabel(new ImageIcon(catPic.getScaledInstance(catPic.getWidth(),catPic.getHeight(),Image.SCALE_SMOOTH)));
                    main.setSize(catPic.getWidth(), catPic.getHeight());
                    main.add(cLabel);
                    main.setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnirestException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Unirest.shutdown();
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
