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

public class TituloPassageiros
{
	LinearLayout lyt;
	
	public TituloPassageiros(final Context context) { 
	
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view = inflate.inflate(R.layout.titulopassageiros,null);
		
		this.lyt = (LinearLayout) view.findViewById( R.id.lytPrincipal );
		
		
	}
	
	public LinearLayout getLayout() {
		return this.lyt;
	}
}
