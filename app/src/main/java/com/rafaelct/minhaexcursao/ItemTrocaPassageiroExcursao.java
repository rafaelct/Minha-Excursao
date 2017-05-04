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
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.util.*;


public class ItemTrocaPassageiroExcursao
{

	FrameLayout lyt;
	Context context;
	String nome,nomeTroca;
	int idExcursao,idPassageiroTroca,idPassageiro,vagasTotal;
	float valorTotal = 0;
	float valor = 0;

	TextView txtValor,txtValorTotal;
	boolean pagou = false;
	//int idExcursao;
	ImageButton btnDinheiroPassageiro;
	ImageButton btnTrocaPassageiro;

	ControleGerenciarExcursao controle;
	
	public ItemTrocaPassageiroExcursao( final Context context,final boolean espera,final int idExcursao,final int idPassageiroTroca,final int idPassageiro,final String nome,String rg,final int pg,ControleGerenciarExcursao controleGerenciarExcursao) {


		this.context = context;
		this.nome = nome;
		this.controle = controleGerenciarExcursao;
		
		DBConnect dbConnect = new DBConnect(context);
		
		if(espera) {
			this.nomeTroca = dbConnect.getPassageiro(idPassageiro).get("nome");
		} else {
			this.nomeTroca = dbConnect.getPassageiro(idPassageiroTroca).get("nome");
		}
		//this.txt = txtValor;
		//this.idExcursao = idExcursao;


		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.itemtrocapassageirosexcursao,null);

		lyt = (FrameLayout) view.findViewById(R.id.lytFrame);
		final LinearLayout lytOp = (LinearLayout) view.findViewById( R.id.lytOpPassageiro );
		ImageButton btnExcluirPassageiro = (ImageButton) view.findViewById( R.id.btnExcluirPassageiro );
		final ImageButton btnDinheiroPassageiro = (ImageButton) view.findViewById( R.id.btnEditarPassageiro );
		ImageView imgPassageiro = (ImageView) view.findViewById( R.id.imgPassageiro );
		btnTrocaPassageiro = (ImageButton) view.findViewById( R.id.btnTrocaPassageiro );

		this.btnDinheiroPassageiro = btnDinheiroPassageiro;
		this.idExcursao = idExcursao;
		this.idPassageiro = idPassageiro;
		this.idPassageiroTroca = idPassageiroTroca;
		
		if(espera) {
			lyt.setBackgroundColor(Color.parseColor("#33FF0000") );
		}

		TextView txtNome = (TextView) view.findViewById(R.id.nomePassageiro);
		TextView txtRg = (TextView) view.findViewById(R.id.rg);
		//TextView txtValor = (TextView) view.findViewById(R.id.valor);

		txtNome.setText(nome);
		txtRg.setText( String.valueOf( rg ) );
		//txtValor.setText( String.valueOf( valor ) );

		//final TextView txtValor = controle.getTxtValorExcursao();
		//final TextView txtValorTotal = controle.getTxtValorTotalExcursao();

		//this.txtValor = txtValor;
		//this.txtValorTotal = txtValorTotal;

		
		lyt.setOnClickListener(new View.OnClickListener() {

				//private String nomeTroca;

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method

					Toast.makeText( context, nome,30).show();

					/*
					if( controle.getLytAddPassageirosExcursao().getVisibility() == LinearLayout.VISIBLE) {
						controle.getLytAddPassageirosExcursao().setVisibility(LinearLayout.INVISIBLE);
						return;
					}
					*/
					
					DialogInterface.OnClickListener listenerPositivo = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
						
							boolean flagPg;
					
							if( pg == 1 ) {
								flagPg = true;
							} else {
								flagPg = false;
							}
					
							DBConnect dbConnect = new DBConnect(context);
					
							HashMap<String,String> hashTroca = dbConnect.getDadosPassageirosExcursao(idExcursao,idPassageiroTroca);
							HashMap<String,String> hash = dbConnect.getDadosPassageirosExcursao(idExcursao,idPassageiro);
							
							
							int pgTroca = Integer.valueOf( (String) hash.get("pagou") );
					
							boolean flagPgTroca;
					
							if(pgTroca == 1 ) {
								flagPgTroca = true;
							} else {
								flagPgTroca = false;
							}
					
							//String esperaTroca = (String) hashTroca.get("espera");
							
							//String esperaTroca = false;
							
							boolean flagEsperaTroca = true;
					
							if( espera ) {
								flagEsperaTroca = false;
							} else {
								flagEsperaTroca = true;
							}
					
							
							if(espera) {
								//dbConnect.atuDadosPassageirosExcursao( Integer.valueOf( (String) hash.get("id") ),idExcursao,idPassageiroTroca,flagPg,flagEsperaTroca);
								//dbConnect.atuDadosPassageirosExcursao( Integer.valueOf( (String) hashTroca.get("id") ),idExcursao,idPassageiro,flagPgTroca,espera);
								dbConnect.atuEsperaPassageirosExcursao( Integer.valueOf( (String) hash.get("id")),true);
								dbConnect.atuEsperaPassageirosExcursao( Integer.valueOf( (String) hashTroca.get("id")),false);
							} else {
								//dbConnect.atuDadosPassageirosExcursao( Integer.valueOf( (String) hashTroca.get("id") ),idExcursao,idPassageiro,flagPgTroca,flagEsperaTroca);
								//dbConnect.atuDadosPassageirosExcursao( Integer.valueOf( (String) hash.get("id") ),idExcursao,idPassageiroTroca,flagPg,espera);
								dbConnect.atuEsperaPassageirosExcursao( Integer.valueOf( (String) hashTroca.get("id")),false);
								dbConnect.atuEsperaPassageirosExcursao( Integer.valueOf( (String) hash.get("id")),true);
								
							}
							if( lytOp.getVisibility() == LinearLayout.VISIBLE ) {
								lytOp.setVisibility( LinearLayout.INVISIBLE);
							} else {
								lytOp.setVisibility( LinearLayout.VISIBLE);
							}
					
							controle.getLytTrocaPassageiro().setVisibility(LinearLayout.INVISIBLE);
							controle.carregarPassageirosExcursao(context,idExcursao);
					
							p1.dismiss();
							
							//return;

						}


					};
					
					DialogInterface.OnClickListener listenerNegativo = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
							p1.dismiss();
						}


					};

					Alerta alerta;
					
					if( espera ) {
					alerta = new Alerta(context,"Substituir Passageiro","Tem certeza que deseja substituir o passageiro " + nomeTroca + " pelo " + nome + " ?",listenerPositivo,listenerNegativo);
					} else {
						alerta = new Alerta(context,"Substituir Passageiro","Tem certeza que deseja substituir o passageiro " + nome + " pelo " + nomeTroca + " ?",listenerPositivo,listenerNegativo);
					}
					
					alerta.show();
					
				}
			});

		lyt.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View p1)
				{
					// TODO: Implement this method
					Toast.makeText(context,"longo click !!!",10).show();

					if( lytOp.getVisibility() == LinearLayout.VISIBLE ) {
						lytOp.setVisibility( LinearLayout.INVISIBLE);
					} else {
						lytOp.setVisibility( LinearLayout.VISIBLE);
					}
					return true;

				}
			});
	}

	
	public FrameLayout getLayout() {
		return lyt;
	}

}
