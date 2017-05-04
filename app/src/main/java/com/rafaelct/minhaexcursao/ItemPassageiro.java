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
import android.os.*;
import android.view.*;
import android.widget.*;

public class ItemPassageiro
{

	FrameLayout lyt;
	Context context;
	String nome,nomeTroca;

	public ItemPassageiro( final Context context,final int id,final String nome,String rg) {


		this.context = context;
		this.nome = nome;

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.itempassageiro,null);

		lyt = (FrameLayout) view.findViewById(R.id.lytFrame);
		final LinearLayout lytOp = (LinearLayout) view.findViewById( R.id.lytOpPassageiro );
		ImageButton btnExcluirPassageiro = (ImageButton) view.findViewById( R.id.btnExcluirPassageiro );
		ImageButton btnEditarPassageiro = (ImageButton) view.findViewById( R.id.btnEditarPassageiro );
		
		TextView txtNome = (TextView) view.findViewById(R.id.nomePassageiro);
		TextView txtRg = (TextView) view.findViewById(R.id.rg);
		//TextView txtValor = (TextView) view.findViewById(R.id.valor);

		txtNome.setText(nome);
		txtRg.setText( String.valueOf( rg ) );
		//txtValor.setText( String.valueOf( valor ) );

		btnEditarPassageiro.setOnClickListener(new View.OnClickListener() {

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					Intent i = new Intent(context,PassageirosActivity.class);
					
					Bundle b = new Bundle();
					b.putInt("id",id);
					
					i.putExtra("bundle",b);
					context.startActivity(i);
					
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

							dbConnect.delPassageiro(id);
							
						}
						
						


					};
	
					DialogInterface.OnClickListener listenerNegativo = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							// TODO: Implement this method

							
						}




					};
					
					DBConnect dbConnect = new DBConnect(context);
					
					Alerta alerta;
					
					int qtdExcursao = dbConnect.qtdExcursoesPassageiro(id);
					if( qtdExcursao > 0 ) {
						alerta = new Alerta(context,"Remover Passageiro","Não posso remover o passageiro " + nome + ",passageiro esta em " + qtdExcursao + " excursão",listenerNegativo);
					} else {
						alerta = new Alerta(context,"Remover Passageiro","Tem certeza que deseja remover o passageiro " + nome + " ?",listenerPositivo,listenerNegativo);
					}
					
					alerta.show();
					
					
					
				}

			});
		
		lyt.setOnClickListener(new View.OnClickListener() {

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method

					Toast.makeText( context, nome,30).show();

					if( lytOp.getVisibility() == LinearLayout.VISIBLE ) {
						lytOp.setVisibility( LinearLayout.INVISIBLE);
						return;
					} 

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
