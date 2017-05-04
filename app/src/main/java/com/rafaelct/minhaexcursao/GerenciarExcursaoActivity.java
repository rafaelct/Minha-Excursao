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

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.text.*;
import android.view.View.*;

public class GerenciarExcursaoActivity extends Activity
{

	private int id;
	private TextView txtNome,txtVagas,txtValor,txtValorTotal;
	private ControleGerenciarExcursao controle;
	private Button btnCadPassageirosExcursao;
	private LinearLayout lytAddPassageiro;

	private ImageView btnAddPassageiros;

	private LinearLayout lytScrollAddPassageiro;

	private LinearLayout lytTrocaPassageiro;

	private ImageView btnEstatisticaExcursao;

	private LinearLayout lAds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gerenciarexcursao );
		
		
		lytScrollAddPassageiro = (LinearLayout) findViewById( R.id.lytScrollAddPassageiro );
		lytAddPassageiro = (LinearLayout) findViewById( R.id.lytAddPassageiro );
		lytTrocaPassageiro = (LinearLayout) findViewById( R.id.lytTrocaPassageiros );
		lAds = (LinearLayout) findViewById( R.id.lAds );
		
		txtNome = (TextView) findViewById( R.id.nomeExcursao );
		txtVagas = (TextView) findViewById( R.id.vagas );
		txtValor = (TextView) findViewById( R.id.valor );
		txtValorTotal = (TextView) findViewById( R.id.valorpago );
		
		btnCadPassageirosExcursao = (Button) findViewById( R.id.btnCadPassageirosExcursao );
		btnAddPassageiros = (ImageView) findViewById( R.id.btnAddPassageiro);
		btnEstatisticaExcursao = (ImageView) findViewById( R.id.btnEstatisticaExcursao);
		
		btnCadPassageirosExcursao.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					Intent i = new Intent(getBaseContext(),PassageirosActivity.class);
					Bundle b = new Bundle();
					b.putInt("idexcursao",id);
					i.putExtra("bundle",b);
					
					startActivity(i);
					
					lytAddPassageiro.setVisibility( LinearLayout.INVISIBLE );
					
					/*
					LayoutInflater infrater = getLayoutInflater();
					View view = infrater.inflate(R.layout.passageiros_activity,null);
					
					AlertDialog alert;
					AlertDialog.Builder builder = new AlertDialog.Builder(GerenciarExcursaoActivity.this);
					builder.setTitle("frango" );
					//builder.setMessage("teste");
					builder.setView(view);
					/*
					builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() { public void onClick(DialogInterface arg0, int arg1) { Toast.makeText(getApplicationContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show(); } }); 
					
					//define um botão como negativo. 
					builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() { public void onClick(DialogInterface arg0, int arg1) { Toast.makeText(getApplicationContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show(); } });
					*/
					//Leia mais em: Exibindo caixas de diálogos no Android com AlertDialog http://www.devmedia.com.br/exibindo-caixas-de-dialogos-no-android-com-alertdialog/26749#ixzz3qVF059ck
					//alert = builder.create();
					
					//alert.show();
					
					//Toast.makeText(getBaseContext(),"teste do teste",25).show();
					
				}
				
			});
			
		
		
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("bundle");

		id = -1;

		if(b != null ) {
			id = b.getInt("id");
		}

		
		//MyAds myAds = new MyAds(this);
		//lAds.addView( myAds.getLayout() );
		
		DBConnect dbConnect = new DBConnect(this);

		HashMap hashExcursao = dbConnect.getExcursao(id);

		txtNome.setText( (String) hashExcursao.get("nome") );
		txtVagas.setText( (String) hashExcursao.get("vagas") );
		txtValor.setText( NumberFormat.getCurrencyInstance().format( Float.valueOf( (String) hashExcursao.get("valor") ) ) );

		//Log.v("Debug valor","|"+ (String) hashExcursao.get("valor")+"|" );
		//Log.v("debug vlr.","|2000|");

		Valor.valor =  Float.valueOf( (String) hashExcursao.get("valor") );
		Valor.valorTotal = 0;

		int vagas = Integer.valueOf( (String) hashExcursao.get("vagas") );
		LinearLayout lytScroll = (LinearLayout) findViewById( R.id.lytScroll);
		//LinearLayout lytGerTit = (LinearLayout) findViewById( R.id.lytTitExcursao );

		//ItemExcursao itemExcursao = new ItemExcursao(this, id, (String) hashExcursao.get("nome"),Integer.valueOf( (String) hashExcursao.get("vagas") ), Float.valueOf( (String) hashExcursao.get("valor") ) );
		//lytGerTit.addView( itemExcursao.getLayout() );

		// colocar em um botao

		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		//View view = inflate.inflate(R.layout.listaespera,null);

		final ControleGerenciarExcursao controle = new ControleGerenciarExcursao(txtNome,txtVagas,txtValor,txtValorTotal,lytScroll,lytAddPassageiro,lytTrocaPassageiro);

		/*
		 ArrayList<HashMap<String,String>> array = dbConnect.getNaoPassageirosExcursao(id);



		 for( HashMap<String,String> hash : array ) {

		 ItemPassageiroExcursao item = new ItemPassageiroExcursao(this, (Integer) Integer.valueOf( id),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"),controle );

		 lytScrollAddPassageiro.addView( item.getLayout() );
		 }
		 // fim
		 */

		/*

		 int vagasOcupadas = 0;
		 boolean espera = false;

		 ArrayList<HashMap<String,String>> arrayPassageirosExcursao = dbConnect.getPassageirosExcursao(id);

		 for( HashMap<String,String> hash : arrayPassageirosExcursao ) {
		 vagasOcupadas++;

		 if(vagasOcupadas == vagas +1 ) {
		 //TextView txtReserva = new TextView(this);
		 //txtReserva.setText("lista de espera");
		 lytScroll.addView(view);
		 espera = true;
		 }

		 ItemGerenciarPassageirosExcursao item = new ItemGerenciarPassageirosExcursao(this, espera,(Integer) Integer.valueOf( id),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"), (Integer) Integer.valueOf( hash.get("pagou") ) ,controle );
		 lytScroll.addView( item.getLayout() );
		 }
		 // fim

		 */


		controle.carregarPassageirosExcursao(GerenciarExcursaoActivity.this,id);
		
		lytTrocaPassageiro.setOnClickListener( new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Toast.makeText(getBaseContext(),"teste",30).show();
				}
				
			
		});
		
		btnEstatisticaExcursao.setOnClickListener( new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					EstatisticaExcursao estatisticaExcursao = new EstatisticaExcursao(getBaseContext(),id,controle);
					lytTrocaPassageiro.removeAllViews();
					lytTrocaPassageiro.addView( estatisticaExcursao.getLayout() );
					lytTrocaPassageiro.setVisibility( LinearLayout.VISIBLE );
					
				}


			});
			
		btnAddPassageiros.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method

					if( lytAddPassageiro.getVisibility() == LinearLayout.VISIBLE ) {
						lytAddPassageiro.setVisibility( LinearLayout.INVISIBLE);
					} else { 

						DBConnect dbConnect = new DBConnect(getBaseContext());

						boolean permiteEspera = dbConnect.permiteEsperaExcursao(id);
						
						HashMap<String,String> hashExcursao = dbConnect.getExcursao(id);
						int lugares = Integer.valueOf( (String) hashExcursao.get("vagas") );
						int vagas = dbConnect.qtdPassageirosExcursao(id);
						
						DialogInterface.OnClickListener listenerNegativo = new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
							}
							
							
						};
						
						if( vagas >= lugares && ! permiteEspera ) {
							Alerta alerta = new Alerta(GerenciarExcursaoActivity.this,"Adicionar passageiro","Não existem lugares disponiveis na excursão",listenerNegativo);
							alerta.show();
							return;
						}
						ArrayList<HashMap<String,String>> array = dbConnect.getNaoPassageirosExcursao(id);


						lytScrollAddPassageiro.removeAllViews();

						for( HashMap<String,String> hash : array ) {

							ItemPassageiroExcursao item = new ItemPassageiroExcursao(GerenciarExcursaoActivity.this, (Integer) Integer.valueOf( id),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"),controle );

							lytScrollAddPassageiro.addView( item.getLayout() );
						}
						// fim

						lytAddPassageiro.setVisibility( LinearLayout.VISIBLE);
					}
				}
			});
			
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		lytScrollAddPassageiro.removeAllViews();
		
		super.onPause();
	}
	
	
}

