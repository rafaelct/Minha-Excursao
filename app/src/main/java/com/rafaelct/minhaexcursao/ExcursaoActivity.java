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

import android.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class ExcursaoActivity extends Activity
{
	
	DBConnect dbConnect;
	EditText edNome,edVagas,edValor;
	ImageView btnSave;
	int id,vagasAtual;
	
	private CheckBox chkListaEspera;

	private LinearLayout lAds;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		
		setContentView(R.layout.excursao_activity);
		//ScrollView scroll = (ScrollView) findViewById( R.id.scroll );
		
		Intent i = getIntent();
		
		id = -1;
	
		Bundle b = i.getBundleExtra("bundle");
		if( b != null ) {
			id = b.getInt("id");
		}
		
		
		
		
		//Toast.makeText(this,String.valueOf(id),30).show();
		
		LayoutInflater li = getLayoutInflater();

		final View view = li.inflate( R.layout.data_dialog,null);
		//scroll.addView( view );
		
		edNome = (EditText) findViewById( R.id.edNome );
		edValor = (EditText) findViewById( R.id.edValor );
		edVagas = (EditText) findViewById( R.id.edVagas );
		btnSave = (ImageView) findViewById( R.id.btnSaveExcursao);
		chkListaEspera = (CheckBox) findViewById( R.id.chkListaEspera );
		lAds = (LinearLayout) findViewById( R.id.lAds );
		
		dbConnect = new DBConnect(this);
		
		if(id != -1 ) {
			
			HashMap<String,String> hash = dbConnect.getExcursao(id);
			edNome.setText( hash.get("nome") );
			edVagas.setText( hash.get("vagas") );
			edValor.setText( hash.get("valor") );
			vagasAtual = Integer.valueOf( (String) hash.get("vagas") );
			int qtdEspera = dbConnect.qtdPassageirosExcursaoEspera(id);
			
			if(qtdEspera > 0 ) {
				edVagas.setEnabled(false);
				chkListaEspera.setEnabled(false);
			} else {
				edVagas.setEnabled(true);
				chkListaEspera.setEnabled(true);
			}
			
			boolean permiteEspera = dbConnect.permiteEsperaExcursao(id);
			
			chkListaEspera.setChecked( permiteEspera );
			
		
			
		}
		//final ImageButton btnSalvar = (ImageButton) findViewById( R.id.btnSave );
		
		//final TextView lblDtSaida = (TextView) findViewById( R.id.dtSaida );
		//final TextView lblDtVolta= (TextView) findViewById( R.id.dtVolta );
		//lblDtSaida.setText("");
		//lblDtVolta.setText("");
		
		//ImageButton btDataSaida = (ImageButton) findViewById( R.id.btDataSaida );
		//ImageButton btDataVolta = (ImageButton) findViewById( R.id.btDataVolta );
		
		//MyAds myAds = new MyAds(this);
		//lAds.addView( myAds.getLayout() );
		
		btnSave.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					String nome = edNome.getText().toString().trim();

					if ( edValor.getText().toString().equals("") ) {
						Toast.makeText( getBaseContext(),"Insira o valor da passagem",5).show();
						return;
					}

					if ( edVagas.getText().toString().equals("") ) {
						Toast.makeText( getBaseContext(),"Insira a quantidade total de vagas no onibus",5).show();
						return;
					}

					/*
					 if( lblDtSaida.getText().equals("") ) {
					 Toast.makeText( getBaseContext() , "Especifique uma data e hora de saida",5).show();
					 return;
					 }

					 if( lblDtVolta.getText().equals("") ) {
					 Toast.makeText( getBaseContext() , "Especifique uma data e hora de retorno",5).show();
					 return;
					 }

					 */

					float valor = Float.valueOf( edValor.getText().toString() );
					int vagas = Integer.valueOf( edVagas.getText().toString() );

					if ( nome.equals("") ) {
						Toast.makeText(getBaseContext(), "Insira um titulo para a excursao.",5).show();
						return;
					}

					
					
					Excursao excursao = new Excursao();
					excursao.setId(id);
					excursao.setNome( nome );
					excursao.setVagas( vagas );
					excursao.setValor( valor );
					excursao.setPermiteEspera( chkListaEspera.isChecked() );
					
					//excursao.setDataSaida( lblDtSaida.getText().toString() );
					//excursao.setDataVolta( lblDtVolta.getText().toString() );

					
					//ArrayList<HashMap<String,String>> arrayPassageirosEspera = dbConnect.getPassageirosExcursaoEspera(id); 
					
					//Toast.makeText(getBaseContext(),String.valueOf(id),25).show();
					
					if(id == -1 ) {
						dbConnect.addExcursao(excursao);
						finish();
					} else {
						
						DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
							}
							
							
							
						};
						
						int qtdPassageiros = dbConnect.qtdPassageirosExcursao(id);
						int qtdEspera = dbConnect.qtdPassageirosExcursaoEspera(id);
						
						if( qtdPassageiros > vagas && qtdEspera == 0 ) {
							Alerta alerta = new Alerta(ExcursaoActivity.this,"Erro","Quantidades de lugares informado menor que a quantidade de passageiros da excursão.\n qtd. de passageiros: "+ qtdPassageiros,listener);
							alerta.show();
							return;
						}
						
						dbConnect.atuExcursao(excursao);
						finish();
						
					
					}
					
				}
			});
		
			
			
		//dbConnect = new DBConnect(this);
		
		/*
		
		
		
		/*
		btDataSaida.setOnClickListener( new View.OnClickListener()  {
			
			public void onClick(View v) {
				DialogAlert dialogAlert = new DialogAlert(ExcursaoActivity.this,R.layout.data_dialog,"Saida","Data da Saida","Hora da Saida",lblDtSaida);
				dialogAlert.show();
				
			}
			
		} );
		
		btDataVolta.setOnClickListener( new View.OnClickListener()  {

				public void onClick(View v) {
					DialogAlert dialogAlert = new DialogAlert(ExcursaoActivity.this,R.layout.data_dialog,"Volta","Data da Volta","Hora da Volta",lblDtVolta);
					dialogAlert.show();

				}

			} );
		
		*/
				//lblDtSaida.setText("rafaelll");
		
			//lblDtSaida.setText( dialogAlert.getData() );
		
        
	}
	
	
}
