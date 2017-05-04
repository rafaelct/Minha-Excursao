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

public class ListaPassageirosActivity extends Activity
{

	

	LinearLayout lytScrollPassageiros;

	private LinearLayout lAds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView( R.layout.listapassageiros );
		
		lytScrollPassageiros = (LinearLayout) findViewById( R.id.lytScrollPassageiro );
		ImageView btnCadPassageiro = (ImageView) findViewById( R.id.btnCadPassageiro);
		lAds = (LinearLayout) findViewById( R.id.lAds );
		
		btnCadPassageiro.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					Intent i = new Intent(getBaseContext(),PassageirosActivity.class);

					startActivity(i);
				}
			});
		
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		lytScrollPassageiros.removeAllViews();
		super.onPause();
		
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		
		DBConnect dbConnect = new DBConnect(this);
		ArrayList<HashMap<String,String>> arrayPassageiros = dbConnect.getPassageiros();

		lytScrollPassageiros.removeAllViews();
		
		for( HashMap<String,String> hash : arrayPassageiros ) {
			ItemPassageiro item = new ItemPassageiro(this,Integer.valueOf( hash.get("id") ),hash.get("nome"),hash.get("rg") );
			lytScrollPassageiros.addView( item.getLayout() );
		}

		//MyAds myAds = new MyAds(this);
		//lAds.addView( myAds.getLayout() );
		
		
		
		super.onResume();
	}
		
		
}
