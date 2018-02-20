package jspectrumanalyzer.core;

import java.io.BufferedReader;
import java.io.IOException;
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

	private Pattern patternEU	= Pattern.compile("\"[^\"]+\";\"([0-9.]+)\\s+-\\s+([0-9.]+)\\s+([kM])Hz\";\"([^\"]+)\";\"([^\"]+)\"");
	
	public HashMap<String, FrequencyAllocationTable> getTable() {
		return new HashMap<>(table);
	}
	
	private void loadEurope() {
		BufferedReader reader	= null;
		
		ArrayList<FrequencyBand> bands	= new ArrayList<>();
		
		try {
			/**
			 * Source:
			 * https://www.efis.dk/views2/search-general.jsp
			 */
			reader	= new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/freq-europe.csv")));
			String line	= null;
			int lineNo	= 0;
			while((line = reader.readLine()) != null) {
				lineNo++;
				if (lineNo == 1)
					continue;
				
				//"- Europe (ECA) -";"526.500 - 1606.500 kHz";"Broadcasting";"Inductive applications/Broadcasting"
				//"- Europe (ECA) -";"5925.000 - 6700.000 MHz";"Fixed/Fixed-Satellite (Earth-to-space)/Earth Exploration-Satellite (passive)";"Passive sensors (satellite)/Fixed/FSS Earth stations/Radiodetermination applications/UWB applications/-/ESV/Radio astronomy"
				Matcher m	= patternEU.matcher(line);
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
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
				}
			}
		}
		FrequencyAllocationTable allocationTable	= new FrequencyAllocationTable("Europe", bands);		
		table.put("Europe", allocationTable);
	}
}
