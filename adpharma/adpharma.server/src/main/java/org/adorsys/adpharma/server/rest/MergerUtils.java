package org.adorsys.adpharma.server.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

public class MergerUtils
{

   public static void copyFields(Object source, Object target,
         List<String> fieldNames)
   {

      try
      {
         for (String fieldName : fieldNames)
         {
            if (fieldName.contains(".")) {
            	continue;
            }
            Object fieldValue = FieldUtils.readField(source, fieldName,
                  true);
            FieldUtils.writeField(target, fieldName, fieldValue, true);
         }
      }
      catch (IllegalAccessException e)
      {
         throw new IllegalStateException(e);
      }

   }
   
   public static Map<String, List<String>> getNestedFields(List<String> fieldNames){
	   Map<String, List<String>> result = new HashMap<String, List<String>>();
	   for (String fieldName : fieldNames) {
		   if(StringUtils.countMatches(fieldName, ".")>2) continue;
		   if(!fieldName.contains(".")) continue;
		   String primaryFn = StringUtils.substringBefore(fieldName, ".");
		   String nestedFn = StringUtils.substringAfter(fieldName, ".");
		   List<String> primaryList = result.get(primaryFn);
		   if(primaryList==null) {
			   primaryList=new ArrayList<String>();
			   result.put(primaryFn, primaryList);
		   }
		   primaryList.add(nestedFn);
	   }
	return result;
   }
}
