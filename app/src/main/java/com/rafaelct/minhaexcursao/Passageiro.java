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
import java.util.*;

public class Passageiro
{
	private int id;
	private String nome;
	private String rg;
	private String dataNasc;
	private String rgResponsavel;
	private String nomeResponsavel;
	private String telRes;
	private String telCel;

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setTelCel(String telCel)
	{
		this.telCel = telCel;
	}

	public String getTelCel()
	{
		return telCel;
	}

	public void setTelRes(String telRes)
	{
		this.telRes = telRes;
	}

	public String getTelRes()
	{
		return telRes;
	}

	public void setNomeResponsavel(String nomeResponsavel)
	{
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getNomeResponsavel()
	{
		return nomeResponsavel;
	}
	
	public void setNome( String nome ) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setRg( String rg) {
		this.rg = rg;
	}
	
	public String getRg() {
		return this.rg;
	}
	
	public void setDataNasc( String dataNasc ) {
		this.dataNasc = dataNasc;
	}
	
	public String getDataNasc() {
		
		return this.dataNasc;
	}
	
	public void setRgResponsavel( String rgResponsavel ) {
		this.rgResponsavel = rgResponsavel;
	}
	
	public String getRgResponsavel() {
		return this.rgResponsavel;
	}
}
