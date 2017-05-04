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
import java.text.*;
import java.util.*;

public class ItemGerenciarPassageirosExcursao
{

	FrameLayout lyt;
	Context context;
	String nome;
	int idExcursao,idPassageiro,vagasTotal;
	float valorTotal = 0;
	float valor = 0;
	
	TextView txtValor,txtValorTotal;
	boolean pagou = false;
	//int idExcursao;
	ImageButton btnDinheiroPassageiro;
	ImageButton btnTrocaPassageiro;
	
	public ItemGerenciarPassageirosExcursao( final Context context,final boolean espera,final int idExcursao,final int idPassageiro,final String nome,String rg,final int pg,final ControleGerenciarExcursao controle) {


		this.context = context;
		this.nome = nome;
		//this.txt = txtValor;
		//this.idExcursao = idExcursao;
		
		
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.itemgerenciarpassageirosexcursao,null);

		lyt = (FrameLayout) view.findViewById(R.id.lytFrame);
		final LinearLayout lytOp = (LinearLayout) view.findViewById( R.id.lytOpPassageiro );
		ImageButton btnExcluirPassageiro = (ImageButton) view.findViewById( R.id.btnExcluirPassageiro );
		final ImageButton btnDinheiroPassageiro = (ImageButton) view.findViewById( R.id.btnEditarPassageiro );
		ImageView imgPassageiro = (ImageView) view.findViewById( R.id.imgPassageiro );
		btnTrocaPassageiro = (ImageButton) view.findViewById( R.id.btnTrocaPassageiro );
		
		this.btnDinheiroPassageiro = btnDinheiroPassageiro;
		this.idExcursao = idExcursao;
		this.idPassageiro = idPassageiro;
		
		if(espera) {
			lyt.setBackgroundColor(Color.parseColor("#33FF0000") );
		}
		
		DBConnect dbConnect = new DBConnect(context);
		int qtdEspera = dbConnect.qtdPassageirosExcursaoEspera(idExcursao);
		
		// se nao tem passageiros na lista devespera , desabilita botao de troca.
		if(qtdEspera == 0 ) {
			btnTrocaPassageiro.setVisibility( ImageButton.INVISIBLE );
		}
		
		
		TextView txtNome = (TextView) view.findViewById(R.id.nomePassageiro);
		TextView txtRg = (TextView) view.findViewById(R.id.rg);
		//TextView txtValor = (TextView) view.findViewById(R.id.valor);

		txtNome.setText(nome);
		txtRg.setText( String.valueOf( rg ) );
		//txtValor.setText( String.valueOf( valor ) );

		final TextView txtValor = controle.getTxtValorExcursao();
		final TextView txtValorTotal = controle.getTxtValorTotalExcursao();
		
		this.txtValor = txtValor;
		this.txtValorTotal = txtValorTotal;
		
		if(pg == 0) {
			pagou = false;
			//btnDinheiroPassageiro.setBackgroundResource( R.drawable.dinheiro3 );
			imgPassageiro.setBackgroundResource( R.drawable.passageiro );
			valorTotal = 0;
		} else {
			pagou = true;
			//btnDinheiroPassageiro.setBackgroundResource( R.drawable.naodinheiro2 );
			imgPassageiro.setBackgroundResource( R.drawable.pessoapagou );
			addPagou();
			
		}
		
		 btnDinheiroPassageiro.setOnClickListener(new View.OnClickListener() {

		 //private String nome;

		 @Override
		 public void onClick(View p1)
		 {
			//float valor = 0;
			//float valorTotal = 0;
			
			if( ! pagou) {
				
				
				DBConnect dbConnect = new DBConnect(context);
				dbConnect.atuPagou(idExcursao,idPassageiro,true);
				
				/*
		 		valorTotal = Float.valueOf( txtValorTotal.getText().toString() );
				valor = Float.valueOf( txtValor.getText().toString() );
			 	
				btnDinheiroPassageiro.setBackgroundResource( R.drawable.naodinheiro2 );
				valorTotal = valorTotal + valor;
				//txtValorTotal.setText( String.valueOf( valorTotal ) );
				
				pagou = true;
				
				
				*/
				
				
			} else {
				
				DBConnect dbConnect = new DBConnect(context);
				dbConnect.atuPagou(idExcursao,idPassageiro,false);
				
				/*
				valorTotal = Float.valueOf( txtValorTotal.getText().toString() );
				valor = Float.valueOf( txtValor.getText().toString() );
			 	btnDinheiroPassageiro.setBackgroundResource( R.drawable.dinheiro2 );
				
				valorTotal = valorTotal - valor;
				txtValorTotal.setText( String.valueOf( valorTotal ) );
				
				pagou = false;
				*/
				
			}
			
			 txtValorTotal.setText("0");
			 Valor.valorTotal = 0;
			 controle.carregarPassageirosExcursao(context,idExcursao);
			 lytOp.setVisibility( LinearLayout.INVISIBLE);
			
		 }

		 });

		 

		
		 btnExcluirPassageiro.setOnClickListener(new View.OnClickListener() {

		 //private String nome;

		 @Override
		 public void onClick(View p1)
		 {
			 
			 
			 
			 
			 DialogInterface.OnClickListener listenerPositivo = new DialogInterface.OnClickListener() {

				 @Override
				 public void onClick(DialogInterface p1, int p2)
				 {
					 // TODO: Implement this method
					 DBConnect dbConnect = new DBConnect(context);
					 dbConnect.delPassageirosExcursao(idPassageiro,idExcursao);
					 lytOp.setVisibility( LinearLayout.INVISIBLE);
					 controle.carregarPassageirosExcursao(context,idExcursao);
					 
				 }
				 
				 
			 };
			 
			 DialogInterface.OnClickListener listenerNegativo = new DialogInterface.OnClickListener() {

				 @Override
				 public void onClick(DialogInterface p1, int p2)
				 {
					 // TODO: Implement this method
					 
				 }


			 };
			 
			 Alerta alerta = new Alerta(context,"Remover Passageiro","Tem certeza que deseja excluir o passageiro " + nome + " da excursão ?",listenerPositivo,listenerNegativo);
			 alerta.show();
			 /*
		 int qtdEspera = dbConnect.qtdPassageirosExcursaoEspera(idExcursao);
		 if(qtdEspera > 0 && ! espera ) {
			 ArrayList<HashMap<String,String>> arrayPassageirosExcursaoEspera = dbConnect.getPassageirosExcursaoEspera(idExcursao);
			 
			 if( arrayPassageirosExcursaoEspera.size() > 0 ) {
				 HashMap<String,String> hash = arrayPassageirosExcursaoEspera.get(0);
				 dbConnect.atuRetiraEsperaPassageiroExcursao( Integer.valueOf( (String) hash.get("idPassageiroExcursao") ) );
			 }
			 
		 }
		 
		 */
			 
			 
		 }

		 });

		btnTrocaPassageiro.setOnClickListener(new View.OnClickListener() {

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					/*
					DBConnect dbConnect = new DBConnect(context);
					int qtdEspera = dbConnect.qtdPassageirosExcursaoEspera(idExcursao);
					if(qtdEspera > 0 ) {
						ArrayList<HashMap<String,String>> arrayPassageirosExcursaoEspera = dbConnect.getPassageirosExcursaoEspera(idExcursao);

						if( arrayPassageirosExcursaoEspera.size() > 0 ) {
							HashMap<String,String> hash = arrayPassageirosExcursaoEspera.get(0);
							dbConnect.atuRetiraEsperaPassageiroExcursao( Integer.valueOf( (String) hash.get("idPassageiroExcursao") ) );
						}

					}
					*/
					lytOp.setVisibility( LinearLayout.INVISIBLE);
					
					TrocaPassageiro trocaPassageiro = new TrocaPassageiro(context,idExcursao,idPassageiro,controle);
					
					controle.getLytTrocaPassageiro().removeAllViews();
					controle.getLytTrocaPassageiro().addView( trocaPassageiro.getLayout() );

					controle.getLytTrocaPassageiro().setVisibility( LinearLayout.VISIBLE );
					//control
					
					
					//controle.carregarPassageirosExcursao(context,idExcursao);

				}

			});

		

		lyt.setOnClickListener(new View.OnClickListener() {

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method

					Toast.makeText( context, nome,30).show();

					if( controle.getLytAddPassageirosExcursao().getVisibility() == LinearLayout.VISIBLE) {
						controle.getLytAddPassageirosExcursao().setVisibility(LinearLayout.INVISIBLE);
						return;
					}
					
					if( lytOp.getVisibility() == LinearLayout.VISIBLE ) {
						lytOp.setVisibility( LinearLayout.INVISIBLE);
					} else {
						lytOp.setVisibility( LinearLayout.VISIBLE);
					}
					//return;

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

	/*
	public void carregarPassageirosExcursao(ControleGerenciarExcursao controle,int idExcursao) {

		//ControleGerenciarExcursao controle = new ControleGerenciarExcursao(txtNomeExcursao,txtVagasExcursao,txtValorExcursao,txtValorTotalExcursao,lytPassageirosExcursao,lytAddPassageirosExcursao);
		DBConnect dbConnect = new DBConnect(context);
		ArrayList<HashMap<String,String>> arrayPassageirosExcursao = dbConnect.getPassageirosExcursao(idExcursao);
		LinearLayout lytPassageirosExcursao = controle.getLytPassageirosExcursao();
		
		lytPassageirosExcursao.removeAllViews();
		
		 for( HashMap<String,String> hash : arrayPassageirosExcursao ) {
		 ItemGerenciarPassageirosExcursao item = new ItemGerenciarPassageirosExcursao(context, (Integer) Integer.valueOf( idExcursao),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"),controle );
		 lytPassageirosExcursao.addView( item.getLayout() );
		 }
		 

		// fim
		//this.setLytPassageirosExcursao( lytPassageirosExcursao);

		//return lytPassageirosExcursao;

	}
	
	*/
	
	public void addPagou() {
		
		//float valor = 0;
		//float valorTotal = 0;
		
		//DBConnect dbConnect = new DBConnect(context);
		//dbConnect.atuPagou(idExcursao,idPassageiro,true);

		//valorTotal = Float.valueOf( txtValorTotal.getText().toString() );
		//valor = Float.valueOf( txtValor.getText().toString() );

		//btnDinheiroPassageiro.setBackgroundResource( R.drawable.naodinheiro2 );
		Valor.valorTotal = Valor.valorTotal + Valor.valor;
	
		NumberFormat formato1 = NumberFormat.getCurrencyInstance();
		txtValorTotal.setText( formato1.format( Valor.valorTotal ) );

		pagou = true;
		
	}
	
	public FrameLayout getLayout() {
		return lyt;
	}

}
