package part.online.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import part.online.view.OnlineView;
import data.Entity;
import data.control.FileInput;
import data.control.StanfordNER;

public class ViewController {

    private String filePath;
    private String classifierPath;
    private FileInput fileReader;
    private String[] fileContent;
    private StanfordNER ner;

    /**
     * 
     * @param incPath
     * @param classifierPath
     */
    public ViewController(String incPath, String classifierPath) {
	this.filePath = incPath;
	this.classifierPath = classifierPath;
	ner = new StanfordNER(classifierPath);

    }

    /**
     * reads the inputted .txt File
     */
    public void readIncTextFile() {
	try {
	    fileReader = new FileInput(filePath);
	    fileContent = fileReader.loadCompleteFile();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * calls the chosen Classifier and extracts the Entities
     */
    public void handleEntities() {
	ArrayList<Entity> allEntities;
	String incText = Arrays.toString(fileContent);
	try {
	    allEntities = ner.extractEntities(incText);

	    for (Entity entity : allEntities) {
		System.out.println(entity.toString());
	    }
	} catch (IOException | ParserConfigurationException | SAXException e) {
	    e.printStackTrace();
	}
    }

}
