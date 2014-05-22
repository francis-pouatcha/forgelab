package org.adorsys.adpharma.server.jpa;

import java.util.Comparator;

public class PaymentItemComparator implements Comparator<PaymentItem> {
	
	@Override
	public int compare(PaymentItem o1, PaymentItem o2) {
		return o1.getPaymentMode().compareTo(o2.getPaymentMode());
	}

}
