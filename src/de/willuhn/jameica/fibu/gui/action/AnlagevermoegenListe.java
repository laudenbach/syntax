/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/action/AnlagevermoegenListe.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/09/26 15:15:39 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.action;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action zum Laden der Anfangsbestaende.
 */
public class AnlagevermoegenListe implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
    GUI.startView(de.willuhn.jameica.fibu.gui.views.AnlagevermoegenListe.class,context);
  }

}


/*********************************************************************
 * $Log: AnlagevermoegenListe.java,v $
 * Revision 1.3  2005/09/26 15:15:39  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2005/09/01 16:34:44  willuhn
 * *** empty log message ***
 *
 * Revision 1.1  2005/08/29 14:26:56  willuhn
 * @N Anlagevermoegen, Abschreibungen
 *
 **********************************************************************/