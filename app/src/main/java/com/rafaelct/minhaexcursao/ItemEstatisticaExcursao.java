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
import java.text.*;

public class ItemEstatisticaExcursao
{

	private TextView txtTotLugares;

	private TextView txtOcuLugares;

	private TextView txtDisLugares;

	private TextView txtTotArrecadado;

	private TextView txtOcuArrecadado;

	private TextView txtDisArrecadado;

	private LinearLayout lyt;

	private float valorExcursao = 0;

	private int lugaresExcursao;

	private TextView txtTotLugaresLista;

	private TextView txtDisLugaresLista;

	private TextView txtOcuLugaresLista;

	private TextView txtTotLugaresEspera;

	private TextView txtOcuLugaresEspera;

	private TextView txtDisLugaresEspera;

	private TextView txtTotArrecadadoLista;

	private TextView txtOcuArrecadadoLista;

	private TextView txtDisArrecadadoLista;

	private TextView txtTotArrecadadoEspera;

	private TextView txtOcuArrecadadoEspera;

	private TextView txtDisArrecadadoEspera;

	private TextView txtOcuPagamentosLista;

	private TextView txtDisPagamentosLista;

	private TextView txtOcuPagamentosEspera;

	private TextView txtDisPagamentosEspera;

	private TextView txtTotPagamentos;

	private TextView txtOcuPagamentos;

	private TextView txtDisPagamentos;
	
	public ItemEstatisticaExcursao(Context context,int idExcursao,int lugares,float valor) {
		
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.itemestatisticaexcursao2,null);
		
		valorExcursao = valor;
		lugaresExcursao = lugares;
		
		lyt = (LinearLayout) view.findViewById( R.id.lytItemEstatistica);
		
		txtTotLugares = (TextView) view.findViewById( R.id.txtTotLugares );
		txtOcuLugares = (TextView) view.findViewById( R.id.txtOcuLugares );
		txtDisLugares = (TextView) view.findViewById( R.id.txtDisLugares );
		
		txtTotLugaresLista = (TextView) view.findViewById( R.id.txtTotLugaresLista );
		txtOcuLugaresLista = (TextView) view.findViewById( R.id.txtOcuLugaresLista );
		txtDisLugaresLista = (TextView) view.findViewById( R.id.txtDisLugaresLista );
		
		txtTotLugaresEspera = (TextView) view.findViewById( R.id.txtTotLugaresEspera );
		txtOcuLugaresEspera = (TextView) view.findViewById( R.id.txtOcuLugaresEspera );
		txtDisLugaresEspera = (TextView) view.findViewById( R.id.txtDisLugaresEspera );
		
		txtTotArrecadado = (TextView) view.findViewById( R.id.txtTotArrecadado );
		txtOcuArrecadado = (TextView) view.findViewById( R.id.txtOcuArrecadado );
		txtDisArrecadado = (TextView) view.findViewById( R.id.txtDisArrecadado );
		
		txtTotArrecadadoLista = (TextView) view.findViewById( R.id.txtTotArrecadadoLista );
		txtOcuArrecadadoLista = (TextView) view.findViewById( R.id.txtOcuArrecadadoLista );
		txtDisArrecadadoLista = (TextView) view.findViewById( R.id.txtDisArrecadadoLista );
		
		txtTotArrecadadoEspera = (TextView) view.findViewById( R.id.txtTotArrecadadoEspera );
		txtOcuArrecadadoEspera = (TextView) view.findViewById( R.id.txtOcuArrecadadoEspera );
		txtDisArrecadadoEspera = (TextView) view.findViewById( R.id.txtDisArrecadadoEspera );
		
		txtTotPagamentos = (TextView) view.findViewById( R.id.txtTotPagamentos );
		txtOcuPagamentos = (TextView) view.findViewById( R.id.txtOcuPagamentos );
		txtDisPagamentos = (TextView) view.findViewById( R.id.txtDisPagamentos );
		
		txtOcuPagamentosLista = (TextView) view.findViewById( R.id.txtOcuPagamentosLista );
		txtDisPagamentosLista = (TextView) view.findViewById( R.id.txtDisPagamentosLista );
		
		txtOcuPagamentosEspera = (TextView) view.findViewById( R.id.txtOcuPagamentosEspera );
		txtDisPagamentosEspera = (TextView) view.findViewById( R.id.txtDisPagamentosEspera );
		
		
		
		carregarPassageirosExcursao(context,idExcursao);
		
		
	}
	
	public void carregarPassageirosExcursao(Context context,int idExcursao) {

		Valor.valorTotal = 0;

		//NumberFormat formato1 = NumberFormat.getCurrencyInstance();
		//this.txtValorTotalExcursao.setText( formato1.format( Valor.valorTotal ) );

		//ControleGerenciarExcursao controle = new ControleGerenciarExcursao(txtNomeExcursao,txtVagasExcursao,txtValorExcursao,txtValorTotalExcursao,lytPassageirosExcursao,lytAddPassageirosExcursao,lytTrocaPassageiro);

		DBConnect dbConnect = new DBConnect(context);
		ArrayList<HashMap<String,String>> arrayPassageirosExcursao = dbConnect.getPassageirosExcursao(idExcursao);
		ArrayList<HashMap<String,String>> arrayPassageirosExcursaoEspera = dbConnect.getPassageirosExcursaoEspera((idExcursao) );

		//LinearLayout lytPassageirosExcursao = controle.getLytPassageirosExcursao();

		//int vagasTotal = Integer.valueOf( txtVagasExcursao.getText().toString() );
		int vagas = 0;
		int pagou = 0;
		float valor = 0;
		
		//LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		//View view = inflate.inflate(R.layout.listaespera,null);

		boolean espera = false;

		//TituloPassageiros titPassageiros = new TituloPassageiros(context);

		//lytPassageirosExcursao.removeAllViews();

		//lytPassageirosExcursao.addView( titPassageiros.getLayout() );

		for( HashMap<String,String> hash : arrayPassageirosExcursao ) {
			vagas++;

			if( Integer.valueOf( (String) hash.get("pagou") ) == 1 ) {
				pagou++;
				valor = Float.valueOf( valorExcursao ) + valor;
				
			}
			
			
			/*
			 if( vagas == vagasTotal + 1 ) {
			 lytPassageirosExcursao.addView( view );
			 espera = true;
			 }
			 */
			//ItemGerenciarPassageirosExcursao item = new ItemGerenciarPassageirosExcursao(context,espera, (Integer) Integer.valueOf( idExcursao),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"), (Integer) Integer.valueOf( hash.get("pagou") ),controle );
			//lytPassageirosExcursao.addView( item.getLayout() );
		}

		if(arrayPassageirosExcursaoEspera.size() > 0 ) {
			//lytPassageirosExcursao.addView( view );
			espera = true;
		}

		int vagasEspera = 0;
		int pagouEspera = 0;
		float valorEspera = 0;
		
		for( HashMap<String,String> hash : arrayPassageirosExcursaoEspera ) {
			//vagas++;
			vagasEspera++;
			
			if( Integer.valueOf( (String) hash.get("pagou") ) == 1 ) {
				pagouEspera++;
				valorEspera = valorExcursao + valorEspera;

			}
			
		}
			
			int vagasTotal = Integer.valueOf( lugaresExcursao );
			
			int vagasTotOcupada = 0;
			
			int vagasDisponiveis = 0;
			
			vagasTotOcupada = vagas + vagasEspera;
			
			txtTotLugares.setText( String.valueOf( vagasTotal ) );
			//txtTotLugaresLista.setText( String.valueOf( vagas ) );
			//txtTotLugaresEspera.setText( String.valueOf( String.valueOf( vagasEspera ) ) );
			
			txtOcuLugares.setText( String.valueOf( vagasTotOcupada ) );
			txtOcuLugaresLista.setText( String.valueOf( vagas ) );
			txtOcuLugaresEspera.setText( String.valueOf( vagasEspera ) );
			
			vagasDisponiveis = vagasTotal - vagasTotOcupada;
			
			txtDisLugares.setText( String.valueOf( vagasDisponiveis ) );
			txtDisLugaresLista.setText( String.valueOf( ( lugaresExcursao - vagas ) ) );
			
			float valorTotal = valorExcursao * lugaresExcursao;
			float valorArrecadado = valorExcursao * ( pagou + pagouEspera );
			float valorArrecadadoLista = valorExcursao * ( pagou );
			float valorArrecadadoEspera = valorExcursao * ( pagouEspera );
			
			float valorFalta = valorTotal - valorArrecadado;
			float valorFaltaLista = valorTotal - valorArrecadadoLista;
			float valorFaltaEspera = ( valorExcursao * vagasEspera ) - valorArrecadadoEspera;
			
			NumberFormat formato1 = NumberFormat.getCurrencyInstance();
			txtTotArrecadado.setText( formato1.format( valorTotal ) );
			txtOcuArrecadado.setText( formato1.format( valorArrecadado ) );
			txtDisArrecadado.setText( formato1.format( valorFalta ) );
			
			txtOcuArrecadadoLista.setText( formato1.format( valorArrecadadoLista ) );
			txtOcuArrecadadoEspera.setText( formato1.format( valorArrecadadoEspera ) );
			
			txtDisArrecadadoLista.setText( formato1.format( valorFaltaLista ) );
			
			txtTotPagamentos.setText( String.valueOf( lugaresExcursao ) );
			txtOcuPagamentos.setText( String.valueOf( ( pagou + pagouEspera )  ) );
			txtOcuPagamentosLista.setText( String.valueOf( pagou ) );
			txtOcuPagamentosEspera.setText( String.valueOf( ( pagouEspera )  ) );
			
			txtDisPagamentos.setText( String.valueOf( vagasEspera - pagouEspera ) );
			txtDisPagamentosLista.setText( String.valueOf( vagas - pagou ) );
			txtDisPagamentosEspera.setText( String.valueOf( vagasEspera - pagouEspera ) );
			
			
			//txtDisArrecadadoEspera.setText( formato1.format( valorFaltaEspera ) );
			
			/*
			 if( vagas == vagasTotal + 1 ) {
			 lytPassageirosExcursao.addView( view );
			 espera = true;
			 }
			 */
			//ItemGerenciarPassageirosExcursao item = new ItemGerenciarPassageirosExcursao(context,espera, (Integer) Integer.valueOf( idExcursao),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"), (Integer) Integer.valueOf( hash.get("pagou") ),controle );
			//lytPassageirosExcursao.addView( item.getLayout() );
		


		// fim
		//this.setLytPassageirosExcursao( lytPassageirosExcursao);

		//return lytPassageirosExcursao;

	}
	
	public LinearLayout getLayout() {
		return lyt;
	}
	
}
