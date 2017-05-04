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
//import com.rafaelct.listaexcursao.*;
import android.widget.*;
import android.os.*;
import java.text.*;

public class ItemExcursao
{
	
	FrameLayout lyt;
	Context context;
	String nome;
	int id;
	public ItemExcursao( final Context context,final int id,final String nome,int vagas,float valor) {
		
		
		this.context = context;
		this.nome = nome;
		this.id = id;
		
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.itemexcursao,null);
		
		lyt = (FrameLayout) view.findViewById(R.id.lytFrame);
		lyt.setEnabled(true);
		
		final LinearLayout lytOp = (LinearLayout) view.findViewById( R.id.lytOp );
		ImageButton btnEditarExcursao = (ImageButton) view.findViewById( R.id.btnEditarExcursao );
		ImageButton btnExcluirExcursao = (ImageButton) view.findViewById( R.id.btnExcluirExcursao );
		
		TextView txtNome = (TextView) view.findViewById(R.id.nomeExcursao);
		TextView txtVagas = (TextView) view.findViewById(R.id.vagas);
		TextView txtValor = (TextView) view.findViewById(R.id.valor);
		
		txtNome.setText(nome);
		txtVagas.setText( String.valueOf( vagas ) );
		//txtValor.setText( String.valueOf( valor ) );
		
		txtValor.setText( NumberFormat.getCurrencyInstance().format(valor) );
		
		lyt.setOnClickListener(new View.OnClickListener() {

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					//Toast.makeText( context, nome,30).show();
					
					if( lytOp.getVisibility() == LinearLayout.VISIBLE ) {
						lytOp.setVisibility( LinearLayout.INVISIBLE);
						return;
					} 
					
					Intent i = new Intent(context,GerenciarExcursaoActivity.class);
					Bundle b = new Bundle();
					
					b.putInt("id",id);
					i.putExtra("bundle",b);
					context.startActivity(i);
					
				}
			});
			
		btnExcluirExcursao.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					DialogInterface.OnClickListener listenerPositivo = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
							
							DBConnect dbConnect = new DBConnect(context);
							dbConnect.delExcursao(id);
							dbConnect.delPassageirosExcursao(id);
							lyt.setVisibility( FrameLayout.GONE );
						}

						
					};
					
					DialogInterface.OnClickListener listenerNegativo = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method
							
						}

						
					};
					
					Alerta alerta = new Alerta(context,"Remover Excursão","Tem certeza que deseja remover a excursao ?",listenerPositivo,listenerNegativo);
					alerta.show();
					
					
				}
			});
			
		btnEditarExcursao.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method

					Intent i = new Intent(context,ExcursaoActivity.class);
					Bundle b = new Bundle();
					b.putInt("id",id);
					i.putExtra("bundle",b);
					context.startActivity(i);
					
				}
			});
		
		lyt.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View p1)
				{
					// TODO: Implement this method
					//Toast.makeText(context,"longo click !!!",10).show();
					
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
