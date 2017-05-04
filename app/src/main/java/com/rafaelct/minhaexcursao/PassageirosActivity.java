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
import android.view.*;
import android.widget.*;
import com.rafaelct.minhaexcursao.*;
import java.util.*;
import android.content.res.*;
import android.util.*;
import android.view.View.*;

public class PassageirosActivity extends Activity 
{
	
	EditText txtNome,txtDtNasc,txtRg,txtRgResp,txtNomeResp,txtDddRes,txtTelRes,txtDddCel,txtTelCel;
	ImageButton btnSalvarPassageiro;
	int id,idExcursao;

	private LinearLayout lytPrincipal;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		
		//startActivity( new Intent(this,PassageirosActivity.class) );
		setContentView(R.layout.passageiros_activity);
		
		lytPrincipal = (LinearLayout) findViewById( R.id.lytPrincipal );
		
		txtNome = (EditText) findViewById( R.id.txtNome );
		txtRg = (EditText) findViewById( R.id.txtRg );
		txtDtNasc = (EditText) findViewById( R.id.txtDtNasc );
		txtRgResp = (EditText) findViewById( R.id.txtRgResp );
		txtNomeResp = (EditText) findViewById( R.id.txtNomeResp );
		//txtDddRes = (EditText) findViewById( R.id.txtDddRes );
		txtTelRes = (EditText) findViewById( R.id.txtTelRes );
		//txtDddCel = (EditText) findViewById( R.id.txtDddCel );
		txtTelCel = (EditText) findViewById( R.id.txtTelCel);
		//chkEspecial = (CheckBox) findViewById( R.id.chkEspecial );
		
		btnSalvarPassageiro = (ImageButton) findViewById( R.id.btnSalvarPassageiro );
		
		
		
		Intent i = getIntent();
		
		Bundle b = i.getBundleExtra("bundle");
		
		id = -1;
		idExcursao = -1;
		
		if(b != null ) {
			id = b.getInt("id",-1);
			idExcursao = b.getInt("idexcursao",-1);
		}
		
		if( id > -1 ) {
			//id = b.getInt("id");
			DBConnect dbConnect = new DBConnect(this);
			HashMap<String,String> hash = dbConnect.getPassageiro(id);
			txtNome.setText( hash.get("nome") );
			txtRg.setText( hash.get("rg") );
			txtDtNasc.setText( hash.get("datanasc") );
			txtTelRes.setText( hash.get("telres") );
			txtTelCel.setText( hash.get("telcel") );
			txtRgResp.setText( hash.get("rg_responsavel") );
			txtNomeResp.setText( hash.get("nome_responsavel") );
			
		}
		btnSalvarPassageiro.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					if ( txtNome.getText().toString().equals("") ) {
						Toast.makeText(getBaseContext(),"Preencha o nome",30).show();
						return;
					}
					
					Passageiro passageiro = new Passageiro();
					passageiro.setId(id);
					passageiro.setNome( txtNome.getText().toString() );
					passageiro.setRg( txtRg.getText().toString() );
					passageiro.setDataNasc( txtDtNasc.getText().toString() );
					passageiro.setRgResponsavel( txtRgResp.getText().toString() );
					passageiro.setNomeResponsavel( txtNomeResp.getText().toString() );
					passageiro.setTelRes( txtTelRes.getText().toString() );
					passageiro.setTelCel( txtTelCel.getText().toString() );


					DBConnect dbConnect = new DBConnect( getBaseContext() );
					
					if( id == -1 ) {
						dbConnect.addPassageiro( passageiro );
						Toast.makeText( getBaseContext(),"Passageiro cadastrado com sucesso.",30).show();
					} else {
						dbConnect.atuPassageiro( passageiro );
						Toast.makeText( getBaseContext(),"Passageiro atualizado com sucesso.",30).show();
					}
					
					if( idExcursao > -1 ) {
						
						int lugares = Integer.valueOf( dbConnect.getExcursao(idExcursao).get("vagas") );
						int qtd = dbConnect.qtdPassageirosExcursao(idExcursao);
						int idPassageiro = dbConnect.idUltimoPassageirosCadastrado();
						
						if( qtd >= lugares ) {
							dbConnect.addPassageirosExcursaoEspera(idExcursao,idPassageiro);
						} else {
							dbConnect.addPassageirosExcursao(idExcursao,idPassageiro);
						}
						
					}
					finish();
				}
			});
	}

	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO: Implement this method
		
		if( newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_YES ) {

			Toast.makeText(PassageirosActivity.this,"Teclado escondido",30).show();
		} else {
			Toast.makeText(PassageirosActivity.this,"Teclado exibido",30).show();

		}
		
		super.onConfigurationChanged(newConfig);
		
		
	}
	
	
}
