package edu.mc3.randomizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class UploadPicture {
/**
 * Update our twitter account status with the abstracted picture and message.
 * 
 * @param abstractedImage The abstracted picture that we will post to twitter
 * @param message The message will display in our twitter account
 */

   public static void uploadPic(BufferedImage abstractedImage, String message) {
	  try{
	    StatusUpdate status = new StatusUpdate(message);
    	File file = new File("abstractImage.jpg");
    	ImageIO.write(abstractedImage, "jpg", file);//get the abstracted picture
    	final Twitter twitter = new TwitterFactory().getInstance();
	    status.setMedia(file); //set image into twitter post
	    twitter.updateStatus(status); //set status in twitter post
	  } catch(TwitterException e){
		  System.err.print("Failed to upload the image: " + e.getMessage());
	  } catch(IOException err) {
		  System.err.print("Failed to upload the image: " + err.getMessage());
	  }
	}
   }