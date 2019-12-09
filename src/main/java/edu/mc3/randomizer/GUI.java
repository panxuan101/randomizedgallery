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
	// private variables
		private String twitterURL;
		private String message;
		private String mod;
		private BufferedImage rawImage;
		private BufferedImage processedImage;
		private String[] mods;
		JLabel jlProcessedImage;
		JLabel jlRawImage;
		private ImageMods boof;


		/**
		 * default method - creates a form
		 */
		public static void main(String[] args) throws Exception {
			GUI mForm = new GUI();
		}

		/**
		 * Constructor
		 * 
		 * @throws IOException
		 */
		public GUI() throws IOException {

			boof = new ImageMods();
			mods = boof.getAvailableMods();

			initializeForm();

		}

		/**
		 * @return the twitterURL
		 */
		public String getTwitterURL() {
			return this.twitterURL;
		}

		/**
		 * @param twitterURL the twitterURL to set
		 */
		public void setTwitterURL(String twitterURL) {
			this.twitterURL = twitterURL;
		}

			/**
		 * @return the message
		 */
		public String getMessage() {
			return this.message;
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
			return this.mod;
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

		/**
		 * void - gets the image from twitter 
		 * thanks SO for a clean rescaling (getscaledinstance)
		 */
		private void retrieveImage() {
			// 
			try {
				BufferedImage twitterImage = TwitterPhotoDownloader.getTwitterPhotoFromTwitterID(getTwitterID());
				if(twitterImage!=null) {
				setRawImage(twitterImage);
				Image dimg = rawImage.getScaledInstance(jlRawImage.getWidth(), jlRawImage.getHeight(), Image.SCALE_SMOOTH);
				jlRawImage.setIcon(new ImageIcon(dimg));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * void - post the image to twitter
		 */
		private void postImage() {
			UploadPicture.uploadPic(getProcessedImage(), getMessage());
		}

		/** 
		 * sends boof the image and processes
		 * void - IOException
		 */
		private void processImage() throws IOException {
			boof.setMod(mod);
			setProcessedImage(boof.applyFilter(getRawImage()));
			Image dimg = processedImage.getScaledInstance(jlProcessedImage.getWidth(), jlProcessedImage.getHeight(),
					Image.SCALE_SMOOTH);
			jlProcessedImage.setIcon(new ImageIcon(dimg));
		}

		/**
		 * void - gets input from the form
		 * @param URL
		 * @param msg
		 * @param modIndex
		 */
		private void getInput(String URL, String msg, int modIndex) {
			twitterURL = URL;
			message = msg;
			mod = boof.getAvailableMods()[modIndex];

		}

		private void initializeForm() throws IOException {
			// setup frame
			JFrame mFrame = new JFrame("My First GUI");
			mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mFrame.setSize(725, 500);

			// setup buttons
			JButton btnConvert = new JButton("Convert");
			btnConvert.setBounds(140, 155, 100, 25);

			JButton btnGetFromTwitter = new JButton("Get Image");
			btnGetFromTwitter.setBounds(140, 30, 100, 25);

			JButton btnPostToTwitter = new JButton("Post Image");
			btnPostToTwitter.setBounds(140, 285, 100, 25);
			
			// setup input fields
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

			// setup raw image section
			jlRawImage = new JLabel(new ImageIcon());
			jlRawImage.setBounds(300, 15, 300, 200);

			// setup processed image section
			jlProcessedImage = new JLabel(new ImageIcon());
			jlProcessedImage.setBounds(300, 225, 300, 200);

			mFrame.add(btnConvert);
			mFrame.add(btnGetFromTwitter);
			mFrame.add(btnPostToTwitter);
			mFrame.add(messageLabel);
			mFrame.add(urlLabel);
			mFrame.add(tfTwitterURL);
			mFrame.add(tfMessage);
			mFrame.add(modListLabel);
			mFrame.add(jlModList);
			mFrame.add(jlRawImage);
			mFrame.add(jlProcessedImage);

			// setup end of frame section
			mFrame.setLayout(null);
			mFrame.setVisible(true);

			// add logic for convert action
			btnConvert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						getInput(tfTwitterURL.getText(), tfMessage.getText(), jlModList.getSelectedIndex());
						processImage();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			});

			// listener for getting a twitter image
			btnGetFromTwitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getInput(tfTwitterURL.getText(), tfMessage.getText(), jlModList.getSelectedIndex());
					retrieveImage();
				}
			});

			// listener for posting to twitter
			btnPostToTwitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getInput(tfTwitterURL.getText(), tfMessage.getText(), jlModList.getSelectedIndex());
					postImage();
					JOptionPane.showMessageDialog(null, "Image Posted to Twitter", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
}