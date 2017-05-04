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

public class ItemPassageiroExcursao
{

	FrameLayout lyt;
	Context context;
	String nome;
	//ControleGerenciarExcursao controleGerenciarExcursao;
	
	public ItemPassageiroExcursao( final Context context,final int idExcursao,final int idPassageiro,final String nome,String rg,final ControleGerenciarExcursao controleGerenciarExcursao) {


		this.context = context;
		this.nome = nome;
		//this.controleGerenciarExcursao = controleGerenciarExcursao;
		
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

		/*
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

			*/
			
			/*
		btnExcluirPassageiro.setOnClickListener(new View.OnClickListener() {

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					DBConnect dbConnect = new DBConnect(context);
					dbConnect.delPassageiro(id);
				}

			});

			*/
			
		lyt.setOnClickListener(new View.OnClickListener() {

				//private String nome;

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method

					//LinearLayout lytGerPassageirosExcursao = controleGerenciarExcursao.getLytPassageirosExcursao();
					
					Toast.makeText( context, nome,30).show();

					DBConnect dbConnect = new DBConnect(context);
					HashMap<String,String> hash = dbConnect.getExcursao(idExcursao);
					
					int lugares = Integer.valueOf( (String) hash.get("vagas") );
					int retCod = -1;
					
					int vagas = dbConnect.qtdPassageirosExcursao(idExcursao);
					boolean permiteEspera = dbConnect.permiteEsperaExcursao(idExcursao);
					
					if( vagas < lugares ) {
						retCod = dbConnect.addPassageirosExcursao(idExcursao,idPassageiro);
					} else {
						retCod = dbConnect.addPassageirosExcursaoEspera(idExcursao,idPassageiro);
					}
					
					if( retCod == 1 ) {
						Toast.makeText(context,"Erro ao adicionar passageiro.",25).show();
						return;
					}
					
					if( retCod == 2 ) {
						Toast.makeText(context,"Passageiro já estava cadastrado nessa Excursão.",25).show();
						return;
					}
					
					lyt.setVisibility( LinearLayout.GONE);
					controleGerenciarExcursao.getLytAddPassageirosExcursao().setVisibility( LinearLayout.INVISIBLE );
					
					controleGerenciarExcursao.carregarPassageirosExcursao(context,idExcursao);
					
					/*
					lytGerPassageirosExcursao.removeAllViews();
					
					ArrayList<HashMap<String,String>> arrayPassageirosExcursao = dbConnect.getPassageirosExcursao(idExcursao);

					for( HashMap<String,String> hash : arrayPassageirosExcursao ) {
						ItemGerenciarPassageirosExcursao item = new ItemGerenciarPassageirosExcursao(context, (Integer) Integer.valueOf( idExcursao),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"),(Integer) Integer.valueOf( hash.get("pagou") ),controleGerenciarExcursao);
						lytGerPassageirosExcursao.addView( item.getLayout() );
					}
					// fim
					*/
					//controleGerenciarExcursao.setLytPassageirosExcursao(lytGerPassageirosExcursao);
					
					/*
					if( lyt.getVisibility() == LinearLayout.VISIBLE ) {
						lyt.setVisibility( LinearLayout.INVISIBLE);
						return;
					} 
					*/
					
				}
			});

			/*
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
			
			*/
	}

	public FrameLayout getLayout() {
		return lyt;
	}

}
