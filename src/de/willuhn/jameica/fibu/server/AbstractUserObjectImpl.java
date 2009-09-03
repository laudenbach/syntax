/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/server/AbstractUserObjectImpl.java,v $
 * $Revision: 1.5 $
 * $Date: 2009/09/03 14:31:10 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.rmi.Mandant;
import de.willuhn.jameica.fibu.rmi.UserObject;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * @author willuhn
 */
public abstract class AbstractUserObjectImpl extends AbstractDBObject implements UserObject
{
  private I18N i18n = null;
  
  /**
   * Erzeugt ein neues User-Objekt.
   * @throws RemoteException
   */
  public AbstractUserObjectImpl() throws RemoteException
  {
    super();
    this.i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#getForeignObject(java.lang.String)
   */
  public Class getForeignObject(String field) throws RemoteException
  {
    if ("mandant_id".equals(field))
      return Mandant.class;
    return null;
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#deleteCheck()
   */
  protected void deleteCheck() throws ApplicationException
  {
    try
    {
      if (!isUserObject())
        throw new ApplicationException("Datensatz geh�rt zum initialen Datenbestand und darf daher nicht gel�scht werden.");
    }
    catch (RemoteException e)
    {
      Logger.error("unable to check user object",e);
      throw new ApplicationException(i18n.tr("Fehler beim L�schen des Datensatzes"));
    }
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#insertCheck()
   */
  protected void insertCheck() throws ApplicationException
  {
    try {
      if (!isUserObject())
        throw new ApplicationException("Datensatz geh�rt zum initialen Datenbestand und darf daher nicht ge�ndert werden.");
    }
    catch (RemoteException e)
    {
      throw new ApplicationException(i18n.tr("Fehler bei der �berpr�fung des Datensatzes"),e);
    }
    super.insertCheck();
  }

  /**
   * @see de.willuhn.datasource.db.AbstractDBObject#updateCheck()
   */
  protected void updateCheck() throws ApplicationException
  {
    insertCheck();
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.UserObject#isUserObject()
   */
  public boolean isUserObject() throws RemoteException
  {
    // TODO: Koennte man ersetzen gegen einen System-Kontenrahmen, der per Default read-only ist,
    //       der Schreibschutz vom User aber explizit entfernt werden koennte
    return getMandant() != null || Settings.inUpdate();
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.UserObject#getMandant()
   */
  public Mandant getMandant() throws RemoteException
  {
    return (Mandant) getAttribute("mandant_id");
  }

  /**
   * @see de.willuhn.jameica.fibu.rmi.UserObject#setMandant(de.willuhn.jameica.fibu.rmi.Mandant)
   */
  public void setMandant(Mandant mandant) throws RemoteException
  {
    if (!this.isNewObject())
      throw new RemoteException("Datensatz geh�rt zum initialen Datenbestand und darf daher nicht ge�ndert werden.");
    setAttribute("mandant_id",mandant);
  }
}

/*********************************************************************
 * $Log: AbstractUserObjectImpl.java,v $
 * Revision 1.5  2009/09/03 14:31:10  willuhn
 * *** empty log message ***
 *
 * Revision 1.4  2009/07/03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.2  2006/12/27 14:42:23  willuhn
 * @N Update fuer MwSt.-Erhoehung
 *
 * Revision 1.1  2006/01/02 15:18:29  willuhn
 * @N Buchungs-Vorlagen
 *
 **********************************************************************/