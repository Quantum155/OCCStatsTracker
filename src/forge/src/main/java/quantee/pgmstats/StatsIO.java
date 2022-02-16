package quantee.pgmstats;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StatsIO {
	
	static void saveStats() throws IOException {
		ArrayList<Integer> savelist = new ArrayList<Integer>();
		savelist.add(OCCEventHandler.lifetimeKills);
		savelist.add(OCCEventHandler.lifetimeDeaths);
		savelist.add(OCCEventHandler.lifetimeObjDamage);
		savelist.add(OCCEventHandler.lifetimeMonuDestroys);
		savelist.add(OCCEventHandler.lifetimeWoolCaps);
		savelist.add(OCCEventHandler.lifetimeSSs);
		savelist.add(OCCLeveling.isLevelingEnabled);
		savelist.add(OCCLeveling.xp);
		savelist.add(OCCLeveling.level);
		// write savelist to savefile
		
		FileOutputStream writeData = new FileOutputStream("OCCStatsSaveFile.txt");
		ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

		writeStream.writeObject(savelist);
		writeStream.flush();
		writeStream.close();
	}
	
	@SuppressWarnings("unchecked")
	static ArrayList<Integer> loadStats() throws Exception {
		FileInputStream readData = new FileInputStream("OCCStatsSaveFile.txt");
	    ObjectInputStream readStream = new ObjectInputStream(readData);

	    ArrayList<Integer> loadlist = (ArrayList<Integer>) readStream.readObject();
	    readStream.close();
	    return loadlist;
	}	
}
