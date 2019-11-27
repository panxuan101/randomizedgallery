package edu.mc3.randomizer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;


import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterPhotoDownloader {
/**
 * Uses a twitter status id. Downloads the tweet, and saves the image to the computer, and 
 * returns a copy of the image as a buffered image.
 * @param String tweetID Id of the the tweet status
 * @return BufferedImage of the image posted to tweet
 */
	public static BufferedImage getTwitterPhotoFromTwitterID(String tweetID) throws IOException {
		final Twitter twitter = new TwitterFactory().getInstance();
		BufferedImage image = null;
		try {
			Status status = twitter.showStatus(Long.parseLong(tweetID)); //get twitter id from string
			if (status == null) { //check to see if the twitter status existgs
				throw new TwitterException("No tweet");
			} else {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText()); //print status text to verify working
				MediaEntity[] mediaEntities = status.getMediaEntities(); //pulls out media entities to find image url
				MediaEntity entity = mediaEntities[0];
				String mediaUrl = entity.getMediaURL();
				URL url = new URL(mediaUrl); 
				System.out.println(entity.getMediaURL());//get the URL
				image = ImageIO.read(url); //download image and convert to buffered image
			}
		} catch (TwitterException e) {
			System.err.print("Failed to search tweets: " + e.getMessage());
		}
		return image;
	}

}
