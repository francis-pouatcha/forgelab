package org.adorsys.adpharma.server.jpa;

import java.util.Comparator;

/**
 * Compare agencies by agency number.
 * 
 * @author francis
 *
 */
public class AgencyByNumberComparator implements Comparator<Agency> {

	@Override
	public int compare(Agency o1, Agency o2) {
		return o1.getAgencyNumber().compareTo(o2.getAgencyNumber());
	}

}
