/**********************************************************************
 * $Source: /cvsroot/syntax/syntax/src/de/willuhn/jameica/fibu/rmi/Konto.java,v $
 * $Revision: 1.6 $
 * $Date: 2003/12/01 20:29:00 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.jameica.fibu.objects;

import java.rmi.RemoteException;

import de.willuhn.jameica.rmi.DBObject;

/**
 * Diese Klasse bildet die Konten in Fibu ab.
 * @author willuhn
 */
public interface Konto extends DBObject
{

  public final static int KONTOTYP_EINNAHME   = 1;
  public final static int KONTOTYP_AUSGABE    = 2;
  public final static int KONTOTYP_GELD       = 3;
  public final static int KONTOTYP_ANLAGE     = 4;
  public final static int KONTOTYP_PRIVAT     = 5; // bedeutet, dass dessen Bestand zum Jahreswechsel nicht uebernommen wird
  

  /**
   * Liefert die Kontonummer.
   * @return
   * @throws RemoteException
   */
  public String getKontonummer() throws RemoteException;

  /**
   * Liefert den Kontenrahmen, in dem sich das Konto befindet.
   * @return Kontenrahmen des Kontos.
   * @throws RemoteException
   */
  public Kontenrahmen getKontenrahmen() throws RemoteException;

  /**
   * Liefert den Saldo des Kontos.
   * @return Saldo.
   * @throws RemoteException
   */
  public double getSaldo() throws RemoteException;


  /**
   * Liefert den Namen des Kontos.
   * @return
   * @throws RemoteException
   */
  public String getName() throws RemoteException;

  /**
   * Liefert den Typ des Kontos.
   * @see Konto#KONTOTYP_*.
   * @return Typ des Kontos. Zur Kodierung siehe Konto#KONTOTYP_*.
   * @throws RemoteException
   */
  public int getTyp() throws RemoteException;

  /**
   * Liefert den Steuersatz des Kontos.
   * @return
   * @throws RemoteException
   */
  public Steuer getSteuer() throws RemoteException;

}

/*********************************************************************
 * $Log: Konto.java,v $
 * Revision 1.6  2003/12/01 20:29:00  willuhn
 * @B filter in DBIteratorImpl
 * @N InputFelder generalisiert
 *
 * Revision 1.5  2003/11/27 00:21:05  willuhn
 * @N Checks via insertCheck(), deleteCheck() updateCheck() in Business-Logik verlagert
 *
 * Revision 1.4  2003/11/24 15:18:21  willuhn
 * *** empty log message ***
 *
 * Revision 1.3  2003/11/22 20:43:07  willuhn
 * *** empty log message ***
 *
 * Revision 1.2  2003/11/21 02:10:57  willuhn
 * @N buchung dialog works now
 *
 * Revision 1.1  2003/11/20 03:48:44  willuhn
 * @N first dialogues
 *
 **********************************************************************/