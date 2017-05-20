package com.example.bertrand.projetgsb;

import java.util.ArrayList;

public class Protocole {
	
	private int numeroProtocole;
	private ArrayList<GlycemieInsuline> lesGlycemieInsuline;
	
	public Protocole(int unNumero)	{
		this.numeroProtocole = unNumero;
		lesGlycemieInsuline = new ArrayList<GlycemieInsuline>();
	}
	
	public int getNumeroProtocole()	{
		return this.numeroProtocole;
	}
	
	public void ajouter(GlycemieInsuline uneGlycemieInsuline)	{
		this.lesGlycemieInsuline.add(uneGlycemieInsuline);
	}
	public int insuline(double uneGlycemie)	{
		//A écrire
		//Renvoie le nombre d'unités d'insuline en fonction de la glycémie
		int retour = 0;
		for (GlycemieInsuline G : lesGlycemieInsuline){
			if (uneGlycemie >= G.getGlycemieInf() && uneGlycemie <= G.getGlycemieSup()) {
				retour = G.getInsuline();
			}
		}
		return retour;
	}
}
