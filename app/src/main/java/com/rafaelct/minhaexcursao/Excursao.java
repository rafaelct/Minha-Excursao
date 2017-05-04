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

public class Excursao
{
	
	private int id;
	private String nome;
	private float valor;
	private int vagas;
	private String dataSaida;
	private String dataVolta;
	private boolean permiteEspera;

	public void setPermiteEspera(boolean permiteEspera)
	{
		this.permiteEspera = permiteEspera;
	}

	public boolean isPermiteEspera()
	{
		return permiteEspera;
	}
	
	public void setId( int id ) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setValor( float valor ) {
		this.valor = valor;
	}
	
	public float getValor() {
		return this.valor;
	}
	
	public void setVagas( int vagas ) {
		this.vagas = vagas;
	}
	
	public int getVagas() {
		return this.vagas;
	}
	
	public void setDataSaida( String dataSaida ) {
		this.dataSaida = dataSaida;
	}
	
	public String getDataSaida() {
		return this.dataSaida;
	}
	
	public void setDataVolta( String dataVolta ) {
		this.dataVolta = dataVolta;
	}
	
	public String getDataVolta() {
		return this.dataVolta;
	}
}
