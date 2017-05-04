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
import android.app.*;
import android.os.*;
import android.view.View.*;

public final class Alerta
{
	
	AlertDialog.Builder alerta;
	
	public Alerta(Context context,String titulo,String msg,DialogInterface.OnClickListener listenerPositivo,DialogInterface.OnClickListener listenerNegativo) {
		
		alerta = new AlertDialog.Builder(context);
		//alerta = 
		alerta.setMessage(msg);
		alerta.setTitle(titulo);
		alerta.setPositiveButton("Sim",listenerPositivo);
		alerta.setNeutralButton("Não",listenerNegativo);
	}
	
	public Alerta(Context context,String titulo,String msg,DialogInterface.OnClickListener listenerNegativo) {

		alerta = new AlertDialog.Builder(context);
		alerta.setMessage(msg);
		alerta.setTitle(titulo);
		//alerta.setPositiveButton("Sim",listenerPositivo);
		alerta.setNeutralButton("Confirma",listenerNegativo);
	}
	
	public AlertDialog show() {
		return alerta.show();
	}
}
