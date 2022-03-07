package oodp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import oodp.data.base.BaseData;
/**
 * DataHandler helper class that handles all interaction with the filesystem.
 * Acts as an intermediate control class to persist data to the file system in JSON format
 */
public class DataHandler {
	private static Logger logger = LoggerFactory.getLogger(DataHandler.class);
	private static Gson gson = new Gson();
	static String dataFolder = "data";
	static Path dataPath = getDataPath(null);

	/**
	 * This method retrieves all data based on the superclass type of `data`
	 * parameter
	 * 
	 * @param <T> The generic datatype of the class that inherits BaseData
	 * @param data The superclass type is used to determine which folder should be
	 *             read
	 * @return Returns a  ArrayList of data that is read from the file
	 *         system
	 */
	public static <T> ArrayList<T> getAllData(BaseData data) {
		// Check if our /data/{SUPERCLASS} folder is already created, if it isn't then
		// create it
		Path containerPath = directoryCheck(data);

		ArrayList<T> retList = new ArrayList<>();
		File[] files = getFilesInDirectory(containerPath.toString());
		for (File i : files) {
			try {
				retList.add((T) gson.fromJson(readFileAsString(i.getAbsolutePath()), data.getClass()));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return retList;
	}

	/**
	 * This method deletes a specified record based on a given object
	 * 
	 * @param data The superclass type is used to determine which folder should be
	 *             read
	 * @return Returns a array list of data that has been read from the file system
	 */
	public static boolean removeData(BaseData data) {
		// Check if our /data/{SUPERCLASS} folder is already created, if it isn't then
		// create it
		Path containerPath = directoryCheck(data);

		String targetUUID = data.getUUID();
		File[] files = getFilesInDirectory(containerPath.toString());
		for (File f : files) {
			if (f.getName().startsWith(targetUUID)) {
				logger.debug("Deleting " + targetUUID + " from " + containerPath.toString());
				return f.delete();
			}
		}
		logger.debug(
				"Attempted to remove entry " + targetUUID + " but it does not exist in " + containerPath.toString());
		return false;
	}

	/**
	 * This method is used to save data classes to the file system. The file path
	 * location is generated by combining the current working directory, class name
	 * and random uuid.
	 * The file is created if it doesn not already exist 
	 * 
	 * @param data The data to be saved to the file system
	 * @return Returns a boolean indicating whether the file was written to the system
	 *         successfully or not (true = success)
	 */
	public static boolean saveData(BaseData data) {
		return saveData(data,true);
	}
	
	/**
	 * This method is used to save data classes to the file system. The file path
	 * location is generated by combining the current working directory, class name
	 * and random uuid
	 * 
	 * @param data The data to be saved to the file system
	 * @param createIfNotExist Creates the data if it does not exist (Basically true = create, false = update only)
	 * @return Returns a boolean indicating whether the file was written to the system
	 *         successfully or not (true = success)
	 */
	public static boolean saveData(BaseData data, boolean createIfNotExist) {
		data.validateBeforeSave();
		if (data.isValidForSaving()) {
			// Check if our /data folder is already created, if it isn't then create it
			Path containerPath = directoryCheck(data);

			try {
				String fileToWrite = Paths.get(containerPath.toString(), data.getUUID() + ".json").toString();
				// Write the deserialized json string to file
				writeToFile(fileToWrite, data.getJson());
			} catch (IOException e) {
				logger.error(e.getMessage());
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Deletes the current working directory /data recursively THIS METHOD DELETES
	 * ALL DATA GENERATED BY THE APP IN THE /DATA FOLDER
	 * @throws IOException when there is a problem deleting the directory
	 */
	public static void wipeData() throws IOException {
		File f = new File(dataPath.toString());
		FileUtils.deleteDirectory(f);
	}

	/**
	 * Checks if the data directory for the class is already created. The data
	 * directory is created if it isn't already created.
	 * 
	 * @param data The folder name is retrieved from the superclass classname of
	 *             this variable
	 * @return Returns the path where data should be saved for this superclass
	 *         type
	 */
	static Path directoryCheck(BaseData data) {
		// Creates the /data directory if it doesn't already exist
		createDataDirectory();

		Path writePath = getDataPath(data);
		// Creates the /data/{SUPERCLASS} directory if it doesn't already exist
		createDirectory(writePath);

		return writePath;
	}

	/**
	 * No arguments overload for createDataDirectory. This creates the /data
	 * directory
	 */
	static void createDataDirectory() {
		createDirectory(dataPath);
	}

	/**
	 * Creates a directory from a given path if it doesn't already exist
	 */
	static void createDirectory(Path dir) {
		if (!Files.exists(dir, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createDirectories(dir);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * Concatenates the current working directory and returns the full /data Path
	 * object
	 * 
	 * @param data The folder name is retrieved from the superclass classname of
	 *             this variable
	 * @return Returns the path where data should be saved for this superclass
	 *         type
	 */
	static Path getDataPath(BaseData data) {
		if (data == null)
			return Paths.get(System.getProperty("user.dir"), dataFolder);
		return Paths.get(System.getProperty("user.dir"), dataFolder, data.getPath());
	}

	/**
	 * Get all files in the provided directory
	 * 
	 * @param dir the directory path
	 * @return An array of Files in the directory
	 */
	static File[] getFilesInDirectory(String dir) {
		return new File(dir).listFiles();
	}

	/**
	 * Get all files in the provided directory
	 * 
	 * @param fileName the fully qualified file path with file name to write to
	 * @param data The data to be written to file
	 */
	static void writeToFile(String fileName, String data) throws IOException {
		File f = new File(fileName);
		if (f.exists())
			f.delete();

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.append(data);
		writer.close();
	}

	/**
	 * Get all files in the provided directory
	 * 
	 * @param filePath the fully qualified file path of the file to read from
	 * @return The string content of the file located at filePath
	 */
	static String readFileAsString(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}
}
