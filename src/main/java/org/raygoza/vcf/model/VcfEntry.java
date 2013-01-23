/**
 * 
 */
package org.raygoza.vcf.model;

/**
 * @author Juan Antonio Raygoza Garay
 *
 */
public class VcfEntry {

	private String ref;
	private String alt;
	private long start;
	private int quality;
	
	public VcfEntry() {
		
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	
	
	
	
	
	
	
}
