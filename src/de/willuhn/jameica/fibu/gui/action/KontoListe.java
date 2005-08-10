/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/gui/action/KontoListe.java,v $
 * $Revision: 1.1 $
 * $Date: 2005/08/10 17:48:02 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by  bbv AG
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.jameica.fibu.gui.action;

import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.util.ApplicationException;

/**
 * Action zum Laden der Liste der Konten.
 * @author willuhn
 */
public class KontoListe implements Action
{

  /**
   * @see de.willuhn.jameica.gui.Action#handleAction(java.lang.Object)
   */
  public void handleAction(Object context) throws ApplicationException
  {
    GUI.startView(de.willuhn.jameica.fibu.gui.views.KontoListe.class,null);
  }

}


/*********************************************************************
 * $Log: KontoListe.java,v $
 * Revision 1.1  2005/08/10 17:48:02  willuhn
 * @C refactoring
 *
 *********************************************************************/