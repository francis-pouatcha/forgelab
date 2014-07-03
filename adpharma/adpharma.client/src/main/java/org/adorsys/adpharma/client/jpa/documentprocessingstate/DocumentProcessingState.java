package org.adorsys.adpharma.client.jpa.documentprocessingstate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adorsys.javaext.description.Description;

@Description("DocumentProcessingState_description")
public enum DocumentProcessingState
{
	@Description("DocumentProcessingState_SUSPENDED_description")
	SUSPENDED, @Description("DocumentProcessingState_ONGOING_description")
	ONGOING, @Description("DocumentProcessingState_RESTORED_description")
	RESTORED, @Description("DocumentProcessingState_CLOSED_description")
	CLOSED,@Description("DocumentProcessingState_SENT_description") 
	SENT ,@Description("DocumentProcessingState_RETREIVED_description") 
	RETREIVED ;

	public static DocumentProcessingState[] valuesWithNull(){
		DocumentProcessingState[] values = DocumentProcessingState.values();
		DocumentProcessingState[] copyOf = Arrays.copyOf(values, values.length+1);
		return copyOf;

	}
}