package org.adorsys.adpharma.server.rest;

import java.util.List;

import org.adorsys.adpharma.server.utils.PropertyReader;
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
}
