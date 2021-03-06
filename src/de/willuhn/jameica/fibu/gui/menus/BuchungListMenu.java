/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/menus/BuchungListMenu.java,v $
 * $Revision: 1.7 $
 * $Date: 2010/08/30 16:31:43 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.menus;

import de.willuhn.jameica.fibu.Fibu;
import de.willuhn.jameica.fibu.gui.action.BuchungDelete;
import de.willuhn.jameica.fibu.gui.action.BuchungExport;
import de.willuhn.jameica.fibu.gui.action.BuchungGeprueft;
import de.willuhn.jameica.fibu.gui.action.BuchungImport;
import de.willuhn.jameica.fibu.gui.action.BuchungNeu;
import de.willuhn.jameica.fibu.gui.action.BuchungUnGeprueft;
import de.willuhn.jameica.fibu.rmi.Buchung;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.parts.CheckedContextMenuItem;
import de.willuhn.jameica.gui.parts.ContextMenu;
import de.willuhn.jameica.gui.parts.ContextMenuItem;
import de.willuhn.jameica.system.Application;
import de.willuhn.logging.Logger;
import de.willuhn.util.ApplicationException;
import de.willuhn.util.I18N;

/**
 * Vorkonfiguriertes Kontext-Menu fuer Buchungs-Listen.
 */
public class BuchungListMenu extends ContextMenu
{
  /**
   * ct.
   */
  public BuchungListMenu()
  {
    I18N i18n = Application.getPluginLoader().getPlugin(Fibu.class).getResources().getI18N();
    this.addItem(new SingleItem(i18n.tr("�ffnen"), new BuchungNeu(),"document-open.png"));
    this.addItem(new GJContextMenuItem(i18n.tr("Neue Buchung..."), new BNeu(),"list-add.png"));
    this.addItem(new GJCheckedContextMenuItem(i18n.tr("L�schen..."), new BuchungDelete(),"user-trash-full.png"));
    this.addItem(ContextMenuItem.SEPARATOR);
    this.addItem(new GeprueftItem(i18n.tr("Als \"gepr�ft\" markieren"), new BuchungGeprueft(),false,"emblem-default.png"));
    this.addItem(new GeprueftItem(i18n.tr("Als \"ungepr�ft\" markieren"), new BuchungUnGeprueft(),true,"edit-undo.png"));
    this.addItem(ContextMenuItem.SEPARATOR);
    this.addItem(new CheckedContextMenuItem(i18n.tr("Exportieren..."),new BuchungExport(),"document-save.png"));
    this.addItem(new ContextMenuItem(i18n.tr("Importieren..."),new BuchungImport(),"document-open.png"));
  }
  
  /**
   * Ueberschrieben, um zu pruefen, ob ein Array oder ein einzelnes Element markiert ist.
   */
  private static class SingleItem extends GJCheckedContextMenuItem
  {
    /**
     * @param text
     * @param action
     * @param icon
     */
    private SingleItem(String text, Action action, String icon)
    {
      super(text,action, icon);
    }
    /**
     * @see de.willuhn.jameica.gui.parts.ContextMenuItem#isEnabledFor(java.lang.Object)
     */
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Buchung[])
        return false;
      return super.isEnabledFor(o);
    }
  }

  /**
   * Ueberschrieben, um zu pruefen, ob die Buchung als geprueft oder ungeprueft markiert werden kann.
   */
  private static class GeprueftItem extends GJCheckedContextMenuItem
  {
    private boolean geprueft = false;
    
    /**
     * @param text
     * @param action
     * @param geprueft
     * @param icon
     */
    private GeprueftItem(String text, Action action, boolean geprueft, String icon)
    {
      super(text,action,icon);
      this.geprueft = geprueft;
    }

    /**
     * @see de.willuhn.jameica.gui.parts.ContextMenuItem#isEnabledFor(java.lang.Object)
     */
    public boolean isEnabledFor(Object o)
    {
      if (o instanceof Buchung)
      {
        try
        {
          Buchung b = (Buchung) o;
          return super.isEnabledFor(o) && ((b.isGeprueft() && geprueft) || (!b.isGeprueft() && !geprueft));
        }
        catch (Exception e)
        {
          Logger.error("unable to check buchung",e);
        }
      }
      return super.isEnabledFor(o);
    }
  }

  /**
   * Erzeugt immer eine neue Buchung - unabhaengig vom Kontext.
   */
  private static class BNeu extends BuchungNeu
  {
    /**
     * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
     */
    public void handleAction(Object context) throws ApplicationException
    {
      super.handleAction(null);
    }
    
  }
}


/*********************************************************************
 * $Log: BuchungListMenu.java,v $
 * Revision 1.7  2010/08/30 16:31:43  willuhn
 * @N Import und Export von Buchungen im XML-Format
 *
 * Revision 1.6  2010/06/04 00:33:56  willuhn
 * @B Debugging
 * @N Mehr Icons
 * @C GUI-Cleanup
 *
 * Revision 1.5  2009/07/03 10:52:19  willuhn
 * @N Merged SYNTAX_1_3_BRANCH into HEAD
 *
 * Revision 1.4.2.1  2009/06/23 10:45:53  willuhn
 * @N Buchung nach Aenderung live aktualisieren
 *
 * Revision 1.4  2006/05/08 15:41:57  willuhn
 * @N Buchungen als geprueft/ungeprueft markieren
 * @N Link Anlagevermoegen -> Buchung
 *
 * Revision 1.3  2005/08/30 22:33:45  willuhn
 * @B bugfixing
 *
 * Revision 1.2  2005/08/25 21:58:57  willuhn
 * @N SKR04
 *
 * Revision 1.1  2005/08/16 23:14:36  willuhn
 * @N velocity export
 * @N context menus
 * @B bugfixes
 *
 **********************************************************************/