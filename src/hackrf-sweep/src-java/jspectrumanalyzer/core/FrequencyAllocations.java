package jspectrumanalyzer.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyAllocations {
	private HashMap<String, FrequencyAllocationTable> table	= new HashMap<>();
	
	public FrequencyAllocations() {
		loadEurope();
	}

	
	
	public HashMap<String, FrequencyAllocationTable> getTable() {
		return new HashMap<>(table);
	}

	private void loadEurope() {
		loadTableFromCSV("Europe", getClass().getResourceAsStream("/resources/freq-europe.csv"));		
		loadTableFromCSV("USA", getClass().getResourceAsStream("/resources/freq-usa.csv"));
	}
	
	private void loadTableFromCSV(String locationName, InputStream is) {
		BufferedReader reader	= null;
		
		ArrayList<FrequencyBand> bands	= new ArrayList<>();
		
		try {
			/**
			 * Source:
			 * https://www.efis.dk/views2/search-general.jsp
			 */
			//"- Europe (ECA) -";"526.500 - 1606.500 kHz";"Broadcasting";"Inductive applications/Broadcasting"
			//"- Europe (ECA) -";"5925.000 - 6700.000 MHz";"Fixed/Fixed-Satellite (Earth-to-space)/Earth Exploration-Satellite (passive)";"Passive sensors (satellite)/Fixed/FSS Earth stations/Radiodetermination applications/UWB applications/-/ESV/Radio astronomy"
			Pattern patternCSV	= Pattern.compile("\"[^\"]+\";\"([0-9.]+)\\s+-\\s+([0-9.]+)\\s+([kM])Hz\";\"([^\"]+)\";\"([^\"]+)\"");
			
			reader	= new BufferedReader(new InputStreamReader(is));
			String line	= null;
			int lineNo	= 0;
			while((line = reader.readLine()) != null) {
				lineNo++;
				if (lineNo == 1)
					continue;
				
				Matcher m	= patternCSV.matcher(line);
				if (m.find()) {
					double multiplier	= m.group(3).equals("k") ? 1000 : 1000000;
					long startFreq	= Math.round(Double.parseDouble(m.group(1)) * multiplier);
					long stopFreq	= Math.round(Double.parseDouble(m.group(2)) * multiplier);
					String name	= m.group(4);
					String applications	= m.group(5);
					FrequencyBand band	= new FrequencyBand(startFreq, stopFreq, name, applications);
					bands.add(band);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
				}
			}
		}
		FrequencyAllocationTable allocationTable	= new FrequencyAllocationTable(locationName, bands);
		table.put(locationName, allocationTable);
	}
}
