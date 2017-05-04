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
import android.widget.*;
import android.util.*;
import android.content.*;
import android.view.*;

public class LinearLayoutCustom extends LinearLayout 
{

	ImageButton imgButton;
	
	public LinearLayoutCustom(Context context, AttributeSet attributeSet,int idResourceLayout,int idResourceButton) {
        super(context, attributeSet);
		this.imgButton = imgButton;
		
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(idResourceLayout, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("Search Layout", "Handling Keyboard Window shown");
		

        final int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();

        if (actualHeight > proposedheight){
            // Keyboard is shown
			imgButton.setVisibility( ImageButton.INVISIBLE );
			
        } else {
            // Keyboard is hidden
			imgButton.setVisibility( ImageButton.VISIBLE );
			
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
