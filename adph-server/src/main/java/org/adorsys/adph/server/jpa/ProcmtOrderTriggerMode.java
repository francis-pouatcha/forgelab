package org.adorsys.adph.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("org.adorsys.adph.server.jpa.ProcmtOrderTriggerMode.description")
public enum ProcmtOrderTriggerMode
{
   @Description("org.adorsys.adph.server.jpa.ProcmtOrderTriggerMode.MANUELLE.description")
   MANUELLE, @Description("org.adorsys.adph.server.jpa.ProcmtOrderTriggerMode.RUPTURE_STOCK.description")
   RUPTURE_STOCK, @Description("org.adorsys.adph.server.jpa.ProcmtOrderTriggerMode.PLUS_VENDU.description")
   PLUS_VENDU
}