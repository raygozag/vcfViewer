package org.raygoza.vcf.model;

import java.util.HashMap;
import java.util.Vector;

public class VcfModel {

	
	private HashMap<String, Vector<VcfEntry>> entries = new HashMap<String, Vector<VcfEntry>>(); 
	
	public VcfModel() {
		
	}
	
	
	public void addEntry(String chrom, VcfEntry entry) {
		
		if(!entries.containsKey(chrom)) {
			Vector<VcfEntry> l_entries = new Vector<VcfEntry>();
			l_entries.add(entry);
			entries.put(chrom, l_entries);
		}else {
			Vector<VcfEntry> l_entries = entries.get(chrom);
			l_entries.add(entry);
		}
		
	}
	
	public Vector<VcfEntry> getEntries(String chrom){
		return entries.get(chrom);
	}
	
	public Vector<String> getChromosomes(){
		return new Vector<String>(entries.keySet());
	}
	
	
}
