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
import com.rafaelct.minhaexcursao.*;
import java.text.*;
import java.util.*;

public class ControleGerenciarExcursao
{
	
	TextView txtNomeExcursao;
	TextView txtVagasExcursao;
	TextView txtValorExcursao;
	TextView txtValorTotalExcursao;
	LinearLayout lytPassageirosExcursao;
	LinearLayout lytAddPassageirosExcursao;
	LinearLayout lytTrocaPassageiro;
	
	float valor = 0;
	float valorTotal = 0;
	
	public ControleGerenciarExcursao(final TextView nome,TextView vagas,TextView valor,TextView valorTotal,LinearLayout lytPassageirosExcursao,LinearLayout lytAddPassageirosExcursao,LinearLayout lytTrocaPassageiro) {
		this.txtNomeExcursao = nome;
		this.txtVagasExcursao = vagas;
		this.txtValorExcursao = valor;
		this.txtValorTotalExcursao = valorTotal;
		this.lytPassageirosExcursao = lytPassageirosExcursao;
		this.lytAddPassageirosExcursao = lytAddPassageirosExcursao;
		this.lytTrocaPassageiro = lytTrocaPassageiro;
		
	}

	public void setLytTrocaPassageiro(LinearLayout lytTrocaPassageiro)
	{
		this.lytTrocaPassageiro = lytTrocaPassageiro;
	}

	public LinearLayout getLytTrocaPassageiro()
	{
		return lytTrocaPassageiro;
	}

	
	public void setValorTotal(float valorTotal)
	{
		this.valorTotal = valorTotal;
	}

	public float getValorTotal()
	{
		return valorTotal;
	}

	public void setValor(float valor)
	{
		this.valor = valor;
	}

	public float getValor()
	{
		return valor;
	}

	public void carregarPassageirosExcursao(Context context,int idExcursao) {

		Valor.valorTotal = 0;
		
		NumberFormat formato1 = NumberFormat.getCurrencyInstance();
		this.txtValorTotalExcursao.setText( formato1.format( Valor.valorTotal ) );
		
		ControleGerenciarExcursao controle = new ControleGerenciarExcursao(txtNomeExcursao,txtVagasExcursao,txtValorExcursao,txtValorTotalExcursao,lytPassageirosExcursao,lytAddPassageirosExcursao,lytTrocaPassageiro);
		
		DBConnect dbConnect = new DBConnect(context);
		ArrayList<HashMap<String,String>> arrayPassageirosExcursao = dbConnect.getPassageirosExcursao(idExcursao);
		ArrayList<HashMap<String,String>> arrayPassageirosExcursaoEspera = dbConnect.getPassageirosExcursaoEspera((idExcursao) );
		
		//LinearLayout lytPassageirosExcursao = controle.getLytPassageirosExcursao();

		int vagasTotal = Integer.valueOf( txtVagasExcursao.getText().toString() );
		int vagas = 0;
		
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.listaespera,null);
		
		boolean espera = false;
		
		TituloPassageiros titPassageiros = new TituloPassageiros(context);
		
		lytPassageirosExcursao.removeAllViews();

		lytPassageirosExcursao.addView( titPassageiros.getLayout() );
		
		for( HashMap<String,String> hash : arrayPassageirosExcursao ) {
			vagas++;
			
			/*
			if( vagas == vagasTotal + 1 ) {
				lytPassageirosExcursao.addView( view );
				espera = true;
			}
			*/
			ItemGerenciarPassageirosExcursao item = new ItemGerenciarPassageirosExcursao(context,espera, (Integer) Integer.valueOf( idExcursao),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"), (Integer) Integer.valueOf( hash.get("pagou") ),controle );
			lytPassageirosExcursao.addView( item.getLayout() );
		}

		if(arrayPassageirosExcursaoEspera.size() > 0 ) {
			lytPassageirosExcursao.addView( view );
			espera = true;
		}
			
		for( HashMap<String,String> hash : arrayPassageirosExcursaoEspera ) {
			vagas++;

			/*
			 if( vagas == vagasTotal + 1 ) {
			 lytPassageirosExcursao.addView( view );
			 espera = true;
			 }
			 */
			ItemGerenciarPassageirosExcursao item = new ItemGerenciarPassageirosExcursao(context,espera, (Integer) Integer.valueOf( idExcursao),(Integer) Integer.valueOf( hash.get("id" ) ), (String) hash.get("nome"), (String) hash.get("rg"), (Integer) Integer.valueOf( hash.get("pagou") ),controle );
			lytPassageirosExcursao.addView( item.getLayout() );
		}
		

		// fim
		//this.setLytPassageirosExcursao( lytPassageirosExcursao);

		//return lytPassageirosExcursao;

	}
	
	
	
	public void setLytAddPassageirosExcursao(LinearLayout lytAddPassageirosExcursao)
	{
		this.lytAddPassageirosExcursao = lytAddPassageirosExcursao;
	}

	public LinearLayout getLytAddPassageirosExcursao()
	{
		return lytAddPassageirosExcursao;
	}

	public void setLytPassageirosExcursao(LinearLayout lytPassageirosExcursao)
	{
		this.lytPassageirosExcursao = lytPassageirosExcursao;
	}

	public LinearLayout getLytPassageirosExcursao()
	{
		return lytPassageirosExcursao;
	}

	public void setTxtValorTotalExcursao(TextView txtValorTotalExcursao)
	{
		this.txtValorTotalExcursao = txtValorTotalExcursao;
	}

	public TextView getTxtValorTotalExcursao()
	{
		return txtValorTotalExcursao;
	}

	public void setTxtValorExcursao(TextView txtValorExcursao)
	{
		this.txtValorExcursao = txtValorExcursao;
	}

	public TextView getTxtValorExcursao()
	{
		return txtValorExcursao;
	}

	public void setTxtVagasExcursao(TextView txtVagasExcursao)
	{
		this.txtVagasExcursao = txtVagasExcursao;
	}

	public TextView getTxtVagasExcursao()
	{
		return txtVagasExcursao;
	}

	public void setTxtNomeExcursao(TextView txtNomeExcursao)
	{
		this.txtNomeExcursao = txtNomeExcursao;
	}

	public TextView getTxtNomeExcursao()
	{
		return txtNomeExcursao;
	}
}
