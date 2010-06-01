/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/views/BuchungNeu.java,v $
 * $Revision: 1.42 $
 * $Date: 2010/06/01 16:37:22 $
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
import de.willuhn.jameica.fibu.gui.action.BuchungDelete;
import de.willuhn.jameica.fibu.gui.controller.BuchungControl;
import de.willuhn.jameica.fibu.rmi.Anlagevermoegen;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.internal.action.Back;
import de.willuhn.jameica.gui.parts.Button;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.system.Application;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Erzeugt eine neue Buchung oder bearbeitet eine existierende.
 * @author willuhn
 */
public class BuchungNeu extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {

    final I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

    // Headline malen
		GUI.getView().setTitle(i18n.tr("Buchung bearbeiten"));

    final BuchungControl control = new BuchungControl(this);
    
    // Gruppe Konto erzeugen
    LabelGroup kontoGroup = new LabelGroup(getParent(),i18n.tr("Eigenschaften"));

    kontoGroup.addLabelPair(i18n.tr("Vorlage"),         control.getBuchungstemplate());
    kontoGroup.addSeparator();
    kontoGroup.addLabelPair(i18n.tr("Datum"),           control.getDatum());
    kontoGroup.addLabelPair(i18n.tr("Soll-Konto"),      control.getSollKontoAuswahl());
    kontoGroup.addLabelPair(i18n.tr("Haben-Konto"),     control.getHabenKontoAuswahl());
    kontoGroup.addLabelPair(i18n.tr("Text"),            control.getText());
    kontoGroup.addLabelPair(i18n.tr("Beleg-Nr."),       control.getBelegnummer());
    kontoGroup.addLabelPair(i18n.tr("Brutto-Betrag"),   control.getBetrag());
    kontoGroup.addLabelPair(i18n.tr("Steuersatz"),      control.getSteuer());
    
    Buchung b = control.getBuchung();
    Anlagevermoegen av = b.getAnlagevermoegen();
    if (av != null)
      kontoGroup.addLabelPair(i18n.tr("Zugeh�riges Anlagegut"), control.getAnlageVermoegenLink());
    else if (b.isNewObject())
      kontoGroup.addCheckbox(control.getAnlageVermoegen(),i18n.tr("In Anlageverm�gen �bernehmen"));

    // wir machen das Datums-Feld zu dem mit dem Focus.
    control.getDatum().focus();

    boolean closed = Settings.getActiveGeschaeftsjahr().isClosed();
    if (closed) GUI.getView().setErrorText(i18n.tr("Buchung kann nicht mehr ge�ndert werden, da das Gesch�ftsjahr abgeschlossen ist"));

    // und noch die Abschicken-Knoepfe
    ButtonArea buttonArea = kontoGroup.createButtonArea(4);
    buttonArea.addButton(i18n.tr("Zur�ck"), new Back());

    Button delete = new Button(i18n.tr("L�schen"), new BuchungDelete(), getCurrentObject());
    delete.setEnabled(!closed);
    buttonArea.addButton(delete);

    Button store = new Button(i18n.tr("Speichern"),new Action() {
      public void handleAction(Object context) throws ApplicationException
      {
        control.handleStore(false);
      }
    });
    store.setEnabled(!closed);
    buttonArea.addButton(store);

    Button storeNew = new Button(i18n.tr("Speichern und n�chste Buchung"),new Action() {
      public void handleAction(Object context) throws ApplicationException
      {
        control.handleStore(true);
      }
    },null,true);
    storeNew.setEnabled(!closed);
    buttonArea.addButton(storeNew);
  }
}

/*********************************************************************
 * $Log: BuchungNeu.java,v $
 * Revision 1.42  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 **********************************************************************/