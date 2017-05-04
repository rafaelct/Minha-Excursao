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
import java.util.*;
import android.util.*;
import android.net.*;
//import com.google.android.gms.ads.*;

public class MainActivity extends Activity 
{
	
	LinearLayout lytScroll;

	private LinearLayout lAds;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		//AlertDialog.Builder alerta = AlertDialog.Builder;
		lytScroll = (LinearLayout) findViewById( R.id.lytScroll);
		lAds = (LinearLayout) findViewById( R.id.lAds );
		
		ImageView donate = (ImageView) findViewById(R.id.doar);
		
		ImageView btnAdd = (ImageView) findViewById( R.id.btnAdd );
		//ImageView btnCadPassageiro = (ImageView) findViewById( R.id.btnCadPassageiro );
		ImageView btnPassageiros = (ImageView) findViewById( R.id.btnPassageiros );
		
		DBConnect dbConnect = new DBConnect(this);
		Log.v("Qtd. passageiros",String.valueOf( dbConnect.qtdPassageirosExcursao(1) ) );
		
		donate.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=rafael%2ecosta%2eteixeira1982%40gmail%2ecom&lc=BR&item_name=App%20app2org&no_note=0&currency_code=BRL&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHostedGuest"));
					startActivity(browserIntent);
				}
			});
			
		btnAdd.setOnClickListener( new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					Intent i = new Intent(getBaseContext(),ExcursaoActivity.class);
					startActivity(i);
					
				}
				
			
		} );
		
		btnPassageiros.setOnClickListener( new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method

					Intent i = new Intent(getBaseContext(),ListaPassageirosActivity.class);
					startActivity(i);

				}


			} );
		
			/*
		btnCadPassageiro.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					Intent i = new Intent(getBaseContext(),PassageirosActivity.class);
					
					startActivity(i);
					
				}
			});
		*/
    }

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		
		DBConnect dbConnect = new DBConnect(this);

		ArrayList<HashMap<String,String>> array = dbConnect.getExcursao();

		for( HashMap hash : array ) {
			ItemExcursao item = new ItemExcursao(this,Integer.valueOf( (String) hash.get("id") ),(String) hash.get("nome"), Integer.valueOf( (String) hash.get("vagas") ),Float.valueOf( (String) hash.get("valor") ) );
			lytScroll.addView( item.getLayout() );

		}
		
		//MyAds myAds = new MyAds(this);
		//lAds.addView( myAds.getLayout() );
		
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		
		lytScroll.removeAllViews();
		
		super.onPause();
	}
	
	

}
