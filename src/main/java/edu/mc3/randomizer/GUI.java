package edu.mc3.randomizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;


public class GUI {
	 public static void main(String[] args) throws Exception {
	        GUI mForm = new GUI();
	        TwitterPhotoDownloader mTwitter = new TwitterPhotoDownloader();
	        ImageMods boof = new ImageMods();
	    }
  // JFrame mFrame;
  // JTextField tfUser, tfPass, tfTwitterURL;
  // JLabel jlRawImage, jlProcessedImage;

  private String twitterURL;
  private String username;
  private String password;
  private String message;
  private String mod;
  private BufferedImage rawImage;
  private BufferedImage processedImage;
  private String[] mods;
  JLabel jlProcessedImage;
  JLabel jlRawImage;
  

  private ImageMods boof;
  private TwitterPhotoDownloader mTwitter;
  // initialization
  public GUI() throws IOException {
    
    
    boof = new ImageMods();
    mods = boof.getAvailableMods();
    
    mTwitter = new TwitterPhotoDownloader();
    message = "Hi class!";
    
    initializeForm();
    
  }

  /**
   * @return the twitterURL
   */
  public String getTwitterURL() {
    return twitterURL;
  }

  /**
   * @param twitterURL the twitterURL to set
   */
  public void setTwitterURL(String twitterURL) {
    this.twitterURL = twitterURL;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   */
  public void setMessage(String msg) {
    this.message = msg;
  }
  /**
   * @return the mod
   */
  public String getMod() {
	  return mod;
  }
  /**
   * @param mod
   */
  public void setMod(String mod) {
	  this.mod = mod;
  }

  /**
   * @return the rawImage
   */
  public BufferedImage getRawImage() {
    return rawImage;
  }

  /**
   * @param rawImage the rawImage to set
   */
  public void setRawImage(BufferedImage rawImage) {
    this.rawImage = rawImage;
  }

  /**
   * @return the processedImage
   */
  public BufferedImage getProcessedImage() {
    return processedImage;
  }

  /**
   * @param processedImage the processedImage to set
   */
  public void setProcessedImage(BufferedImage processedImage) {
    this.processedImage = processedImage;
  }
  
  /** 
   * @return twitter id
   */
  public String getTwitterID() {
	    String[] parsedURL = twitterURL.split("/");
	    return parsedURL[parsedURL.length - 1];
  }

  private void retrieveImage() {
    // TODO  - change to return the image, not save it i think
	  try {
		  setRawImage(TwitterPhotoDownloader.getTwitterPhotoFromTwitterID(getTwitterID()));
		  Image dimg = rawImage.getScaledInstance(jlRawImage.getWidth(), jlRawImage.getHeight(),
			        Image.SCALE_SMOOTH);
		  jlRawImage.setIcon(new ImageIcon(dimg));
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  private void postImage() {
	  // TODO - add method to post to twitter
	  UploadPicture.uploadPic(getProcessedImage(), getMessage());
  }
  
  private void processImage() throws IOException {
	  // todo - makebrighter i think needs to take in an image and return an image
	  boof.setMod(mod);
	  setProcessedImage(boof.applyFilter(getRawImage()));
//	  boof.setMod(mod);
//	  boof.setProcessedImage(getRawImage());
	  Image dimg = processedImage.getScaledInstance(jlProcessedImage.getWidth(), jlProcessedImage.getHeight(),
		        Image.SCALE_SMOOTH);
	  jlProcessedImage.setIcon(new ImageIcon(dimg));
	  //jlProcessedImage = new JLabel(new ImageIcon(rawImage));
  }

//  private void getInput(String user, String pass, String URL) {
  private void getInput(String URL, String msg, int modIndex) {
//    username = user; // tfUser.getText();
//    password = pass;// tfPass.getText();
    twitterURL = URL;// tfTwitterURL.getText();
    message = msg;
    mod = boof.getAvailableMods()[modIndex];   

  }
  
//  private String[] getMods() {
////	  String[] mods = {"Random","Red", "Invert", "Grayscale", "Brighter", "Static"};
//	  return mods;
//  }

  private void initializeForm() throws IOException {
    // setup frame
    JFrame mFrame = new JFrame("My First GUI");
    mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mFrame.setSize(725, 500);

    // setup buttons
    JButton btnConvert = new JButton("Convert");
    btnConvert.setBounds(140, 155, 100, 25);
    mFrame.add(btnConvert);

    JButton btnGetFromTwitter = new JButton("Get Image");
    btnGetFromTwitter.setBounds(140, 30, 100, 25);
    mFrame.add(btnGetFromTwitter);

    JButton btnPostToTwitter = new JButton("Post Image");
    btnPostToTwitter.setBounds(140, 285, 100, 25);
    mFrame.add(btnPostToTwitter);

    // setup twitter section
//    JLabel authLabel = new JLabel("Twitter Information");
//    authLabel.setBounds(10, 10, 150, 20);
//    JLabel userLabel = new JLabel("Username");
//    userLabel.setBounds(20, 35, 100, 20);
//    final JTextField tfUser = new JTextField("");
//    tfUser.setBounds(125, 35, 100, 20);
//    JLabel passLabel = new JLabel("Password");
//    passLabel.setBounds(20, 60, 100, 20);
//    final JTextField tfPass = new JTextField("");
//    tfPass.setBounds(125, 60, 100, 20);
    JLabel messageLabel = new JLabel("Caption to post");
    messageLabel.setBounds(15, 265, 100, 25);
    final JTextField tfMessage = new JTextField("");
    tfMessage.setBounds(30, 290, 105, 20);
    JLabel urlLabel = new JLabel("Image URL");
    urlLabel.setBounds(15, 5, 85, 25);
    final JTextField tfTwitterURL = new JTextField("");
    tfTwitterURL.setBounds(30, 30, 105, 25);
    JLabel modListLabel = new JLabel("Select a mod");
    modListLabel.setBounds(15, 65, 100, 25);
    final JList<String> jlModList = new JList<String>(mods);
    jlModList.setSelectedIndex(0);
    jlModList.setBounds(30, 90, 105, 155);
    
//    mFrame.add(authLabel);
//    mFrame.add(userLabel);
//    mFrame.add(passLabel);
    mFrame.add(messageLabel);
    mFrame.add(urlLabel);
//    mFrame.add(tfUser);
//    mFrame.add(tfPass);
    mFrame.add(tfTwitterURL);
    mFrame.add(tfMessage);
    mFrame.add(modListLabel);
    mFrame.add(jlModList);
    
    // setup raw image section
//    BufferedImage rawImage = ImageIO.read(new File("C:\\Users\\markd\\Pictures\\Max Mug.png"));
    jlRawImage = new JLabel(new ImageIcon());
    jlRawImage.setBounds(300, 15, 300, 200);
    mFrame.add(jlRawImage);

    // setup processed image section
//    BufferedImage processedImage = ImageIO.read(new File("C:\\Users\\markd\\Pictures\\Max Mug.png"));
    jlProcessedImage = new JLabel(new ImageIcon());
    jlProcessedImage.setBounds(300, 225, 300, 200);
    mFrame.add(jlProcessedImage);

    // setup end of frame section
    mFrame.setLayout(null);
    mFrame.setVisible(true);

    // add logic for concert action
    btnConvert.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  try {
    		  getInput(tfTwitterURL.getText(), tfMessage.getText(), jlModList.getSelectedIndex());
			processImage();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
               
      }
    });

    // listener for getting a twitter image
    btnGetFromTwitter.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	 //getInput(tfUser.getText(), tfPass.getText(), tfTwitterURL.getText());
    	 getInput(tfTwitterURL.getText(), tfMessage.getText(), jlModList.getSelectedIndex());
        retrieveImage();
      }
    });

    // listener for posting to twitter
    btnPostToTwitter.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        postImage();
        JOptionPane.showMessageDialog(null, 
                "Image Posted to Twitter", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
      }
    });
  }
}
