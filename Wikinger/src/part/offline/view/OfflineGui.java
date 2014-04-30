package part.offline.view;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import edu.stanford.nlp.ling.CoreAnnotations.YearAnnotation;

public class OfflineGui extends JFrame{

	/**
	 * Computed by JVM
	 */
	private static final long serialVersionUID = -1729904057278086015L;
	private JPanel mainPanel, northpanel, southpanel;
	private JButton startButton;
	private JComboBox<String> dbselector;
	private String[] suitableDB = {"mySql-WikiNER", "Oracle 11g Release"};
	private JLabel crawlDB;
	private JProgressBar crawlProgress;
	private JLabel successfulCrawl;
	private JLabel successfulCreateFile;
	private JLabel readAndAddFiletoDB;
	private JProgressBar addingProgress;
	private JLabel successfulOffline;
	
	public OfflineGui(){
		super("Offline Part - WikiNER");
		this.createOfflineFrame();
		this.setVisible(true);
		this.setLocation(350, 180);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createOfflineFrame(){
		this.setSize(new Dimension(550, 400));
		this.mainPanel = new JPanel();
		this.northpanel = new JPanel();
		this.southpanel = new JPanel();
		this.startButton = new JButton("Start Process");
		//JUST FOR TESTING!
		this.dbselector = new JComboBox<>(suitableDB);
		this.crawlDB = new JLabel("Crawling Wiki DB");
		this.crawlProgress = new JProgressBar(0, 100);
		this.successfulCrawl = new JLabel("Extracting Entities successful");
		this.successfulCreateFile = new JLabel("Files created!");
		this.readAndAddFiletoDB = new JLabel("Read Files and add to DB!");
		this.addingProgress = new JProgressBar(0, 100);
		this.successfulOffline = new JLabel("Offline Part successfull");
		
		//TODO:add Components, set Layout for MainPanel and configure Actions!
		this.add(mainPanel);
		mainPanel.add(northpanel);
		mainPanel.add(southpanel);
		northpanel.add(startButton);
		northpanel.add(dbselector);
		northpanel.add(crawlDB);
		northpanel.add(crawlProgress);
		southpanel.add(successfulCrawl);
		southpanel.add(successfulCreateFile);
		southpanel.add(readAndAddFiletoDB);
		southpanel.add(addingProgress);
		southpanel.add(successfulOffline);
	}
	
}
