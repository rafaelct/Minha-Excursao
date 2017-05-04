/*
 * Copyright (C) 2017 Minha Excursão
 *
 * This file is part of Minha Excursão
 *
 * Minha Excursão is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Minha Excursão is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minha Excursão.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.rafaelct.minhaexcursao;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class TrocaPassageiro
{

	LinearLayout lyt;
	Context context;
	String nome;

	ControleGerenciarExcursao controle;
	
	private LinearLayout lytScrollTrocaPassageiro;
	//ControleGerenciarExcursao controleGerenciarExcursao;

	public TrocaPassageiro( final Context context,final int idExcursao,final int idPassageiroTroca, ControleGerenciarExcursao controleGerenciarExcursao) {


		this.context = context;
		
		this.controle = controleGerenciarExcursao;
		
		//this.controleGerenciarExcursao = controleGerenciarExcursao;

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.trocapassageiro,null);

		lyt = (LinearLayout) view.findViewById(R.id.lytTroca);
		lytScrollTrocaPassageiro = (LinearLayout) view.findViewById( R.id.lytScrollTrocaPassageiro);
		ImageView btnCancelarTroca = (ImageView) view.findViewById( R.id.btncancelartroca );
		TextView txtPassageiroTroca = (TextView) view.findViewById( R.id.txtPassageiroTroca);
		
		lytScrollTrocaPassageiro.removeAllViews();
		

		Valor.valorTotal = 0;

		//ControleGerenciarExcursao controle = new ControleGerenciarExcursao(txtNomeExcursao,txtVagasExcursao,txtValorExcursao,txtValorTotalExcursao,lytPassageirosExcursao,lytAddPassageirosExcursao,lytTrocaPassageiro);

		
		DBConnect dbConnect = new DBConnect(context);
		this.nome = dbConnect.getPassageiro(idPassageiroTroca).get("nome");
		txtPassageiroTroca.setText(this.nome);
		
		boolean espera = false;

		String flagEspera = (String) dbConnect.getDadosPassageirosExcursao(idExcursao,idPassageiroTroca).get("espera");

		
		if(flagEspera == null) {
			espera = false;
		} else {
			espera = true;
			
		}

		ArrayList<HashMap<String,String>> arrayPassageirosExcursao = new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String,String>> arrayPassageirosExcursaoEspera = new ArrayList<HashMap<String,String>>();
		
		if(espera) {
		arrayPassageirosExcursao = dbConnect.getPassageirosExcursao(idExcursao);
		} else {
		arrayPassageirosExcursaoEspera = dbConnect.getPassageirosExcursaoEspera((idExcursao) );
		}

		LayoutInflater inflate2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view2 = inflate2.inflate(R.layout.listaespera,null);

		
		if(espera) {
		TituloPassageiros titPassageiros = new TituloPassageiros(context);
		
		
		//lytTrocaPassageiro.removeAllViews();

		lytScrollTrocaPassageiro.addView( titPassageiros.getLayout() );

		for( HashMap<String,String> hash : arrayPassageirosExcursao ) {
			

			/*
			 if( vagas == vagasTotal + 1 ) {
			 lytPassageirosExcursao.addView( view );
			 espera = true;
			 }
			 */
			ItemTrocaPassageiroExcursao item = new ItemTrocaPassageiroExcursao(context,espera, (Integer) Integer.valueOf( idExcursao), idPassageiroTroca,(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"), (Integer) Integer.valueOf( hash.get("pagou") ),controle );
			lytScrollTrocaPassageiro.addView( item.getLayout() );
		}

		} else {
			
		if(arrayPassageirosExcursaoEspera.size() > 0 ) {
			lytScrollTrocaPassageiro.addView( view2 );
			espera = true;
		}

		}
		
		for( HashMap<String,String> hash : arrayPassageirosExcursaoEspera ) {
			//vagas++;

			/*
			 if( vagas == vagasTotal + 1 ) {
			 lytPassageirosExcursao.addView( view );
			 espera = true;
			 }
			 */
			ItemTrocaPassageiroExcursao item = new ItemTrocaPassageiroExcursao(context,espera, (Integer) Integer.valueOf( idExcursao) ,Integer.valueOf( hash.get("id" ) ), idPassageiroTroca,(String) hash.get("nome"), (String) hash.get("rg"), (Integer) Integer.valueOf( hash.get("pagou") ),controle );
			lytScrollTrocaPassageiro.addView( item.getLayout() );
		}


		
		
		btnCancelarTroca.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					controle.getLytTrocaPassageiro().setVisibility( LinearLayout.INVISIBLE );
					
				}
			});
		// fim
		//this.setLytPassageirosExcursao( lytPassageirosExcursao);

		//return lytPassageirosExcursao;

	}

	public LinearLayout getLayout() {
		return lyt;
	}

}
