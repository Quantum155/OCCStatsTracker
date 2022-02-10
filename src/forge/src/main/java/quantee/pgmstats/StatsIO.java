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
		savelist.add(PGMEventHandler.lifetimeKills);
		savelist.add(PGMEventHandler.lifetimeDeaths);
		savelist.add(PGMEventHandler.lifetimeObjDamage);
		savelist.add(PGMEventHandler.lifetimeMonuDestroys);
		savelist.add(PGMEventHandler.lifetimeWoolCaps);
		savelist.add(PGMEventHandler.lifetimeSSs);
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
	
	static ArrayList<Integer> loadStats() throws Exception {
		FileInputStream readData = new FileInputStream("OCCStatsSaveFile.txt");
	    ObjectInputStream readStream = new ObjectInputStream(readData);

	    ArrayList<Integer> loadlist = (ArrayList<Integer>) readStream.readObject();
	    readStream.close();
	    return loadlist;
	}	
}
