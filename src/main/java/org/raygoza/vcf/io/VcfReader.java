package org.raygoza.vcf.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.raygoza.vcf.model.VcfEntry;
import org.raygoza.vcf.model.VcfModel;

public class VcfReader {

	
	
	public VcfReader() {
		
	}
	
	public VcfModel readModel(String filename) throws FileNotFoundException,IOException {
		
		BufferedReader rd = new BufferedReader(new FileReader(filename));
		VcfModel model = new VcfModel();
		
		String line="";
		String chrom ="";
		while(true) {
			line = rd.readLine();
			if(line==null) break;
			
			if(line.startsWith("#")) continue;
			
			
			String[] values = line.split("\t");
			
			if(chrom!=values[0]) chrom=values[0];
			
			VcfEntry entry = new VcfEntry();
			
			entry.setStart(Long.parseLong(values[1]));
			entry.setRef(values[3].trim());
			entry.setAlt(values[4].trim());
			model.addEntry(chrom, entry);
		}
		
		rd.readLine();
		
		
		return model;
	}
	
	
}
