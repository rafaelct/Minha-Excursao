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

public class EstatisticaExcursao
{

	LinearLayout lyt;
	Context context;
	String nome;
	private int lugaresExcursao;
	private float valorExcursao = 0;
	
	ControleGerenciarExcursao controle;

	private LinearLayout lytScrollEstatistica;
	//ControleGerenciarExcursao controleGerenciarExcursao;

	public EstatisticaExcursao( final Context context,final int idExcursao, ControleGerenciarExcursao controleGerenciarExcursao) {


		this.context = context;

		this.controle = controleGerenciarExcursao;

		//this.controleGerenciarExcursao = controleGerenciarExcursao;

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.estatisticaexcursao,null);

		lyt = (LinearLayout) view.findViewById(R.id.lytEstatistica);
		lytScrollEstatistica = (LinearLayout) view.findViewById( R.id.lytScrollEstatistica);
		ImageView btnEstatisticaCancelar = (ImageView) view.findViewById( R.id.btnEstatisticaCancelar );
		ImageView btnEstatisticaExportar = (ImageView) view.findViewById( R.id.btnExportar );
		
		TextView txtEstatisticaExcursao = (TextView) view.findViewById( R.id.txtEstatisticaExcursao);

		lytScrollEstatistica.removeAllViews();


		Valor.valorTotal = 0;

		//ControleGerenciarExcursao controle = new ControleGerenciarExcursao(txtNomeExcursao,txtVagasExcursao,txtValorExcursao,txtValorTotalExcursao,lytPassageirosExcursao,lytAddPassageirosExcursao,lytTrocaPassageiro);


		DBConnect dbConnect = new DBConnect(context);
		HashMap<String,String> hashExcursao = dbConnect.getExcursao(idExcursao);
		
		txtEstatisticaExcursao.setText( (String) hashExcursao.get("nome") );
		int lugares = Integer.valueOf( (String) hashExcursao.get("vagas") );
		float valor = Float.valueOf( (String) hashExcursao.get("valor") );
		
		ItemEstatisticaExcursao itemEstatisticaExcursao = new ItemEstatisticaExcursao(context,idExcursao,lugares,valor);
		
		lytScrollEstatistica.addView( itemEstatisticaExcursao.getLayout() );
		
		btnEstatisticaCancelar.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					controle.getLytTrocaPassageiro().setVisibility( LinearLayout.INVISIBLE );

				}
			});
			
		btnEstatisticaExportar.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					String txt = exportarTxt();
					ClipboardManager clip =  (ClipboardManager)
						context.getSystemService(Context.CLIPBOARD_SERVICE);
						clip.setText( txt );
						Toast.makeText(context,"Estatistica copiada para a area de transferencia",25).show();

				}

				private String exportarTxt()
				{
					// TODO: Implement this method
					
					Valor.valorTotal = 0;

					//NumberFormat formato1 = NumberFormat.getCurrencyInstance();
					//this.txtValorTotalExcursao.setText( formato1.format( Valor.valorTotal ) );

					//ControleGerenciarExcursao controle = new ControleGerenciarExcursao(txtNomeExcursao,txtVagasExcursao,txtValorExcursao,txtValorTotalExcursao,lytPassageirosExcursao,lytAddPassageirosExcursao,lytTrocaPassageiro);
					StringBuffer buf1 = new StringBuffer();
					
					
					DBConnect dbConnect = new DBConnect(context);
					ArrayList<HashMap<String,String>> arrayPassageirosExcursao = dbConnect.getPassageirosExcursao(idExcursao);
					ArrayList<HashMap<String,String>> arrayPassageirosExcursaoEspera = dbConnect.getPassageirosExcursaoEspera((idExcursao) );
					
					String nomeExcursao = dbConnect.getExcursao(idExcursao).get("nome");
					lugaresExcursao = Integer.valueOf( (String) dbConnect.getExcursao(idExcursao).get("vagas") );
					valorExcursao = Float.valueOf( (String) dbConnect.getExcursao(idExcursao).get("valor") );
					
					
					
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

					buf1.append("Lista de passageiros\n\n");
					
					for( HashMap<String,String> hash : arrayPassageirosExcursao ) {
						vagas++;

						String pagouStr = "";
						
						if( Integer.valueOf( (String) hash.get("pagou") ) == 1 ) {
							pagou++;
							valor = Float.valueOf( valorExcursao ) + valor;
							pagouStr = "(pagou)";

						}

						buf1.append( (String) hash.get("nome") + " " + pagouStr + "\n" );
						
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
						buf1.append("\nLista de espera\n\n");
					}

					int vagasEspera = 0;
					int pagouEspera = 0;
					float valorEspera = 0;

					for( HashMap<String,String> hash : arrayPassageirosExcursaoEspera ) {
						//vagas++;
						vagasEspera++;

						String pagouStr = "";
						
						if( Integer.valueOf( (String) hash.get("pagou") ) == 1 ) {
							pagouEspera++;
							valorEspera = valorExcursao + valorEspera;
							pagouStr = "(pagou)";
						}
						
						buf1.append( hash.get("nome") + " " + pagouStr + "\n");
						
					}

					
					StringBuffer buf2 = new StringBuffer();
					
					int vagasTotal =  lugaresExcursao;

					int vagasTotOcupada = 0;

					int vagasDisponiveis = 0;

					vagasTotOcupada = vagas + vagasEspera;

					//txtTotLugares.setText( String.valueOf( vagasTotal ) );
					
					String expTotLugares =  String.valueOf( vagasTotal );
					//txtTotLugaresLista.setText( String.valueOf( vagas ) );
					//txtTotLugaresEspera.setText( String.valueOf( String.valueOf( vagasEspera ) ) );

					//txtOcuLugares.setText( String.valueOf( vagasTotOcupada ) );
					String expOcuLugares = String.valueOf( vagasTotOcupada );
					
					//txtOcuLugaresLista.setText( String.valueOf( vagas ) );
					String expOcuLugaresLista = String.valueOf( vagas );
					
					//txtOcuLugaresEspera.setText( String.valueOf( vagasEspera ) );
					String expOcuLugaresEspera = String.valueOf( vagasEspera );
					
					vagasDisponiveis = vagasTotal - vagasTotOcupada;

					//txtDisLugares.setText( String.valueOf( vagasDisponiveis ) );
					String expDisLugares = String.valueOf( vagasDisponiveis );
					
					//txtDisLugaresLista.setText( String.valueOf( ( lugaresExcursao - vagas ) ) );
					String expDisLugaresLista = String.valueOf( ( lugaresExcursao - vagas ) );
					
					float valorTotal = valorExcursao * lugaresExcursao;
					float valorArrecadado = valorExcursao * ( pagou + pagouEspera );
					float valorArrecadadoLista = valorExcursao * ( pagou );
					float valorArrecadadoEspera = valorExcursao * ( pagouEspera );

					float valorFalta = valorTotal - valorArrecadado;
					float valorFaltaLista = valorTotal - valorArrecadadoLista;
					float valorFaltaEspera = ( valorExcursao * vagasEspera ) - valorArrecadadoEspera;

					NumberFormat formato1 = NumberFormat.getCurrencyInstance();
					//txtTotArrecadado.setText( formato1.format( valorTotal ) );
					String expTotArrecadado = formato1.format( valorTotal );
					
					//txtOcuArrecadado.setText( formato1.format( valorArrecadado ) );
					String expOcuArrecadado = formato1.format( valorArrecadado );
					
					//txtDisArrecadado.setText( formato1.format( valorFalta ) );
					String expDisArrecadado = formato1.format( valorFalta );
					
					//txtOcuArrecadadoLista.setText( formato1.format( valorArrecadadoLista ) );
					String expOcuArrecadadoLista = formato1.format( valorArrecadadoLista );
					
					//txtOcuArrecadadoEspera.setText( formato1.format( valorArrecadadoEspera ) );
					String expOcuArrecadadoEspera = formato1.format( valorArrecadadoEspera );
					
					//txtDisArrecadadoLista.setText( formato1.format( valorFaltaLista ) );
					String expDisArrecadadoLista = formato1.format( valorFaltaLista );
					
					//txtTotPagamentos.setText( String.valueOf( lugaresExcursao ) );
					String expTotPagamentos = String.valueOf( lugaresExcursao );
					
					//txtOcuPagamentos.setText( String.valueOf( ( pagou + pagouEspera )  ) );
					String expOcuPagamentos = String.valueOf( ( pagou + pagouEspera ) );
					
					//txtOcuPagamentosLista.setText( String.valueOf( pagou ) );
					String expOcuPagamentosLista = String.valueOf( pagou );
					
					//txtOcuPagamentosEspera.setText( String.valueOf(  pagouEspera) );
					String expOcuPagamentosEspera = String.valueOf(  pagouEspera );
					
					//txtDisPagamentos.setText( String.valueOf( vagasEspera - pagouEspera ) );
					String expDisPagamentos = String.valueOf( vagasEspera - pagouEspera );
					
					//txtDisPagamentosLista.setText( String.valueOf( vagas - pagou ) );
					String expDisPagamentosLista = String.valueOf( vagas - pagou );
					
					//txtDisPagamentosEspera.setText( String.valueOf( vagasEspera - pagouEspera ) );
					String expDisPagamentosEspera = String.valueOf( vagasEspera - pagouEspera );

					buf2.append("Excursão: "+ nomeExcursao + "\n\n");
					buf2.append("            | Geral | Lista | Espera\n\n");
					buf2.append("Lugares\n\n");
					buf2.append("Total       | "+ expTotLugares+" | N/A | N/A\n");
					buf2.append("Ocupados    | "+ expOcuLugares+" | "+ expOcuLugaresLista+" | "+expOcuLugaresEspera+"\n");
					buf2.append("Disponivel  | "+ expDisLugares+" | "+ expDisLugaresLista+" | N/A\n\n");
					buf2.append("Arrecadação\n\n");
					buf2.append("Total       | "+ expTotArrecadado+" | N/A | N/A\n");
					buf2.append("Arrecadado  | "+ expOcuArrecadado+" | "+ expOcuArrecadadoLista+" | "+ expOcuArrecadadoEspera+"\n");
					buf2.append("Falta       | "+ expDisArrecadado+" | "+ expDisArrecadadoLista+" | N/A \n\n");
					buf2.append("Pagamentos\n\n");
					buf2.append("Total       | "+ expTotPagamentos + " | N/A | N/A\n");
					buf2.append("Pagaram     | "+ expOcuPagamentos+" | " + expOcuPagamentosLista+" | "+expOcuPagamentosEspera+"\n");
					buf2.append("Falta       | "+ expDisPagamentos+" | " + expDisPagamentosLista+" | "+expDisPagamentosEspera+"\n\n");
					
					buf2.append( buf1.toString() );
					
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
					
					return buf2.toString();
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
