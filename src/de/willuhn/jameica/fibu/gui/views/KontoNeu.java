/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/views/KontoNeu.java,v $
 * $Revision: 1.19 $
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
import de.willuhn.jameica.fibu.gui.action.BuchungNeu;
import de.willuhn.jameica.fibu.gui.action.KontoDelete;
import de.willuhn.jameica.fibu.gui.controller.KontoControl;
import de.willuhn.jameica.fibu.gui.part.BuchungList;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.internal.action.Back;
import de.willuhn.jameica.gui.parts.Button;
import de.willuhn.jameica.gui.util.ButtonArea;
import de.willuhn.jameica.gui.util.Container;
import de.willuhn.jameica.gui.util.Headline;
import de.willuhn.jameica.gui.util.LabelGroup;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Bearbeitet ein Konto.
 * @author willuhn
 */
public class KontoNeu extends AbstractView
{

  /**
   * @see de.willuhn.jameica.gui.AbstractView#bind()
   */
  public void bind() throws Exception
  {

    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();

    final KontoControl control = new KontoControl(this);

    String kr = i18n.tr("unbekannt");
    try
    {
      kr = Settings.getActiveGeschaeftsjahr().getKontenrahmen().getName();
    }
    catch (Exception e)
    {
      Logger.error("error while reading kr",e);
    }

    GUI.getView().setTitle(i18n.tr("Konto bearbeiten. Kontenrahmen: {0}",kr));

    if (!control.getKonto().canChange())
      GUI.getView().setErrorText(i18n.tr("Konto ist ein System-Konto und darf daher nicht ge�ndert werden"));

    Container group = new LabelGroup(getParent(),i18n.tr("Eigenschaften des Kontos"));

    group.addLabelPair(i18n.tr("Name")            , control.getName());
    group.addLabelPair(i18n.tr("Kontonummer")     , control.getKontonummer());
    group.addLabelPair(i18n.tr("Kontoart")        , control.getKontoart());
    group.addLabelPair(i18n.tr("Steuerkonto-Typ") , control.getKontotyp());
    group.addLabelPair(i18n.tr("Steuersatz")      , control.getSteuer());
    group.addLabelPair(i18n.tr("Kontenrahmen")    , control.getKontenrahmen());
    group.addLabelPair(i18n.tr("Saldo")           , control.getSaldo());
    
    ButtonArea buttons = group.createButtonArea(3);
    buttons.addButton(i18n.tr("Zur�ck"), new Back());
    Button delete = new Button(i18n.tr("L�schen"), new KontoDelete(),getCurrentObject());
    delete.setEnabled(control.getKonto().canChange());
    buttons.addButton(delete);
    
    Button store = new Button(i18n.tr("Speichern"), new Action()
    {
      public void handleAction(Object context) throws ApplicationException
      {
        control.handleStore();
      }
    },null,true);
    store.setEnabled(control.getKonto().canChange());
    buttons.addButton(store);

    new Headline(getParent(),i18n.tr("Buchungen auf diesem Konto"));
    new BuchungList(control.getKonto(),new BuchungNeu()).paint(getParent());

  }
}

/*********************************************************************
 * $Log: KontoNeu.java,v $
 * Revision 1.19  2010/06/01 16:37:22  willuhn
 * @C Konstanten von Fibu zu Settings verschoben
 * @N Systemkontenrahmen nach expliziter Freigabe in den Einstellungen aenderbar
 * @C Unterscheidung zwischen canChange und isUserObject in UserObject
 * @C Code-Cleanup
 * @R alte CVS-Logs entfernt
 *
 * Revision 1.18  2009/07/03 10:52:18  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 **********************************************************************/