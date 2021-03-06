/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/views/AnfangsbestandNeu.java,v $
 * $Revision: 1.11 $
 * $Date: 2011/05/12 09:10:31 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.gui.views;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.Settings;
import de.willuhn.jameica.fibu.gui.action.AnfangsbestandDelete;
import de.willuhn.jameica.fibu.gui.controller.AnfangsbestandControl;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.Button;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.Container;
import de.willuhn.jameica.gui.util.SimpleContainer;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Legt einen neuen Anfangsbestand an oder bearbeitet einen existierenden.
 * @author willuhn
 */
public class AnfangsbestandNeu extends AbstractView
{
  private final static I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {
		GUI.getView().setTitle(i18n.tr("Anfangsbestand"));

    final AnfangsbestandControl control = new AnfangsbestandControl(this);
    
    Container group = new SimpleContainer(getParent());
    group.addHeadline(i18n.tr("Eigenschaften"));
    group.addLabelPair(i18n.tr("Konto"),         control.getKontoAuswahl());
    group.addLabelPair(i18n.tr("Anfangsbestand"),control.getBetrag());

    boolean closed = Settings.getActiveGeschaeftsjahr().isClosed();
    if (closed) GUI.getView().setErrorText(i18n.tr("Geschäftsjahr ist bereits geschlossen"));

    ButtonArea buttonArea = new ButtonArea();

    Button delete = new Button(i18n.tr("Löschen"), new AnfangsbestandDelete(), getCurrentObject(),false,"user-trash-full.png");
    delete.setEnabled(!closed);
    buttonArea.addButton(delete);
    
    Button store = new Button(i18n.tr("Speichern"), new Action()
    {
      public void handleAction(Object context) throws ApplicationException
      {
        control.handleStore();
      }
    },null,true,"document-save.png");
    store.setEnabled(!closed);
    buttonArea.addButton(store);
    
    buttonArea.paint(getParent());
  }
}

/*********************************************************************
 * $Log: AnfangsbestandNeu.java,v $
 * Revision 1.11  2011/05/12 09:10:31  willuhn
 * @R Back-Buttons entfernt
 * @C GUI-Cleanup
 *
 * Revision 1.10  2010-06-04 00:33:56  willuhn
 * @B Debugging
 * @N Mehr Icons
 * @C GUI-Cleanup
 *
 * Revision 1.9  2010/06/03 14:26:16  willuhn
 * @N Extension zum Zuordnen von Hibiscus-Kategorien zu SynTAX-Buchungsvorlagen
 * @C Code-Cleanup
 *
 * Revision 1.8  2010/06/02 00:02:58  willuhn
 * @N Mehr Icons
 *
 * Revision 1.7  2010/06/01 23:51:56  willuhn
 * @N Neue Icons - erster Teil
 *
 * Revision 1.6  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 **********************************************************************/