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
import android.database.*;
import android.database.sqlite.*;
import android.util.*;
import java.util.*;

public class DBConnect extends SQLiteOpenHelper 
{

	SQLiteDatabase db;
	private static final String DB_NAME = "organizaexcursoes.db";
	private static final int DB_VERSION = 1;

	public DBConnect(Context context) {
		super(context, DB_NAME, null, DB_VERSION);  

	}

	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		// TODO: Implement this method
		
		db = p1;
		
		db.execSQL("create table passageiros (" + 
		"_id integer primary key,"+
		"rg text, " +
		"nome TEXT not null, " +
		"datanasc text," +
		"telres text,"+
		"telcel text,"+
		"rg_responsavel text,"+
		"nome_responsavel text)");
		
		/*
		db.execSQL("create table contatospassageiros (" + 
				   "_id integer primary key,"+
				   "idPassageiros integer, " +
				   "contato TEXT not null, " +
				   "tpcontato integer not null," +
				   "nomerecado text)");
		*/
		db.execSQL("create table excursao (" + 
				   "_id integer primary key, " +
				   "nome TEXT not null, " +
				   "valor integer not null," +
				   "vagas integer,"+
				   "permiteespera text,"+
				   "datasaida text,"+
				   "datavolta text)");

		
		db.execSQL("create table passageirosexcursao (" + 
			       "_id integer primary key," +
				   "idPassageiro integer not null, " +
				   "idExcursao integer not null,"+
				   "flagpagou integer not null,"+
				   "espera Text )");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		// TODO: Implement this method
	}
	
	

	public int addPassageirosExcursao( int idExcursao , int idPassageiro) {

		
		if( this.buscaPassageiro( idExcursao,idPassageiro) ) {
			return 2;
		}
		
		
		
		try {
			getWritableDatabase().execSQL("insert into Passageirosexcursao (idExcursao,idPassageiro,flagpagou) values ("+ idExcursao + ","+ idPassageiro+ ",0)");
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}

	public int addPassageirosExcursaoEspera( int idExcursao , int idPassageiro) {


		if( this.buscaPassageiro( idExcursao,idPassageiro) ) {
			return 2;
		}



		try {
			getWritableDatabase().execSQL("insert into Passageirosexcursao (idExcursao,idPassageiro,flagpagou,espera) values ("+ idExcursao + ","+ idPassageiro+ ",0,'E')");
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int atuExcursao( Excursao excursao ) {

		//if( ! this.buscaExcursao( excursao.getId() ) ) {
			//return 1;
		//}

		String permiteEspera;

		if( excursao.isPermiteEspera() ) {
			permiteEspera = "'E'";
		} else {
			permiteEspera = "null";
		}
		
		
		try {
			getWritableDatabase().execSQL("update excursao set nome = '"+ excursao.getNome() + "',valor = '"+ excursao.getValor() + "', vagas= '"+ excursao.getVagas() +"' , permiteespera = " + permiteEspera + " where _id = " + excursao.getId());
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int atuRetiraEsperaPassageiroExcursao( int idPassageiroExcursao ) {

		//if( ! this.buscaExcursao( excursao.getId() ) ) {
		//return 1;
		//}

		try {
			getWritableDatabase().execSQL("update passageirosexcursao set espera = null where _id = " + idPassageiroExcursao);
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	
	public int atuPagou( int idExcursao,int idPassageiro,boolean pagou ) {

		//if( ! this.buscaExcursao( excursao.getId() ) ) {
		//return 1;
		//}

		int pg = 0;
		
		if(pagou) {
			pg = 1;
		} else {
			pg = 0;
		}
		try {
			getWritableDatabase().execSQL("update passageirosexcursao set flagpagou = "+ pg+" where idExcursao = " + idExcursao + " and idPassageiro = " + idPassageiro );
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int atuDadosPassageirosExcursao( int id,int idExcursao,int idPassageiro,boolean pagou,boolean espera ) {

		//if( ! this.buscaExcursao( excursao.getId() ) ) {
		//return 1;
		//}

		int pg = 0;

		if(pagou) {
			pg = 1;
		} else {
			pg = 0;
		}
		
		String flagEspera;
		
		if(espera) {
			flagEspera = "'E'";
		} else {
			flagEspera = "null";
		}
		
		try {
			getWritableDatabase().execSQL("update passageirosexcursao set idExcursao = "+idExcursao+",idPassageiro = "+idPassageiro + ",flagpagou = "+ pg+",espera = " + flagEspera + " where _id = " + id );
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int atuEsperaPassageirosExcursao( int id,boolean espera ) {

		//if( ! this.buscaExcursao( excursao.getId() ) ) {
		//return 1;
		//}

		
		String flagEspera;

		if(espera) {
			flagEspera = "'E'";
		} else {
			flagEspera = "null";
		}

		try {
			getWritableDatabase().execSQL("update passageirosexcursao set espera = " + flagEspera + " where _id = " + id );
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int addExcursao( Excursao excursao ) {
		
		if( this.buscaExcursao( excursao.getId() ) ) {
			return 1;
		}

		String permiteEspera;
		
		if( excursao.isPermiteEspera() ) {
			permiteEspera = "'E'";
		} else {
			permiteEspera = "null";
		}
		
		try {
			getWritableDatabase().execSQL("insert into excursao (nome,valor,vagas,permiteespera) values ('"+ excursao.getNome() + "','"+ excursao.getValor() + "','"+ excursao.getVagas() +"',"+ permiteEspera + ")");
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int atuPassageiro( Passageiro passageiro ) {

		if( this.buscaPassageiro( passageiro.getId() ) ) {
			return 1;
		}

		try {
			getWritableDatabase().execSQL("update Passageiros set "+
			"rg = '"+ passageiro.getRg() + "',"+
			"nome = '"+ passageiro.getNome() + "',"+
			"datanasc = '"+ passageiro.getDataNasc() + "',"+
			"rg_responsavel = '"+ passageiro.getRgResponsavel()+"',"+
			"nome_responsavel = '"+ passageiro.getNomeResponsavel()+"',"+
			"telres = '"+ passageiro.getTelRes()+"',"+
			"telcel = '"+ passageiro.getTelCel()+"' where _id = " + passageiro.getId() );
			
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}

	public int addPassageiro( Passageiro passageiro ) {

		if( this.buscaPassageiro( passageiro.getId() ) ) {
			return 1;
		}

		try {
			getWritableDatabase().execSQL("insert into Passageiros (rg,"+
										  "nome,"+
										  "datanasc,"+
										  "rg_responsavel,"+
										  "nome_responsavel,"+
										  "telres,"+
										  "telcel) "+
										  "values ('"+ passageiro.getRg() + "',"+
										  "'"+ passageiro.getNome() + "',"+
										  "'"+ passageiro.getDataNasc() + "',"+
										  "'"+ passageiro.getRgResponsavel()+"',"+
										  "'"+ passageiro.getNomeResponsavel()+"',"+
										  "'"+ passageiro.getTelRes()+"',"+
										  "'"+ passageiro.getTelCel()+"')");
			return 0;
		} catch( SQLiteConstraintException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public void delPassageirosExcursao(int idPassageiro, int idExcursao ) {


		getWritableDatabase().execSQL("delete from passageirosexcursao where idPassageiro = " + idPassageiro + " and idExcursao = " + idExcursao + " ");
	//	getWritableDatabase().execSQL("delete from excursao where id = '" + idExcursao + "'");

	}
	
	public void delPassageirosExcursao(int idExcursao ) {


		getWritableDatabase().execSQL("delete from passageirosexcursao where idExcursao = " + idExcursao );
		//	getWritableDatabase().execSQL("delete from excursao where id = '" + idExcursao + "'");

	}
	
	public void delExcursao(int idExcursao) {


		getWritableDatabase().execSQL("delete from passageirosexcursao where idExcursao = " + idExcursao + " ");
		getWritableDatabase().execSQL("delete from excursao where _id = '" + idExcursao + "'");

	}
	
	public void delPassageiro(int idPassageiro) {

		
		getWritableDatabase().execSQL("delete from passageirosexcursao where idPassageiro = " + idPassageiro + " ");
		getWritableDatabase().execSQL("delete from passageiros where _id = " + idPassageiro + " ");
		
	}
	
	public int qtdPassageirosExcursao( int idExcursao ) {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select count(_id) from passageirosexcursao where idExcursao = "+ idExcursao + "",null);


		//ArrayList array = new ArrayList();

		int i = -1;
		
		while( c.moveToNext() ) {

		 i = (Integer) c.getInt(0);
			
		}

		return i;

	}
	
	public boolean permiteEsperaExcursao( int idExcursao ) {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select permiteespera from excursao where _id = "+ idExcursao + "",null);


		//ArrayList array = new ArrayList();

		String permiteEspera = null;

		while( c.moveToNext() ) {

			permiteEspera =  c.getString(0);

		}
		
		if( permiteEspera == null ) {
			return false;
		} else {
			return true;
		}
		

	}

	
	
	public int idUltimoPassageirosCadastrado() {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select max(_id) from passageiros ",null);


		//ArrayList array = new ArrayList();

		int i = -1;

		while( c.moveToNext() ) {

			i = (Integer) c.getInt(0);

		}

		return i;

	}
	
	public int qtdPassageirosExcursaoEspera( int idExcursao ) {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select count(_id) from passageirosexcursao where idExcursao = "+ idExcursao + " and espera = 'E' ",null);


		//ArrayList array = new ArrayList();

		int i = -1;

		while( c.moveToNext() ) {

			i = (Integer) c.getInt(0);

		}

		return i;

	}

	public int qtdExcursoesPassageiro( int idPassageiro) {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select count(_id) from passageirosexcursao where idPassageiro = "+ idPassageiro,null);


		//ArrayList array = new ArrayList();

		int i = -1;

		while( c.moveToNext() ) {

			i = (Integer) c.getInt(0);

		}

		return i;

	}

	
	
	public boolean buscaExcursao( int idExcursao ) {

		Cursor c = null;

		
		c = getReadableDatabase().rawQuery("select nome from excursao where _id = '"+ idExcursao + "'",null);

			
		//ArrayList array = new ArrayList();

		while( c.moveToNext() ) {

			String str = (String) c.getString(0);
			if( str != null ) {
				return false;
			}
			return true;
		}

		return false;

	}

	public ArrayList<HashMap<String,String>> getExcursao() {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select _id,nome,vagas,valor from excursao ",null);


		ArrayList<HashMap<String,String>> arrayExcursao = new ArrayList<HashMap<String,String>>();

		while( c.moveToNext() ) {

			//Log.v("debug id",c.getString(0));
			//String str = (String) c.getString(0);
			HashMap<String,String> item = new HashMap<String,String>();
			item.put("id", String.valueOf(c.getInt(0)));
			item.put("nome",c.getString(1));
			item.put("vagas",c.getString(2));
			item.put("valor",c.getString(3));
			Log.v("debug db",item.get("id"));
			arrayExcursao.add(item);
			/*
			if( str != null ) {
				//return ;
			}
			*/
			//return arrayExcursao;
		}

		return arrayExcursao;

	}
	
	public HashMap<String,String> getExcursao(int id) {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select _id,nome,vagas,valor,permiteespera from excursao where _id = " + id,null);


		//ArrayList<HashMap<String,String>> arrayExcursao = new ArrayList<HashMap<String,String>>();

		HashMap<String,String> item = new HashMap<String,String>();
		
		while( c.moveToNext() ) {

			Log.v("passei db","passei...");
			//String str = (String) c.getString(0);
			//c.moveToFirst();
			item.put("id",String.valueOf(c.getInt(0)));
			item.put("nome",c.getString(1));
			item.put("vagas",c.getString(2));
			item.put("valor",c.getString(3));
			item.put("permiteespera",c.getString(4));
			
			//arrayExcursao.add(item);
			/*
			 if( str != null ) {
			 //return ;
			 }
			 */
			//return arrayExcursao;
		} 
		/*
			item.put("id",c.getString(0));
			item.put("nome",c.getString(1));
			item.put("vagas",c.getString(2));
			item.put("valor",c.getString(3));
			*/
		

		return item;

	}
	
	
	public HashMap<String,String> getPassageiro(int id) {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select _id,rg,nome,telres,telcel,datanasc,rg_responsavel,nome_responsavel from passageiros where _id = " + id,null);


		//ArrayList<HashMap<String,String>> arrayExcursao = new ArrayList<HashMap<String,String>>();

		HashMap<String,String> item = new HashMap<String,String>();

		while( c.moveToNext() ) {

			Log.v("passei db","passei...");
			//String str = (String) c.getString(0);
			//c.moveToFirst();
			item.put("id",String.valueOf( c.getInt(0) ) );
			item.put("rg",c.getString(1));
			item.put("nome",c.getString(2));
			item.put("telres",c.getString(3));
			item.put("telcel",c.getString(4));
			item.put("datanasc",c.getString(5) );
			item.put("rg_responsavel",c.getString(6) );
			item.put("nome_responsavel", c.getString(7) );
			
			//arrayExcursao.add(item);
			/*
			 if( str != null ) {
			 //return ;
			 }
			 */
			//return arrayExcursao;
		} 
		/*
		 item.put("id",c.getString(0));
		 item.put("nome",c.getString(1));
		 item.put("vagas",c.getString(2));
		 item.put("valor",c.getString(3));
		 */


		return item;

	}
	
	public HashMap<String,String> getDadosPassageirosExcursao(int idExcursao,int idPassageiro) {

		Cursor c = null;


		c = getReadableDatabase().rawQuery("select _id,flagpagou,espera from passageirosexcursao where idExcursao = " + idExcursao + " and idPassageiro = "+ idPassageiro + "",null);


		//ArrayList<HashMap<String,String>> arrayExcursao = new ArrayList<HashMap<String,String>>();

		HashMap<String,String> item = new HashMap<String,String>();

		while( c.moveToNext() ) {

			Log.v("passei db","passei...");
			//String str = (String) c.getString(0);
			//c.moveToFirst();
			item.put("id",String.valueOf( c.getInt(0) ) );
			item.put("pagou",String.valueOf( c.getInt(1) ) );
			item.put("espera",c.getString(2) );
			
		} 

		return item;

	}
	
	public boolean buscaPassageiro(int idExcursao, int idPassageiro) {

		Cursor c = null;
		
		c = getReadableDatabase().rawQuery("select count(_id) from passageirosexcursao where idPassageiro = "+ idPassageiro + " and idExcursao = "+ idExcursao,null);

		ArrayList array = new ArrayList();

		while( c.moveToNext() ) {

			int qtd = c.getInt(0);
			Log.v("quantidade", String.valueOf(qtd) );
			
			if( qtd > 0 ) {
				return true;
			}
			return false;
		}

		return false;

	}

	
	public ArrayList<HashMap<String,String>> getPassageirosExcursao(int idExcursao) {

		Cursor c = null;

		c = getReadableDatabase().rawQuery("select p._id,p.rg,p.nome,p.telres,p.telcel,p.datanasc,p.rg_responsavel,p.nome_responsavel,pe.flagpagou,pe._id from passageiros p, passageirosexcursao pe where pe.idExcursao = "+ idExcursao+" and pe.idPassageiro = p._id and pe.espera is null order by flagpagou desc",null);

		ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String,String>>();

		
		while( c.moveToNext() ) {

			HashMap<String,String> item = new HashMap<String,String>();
			
			
			item.put("id",String.valueOf( c.getInt(0) ) );
			item.put("rg",c.getString(1));
			item.put("nome",c.getString(2));
			item.put("telres",c.getString(3));
			item.put("telcel",c.getString(4));
			item.put("datanasc",c.getString(5) );
			item.put("rg_responsavel",c.getString(6) );
			item.put("nome_responsavel", c.getString(7) );
			item.put("pagou", String.valueOf( c.getInt(8) ) );
			item.put("idPassageiroExcursao",String.valueOf( c.getInt(9) ) );
			
			array.add(item);
			
			/*
			String str = (String) c.getString(0);
			if( str != null ) {
				return false;
			}
			return true;
			*/
			
		}

		return array;

	}
	
	public ArrayList<HashMap<String,String>> getPassageirosExcursaoEspera(int idExcursao) {

		Cursor c = null;

		c = getReadableDatabase().rawQuery("select p._id,p.rg,p.nome,p.telres,p.telcel,p.datanasc,p.rg_responsavel,p.nome_responsavel,pe.flagpagou,pe._id from passageiros p, passageirosexcursao pe where pe.idExcursao = "+ idExcursao+" and pe.idPassageiro = p._id and pe.espera = 'E'",null);

		ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String,String>>();


		while( c.moveToNext() ) {

			HashMap<String,String> item = new HashMap<String,String>();


			item.put("id",String.valueOf( c.getInt(0) ) );
			item.put("rg",c.getString(1));
			item.put("nome",c.getString(2));
			item.put("telres",c.getString(3));
			item.put("telcel",c.getString(4));
			item.put("datanasc",c.getString(5) );
			item.put("rg_responsavel",c.getString(6) );
			item.put("nome_responsavel", c.getString(7) );
			item.put("pagou", String.valueOf( c.getInt(8) ) );
			item.put("idPassageiroExcursao",String.valueOf( c.getInt(9) ) );
			
			array.add(item);

			/*
			 String str = (String) c.getString(0);
			 if( str != null ) {
			 return false;
			 }
			 return true;
			 */

		}

		return array;

	}
	
	
	public ArrayList<HashMap<String,String>> getNaoPassageirosExcursao(int idExcursao) {

		Cursor c = null;

		//c = getReadableDatabase().rawQuery("select p._id,p.rg,p.nome,p.telres,p.telcel,p.datanasc,p.rg_responsavel,p.nome_responsavel  from passageiros p, passageirosexcursao pe, excursao e where pe.idPassageiro = p._id and pe.idExcursao <> "+ idExcursao+"",null);
		c = getReadableDatabase().rawQuery("select p._id,p.rg,p.nome,p.telres,p.telcel,p.datanasc,p.rg_responsavel,p.nome_responsavel  from passageiros p where p._id not in (select p._id from passageiros p, passageirosexcursao pe where pe.idExcursao = "+ idExcursao+" and pe.idPassageiro = p._id)",null);
		
		ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String,String>>();


		while( c.moveToNext() ) {

			HashMap<String,String> item = new HashMap<String,String>();


			item.put("id",String.valueOf( c.getInt(0) ) );
			item.put("rg",c.getString(1));
			item.put("nome",c.getString(2));
			item.put("telres",c.getString(3));
			item.put("telcel",c.getString(4));
			item.put("datanasc",c.getString(5) );
			item.put("rg_responsavel",c.getString(6) );
			item.put("nome_responsavel", c.getString(7) );

			array.add(item);

			/*
			 String str = (String) c.getString(0);
			 if( str != null ) {
			 return false;
			 }
			 return true;
			 */

		}

		return array;

	}
	
	
	public ArrayList<HashMap<String,String>> getPassageiros(int idExcursao) {

		Cursor c = null;

		c = getReadableDatabase().rawQuery("select b.nome,b.telres,b.telcel from passageirosexcursao a, passageiros b where a.idExcursao = "+ idExcursao+" and a.idPassageiro = b._id",null);

		ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String,String>>();

		while( c.moveToNext() ) {

			HashMap<String,String> hashMap = new HashMap<String,String>();
			hashMap.put("nome", c.getString(0) );
			hashMap.put("telres", c.getString(0) );
			hashMap.put("telcel", c.getString(0) );
			array.add(hashMap);
		}

		return array;

	}
	
	public ArrayList<HashMap<String,String>> getPassageiros() {

		
		Cursor c = null;

		c = getReadableDatabase().rawQuery("select _id,nome,rg,telres,telcel from passageiros",null);

		ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String,String>>();

		
		while( c.moveToNext() ) {

			HashMap<String,String> item = new HashMap<String,String>();
			item.put("id", String.valueOf( c.getInt(0) ) );
			item.put("nome", c.getString(1) );
			item.put("rg", c.getString(2) );
			item.put("telcel", c.getString(3) );
			
			array.add(item);
			
			/*
			if( str != null ) {
				return false;
			}
			return true;
		}

		*/
		}
		
		return array;

	}
	
	public boolean buscaPassageiro(int idPassageiro) {

		Cursor c = null;

		c = getReadableDatabase().rawQuery("select nome from passageiros where _id = "+ idPassageiro,null);

		ArrayList array = new ArrayList();

		while( c.moveToNext() ) {

			String str = (String) c.getString(0);
			if( str != null ) {
				return false;
			}
			return true;
		}

		return false;

	}

	
	
}

