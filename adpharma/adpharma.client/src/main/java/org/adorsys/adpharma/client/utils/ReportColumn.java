package org.adorsys.adpharma.client.utils;

public class ReportColumn {
	private final String name;
	private final float size;
	public ReportColumn(String name, float size) {
		super();
		this.name = name;
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public float getSize() {
		return size;
	}
	@Override
	public String toString() {
		return "ReportColumn [name=" + name + ", size=" + size + "]";
	}
	
}
