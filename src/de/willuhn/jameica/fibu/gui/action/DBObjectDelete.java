/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/action/DBObjectDelete.java,v $
 * $Revision: 1.2 $
 * $Date: 2011/05/11 10:38:52 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.gui.action;

import java.rmi.RemoteException;

import de.willuhn.datasource.rmi.DBObject;
import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.messaging.ObjectDeletedMessage;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.dialogs.YesNoDialog;
import de.willuhn.jameica.messaging.StatusBarMessage;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.system.BackgroundTask;
import de.willuhn.jameica.system.OperationCanceledException;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;
import de.willuhn.util.ProgressMonitor;

/**
 * Generische Action fuer das Loeschen von Datensaetzen.
 */
public class DBObjectDelete implements Action
{
  private final static I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
  
  /**
   * Erwartet ein Objekt vom Typ <code>DBObject</code> oder <code>DBObject[]</code> im Context.
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
		if (context == null)
      throw new ApplicationException(i18n.tr("Keine zu l�schenden Daten ausgew�hlt"));

    if (!(context instanceof DBObject) && !(context instanceof DBObject[]))
    {
      Logger.warn("wrong type to delete: " + context.getClass());
      return;
    }

    boolean array = (context instanceof DBObject[]);
    // Sicherheitsabfrage
    YesNoDialog d = new YesNoDialog(YesNoDialog.POSITION_CENTER);
    if (array)
    {
      d.setTitle(i18n.tr("Daten l�schen"));
      d.setText(i18n.tr("Wollen Sie diese {0} Datens�tze wirklich l�schen?",""+((DBObject[])context).length));
    }
    else
    {
      d.setTitle(i18n.tr("Daten l�schen"));
      d.setText(i18n.tr("Wollen Sie diesen Datensatz wirklich l�schen?"));
    }
    try {
      Boolean choice = (Boolean) d.open();
      if (!choice.booleanValue())
        return;
    }
    catch (OperationCanceledException oce)
    {
      Logger.info(oce.getMessage());
      return;
    }
    catch (Exception e)
    {
      Logger.error("error while deleting objects",e);
      GUI.getStatusBar().setErrorText(i18n.tr("Fehler beim L�schen des Datensatzes"));
      return;
    }

    DBObject[] list = null;
    if (array)
      list = (DBObject[]) context;
    else
      list = new DBObject[]{(DBObject)context}; // Array mit einem Element
    
    Worker worker = new Worker(list);
    if (list.length > 100)
      Application.getController().start(worker);
    else
      worker.run(null);
  }
  
  /**
   * Damit koennen wir lange Loeschvorgaenge ggf. im Hintergrund laufen lassen
   */
  private class Worker implements BackgroundTask
  {
    private boolean cancel = false;
    private DBObject[] list = null;

    /**
     * ct.
     * @param list
     */
    private Worker(DBObject[] list)
    {
      this.list = list;
    }
    
    /**
     * @see de.willuhn.jameica.system.BackgroundTask#interrupt()
     */
    public void interrupt()
    {
      this.cancel = true;
    }

    /**
     * @see de.willuhn.jameica.system.BackgroundTask#isInterrupted()
     */
    public boolean isInterrupted()
    {
      return this.cancel;
    }

    /**
     * @see de.willuhn.jameica.system.BackgroundTask#run(de.willuhn.util.ProgressMonitor)
     */
    public void run(ProgressMonitor monitor) throws ApplicationException
    {
      try
      {
        if (monitor != null)
          monitor.setStatusText(i18n.tr("L�sche {0} Datens�tze",""+list.length));

        double factor = 100d / list.length;
        
        for (int i=0;i<list.length;++i)
        {
          if (monitor != null && i % 4 == 0)
            monitor.setPercentComplete((int)((i+4) * factor));

          if (list[i].isNewObject())
            continue; // muss nicht geloescht werden

          // ok, wir loeschen das Objekt
          list[i].delete();
          Application.getMessagingFactory().sendMessage(new ObjectDeletedMessage(list[i]));
        }
        
        if (monitor != null)
          monitor.setPercentComplete(100);
        
        String text = i18n.tr("Datensatz gel�scht.");
        if (list.length > 1)
          text = i18n.tr("{0} Datens�tze gel�scht.",""+list.length);
        
        Application.getMessagingFactory().sendMessage(new StatusBarMessage(text,StatusBarMessage.TYPE_SUCCESS));
        if (monitor != null)
        {
          monitor.setStatusText(text);
          monitor.setStatus(ProgressMonitor.STATUS_DONE);
        }

      }
      catch (RemoteException e)
      {
        Logger.error("error while deleting objects",e);
        Application.getMessagingFactory().sendMessage(new StatusBarMessage(i18n.tr("Fehler beim L�schen der Datens�tze."), StatusBarMessage.TYPE_ERROR));

        if (monitor != null)
        {
          monitor.setStatus(ProgressMonitor.STATUS_ERROR);
          monitor.setStatusText(i18n.tr("Fehler beim L�schen der Daten"));
          monitor.log(e.toString());
        }
      }
      catch (ApplicationException ae)
      {
        if (monitor != null)
        {
          monitor.setStatus(ProgressMonitor.STATUS_ERROR);
          monitor.setStatusText(ae.getMessage());
        }
        throw ae;
      }
    }
    
  }

}


/**********************************************************************
 * $Log: DBObjectDelete.java,v $
 * Revision 1.2  2011/05/11 10:38:52  willuhn
 * @N OCE fangen
 *
 * Revision 1.1  2010-08-27 11:19:40  willuhn
 * @N Import-/Export-Framework incl. XML-Format aus Hibiscus portiert
 *
 **********************************************************************/