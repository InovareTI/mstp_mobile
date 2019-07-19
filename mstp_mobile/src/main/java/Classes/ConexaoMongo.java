package Classes;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;


import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;


public class ConexaoMongo {
	
	//root.getLogger("org.mongodb.driver.connection").setLevel(org.apache.log4j.Level.OFF);
	
	private MongoDatabase db;
	private MongoClient mongoClient;
	private MongoCredential credential;
	public ConexaoMongo() {
		
	
	//String host = "10.100.17.24:27017";
	String host = "localhost:27017";
	String dbname = "mstpDB";
    String user = "mstpwebDB";
    String password = "Xmqxf9qdCXusVYsH";

    //System.out.println("host: " + host + "\ndbname: " + dbname + "\nuser: " + user + "\npassword: " + password);

     credential = MongoCredential.createCredential(user, dbname, password.toCharArray());
     mongoClient = new MongoClient(new ServerAddress(host), Arrays.asList(credential));

     db = mongoClient.getDatabase(dbname);
	}
	public boolean InserirSimpels(String Collection,Document document) {
		
		MongoCollection<Document> coll = db.getCollection(Collection);
		try {
			if(!document.isEmpty()) {
				coll.insertOne(document);
			}else {
				System.out.println("Inserção abortada, documento vazio!");
			}
		
		}catch(MongoWriteException e) {
			System.out.println("Inserção NOK!");
			e.getMessage();
		}
		return true;
	}
public boolean InserirMuitos(String Collection,List<Document> document_list) {
		
		
		try {
			for(int i=0;i<document_list.size();i++) {
				InserirSimpels(Collection,document_list.get(i));
			}
			return true;
		}catch(MongoWriteException e) {
			System.out.println("Inserção NOK!");
			e.getMessage();
			return false;
		}
		
	}
	public FindIterable<Document> ConsultaSimplesSemFiltro(String Collection){
		FindIterable<Document> findIterable = db.getCollection(Collection).find(new Document());
		return findIterable;
	}
	public FindIterable<Document> ConsultaSimplesSemFiltroInicioLimit(String Collection,Integer inicio,Integer limit,int empresa){
		FindIterable<Document> findIterable = db.getCollection(Collection).find(new Document()).filter(Filters.eq("Empresa",empresa)).skip(inicio).limit(limit);
		return findIterable;
	}
	public FindIterable<Document> LastRegisterCollention(String Collection,String campo){
		Document order=new Document();
		order.append(campo, -1);
		FindIterable<Document> findIterable = db.getCollection(Collection).find(new Document()).sort(order).limit(1);
		return findIterable;
	}
	public FindIterable<Document> ConsultaSimplesComFiltroInicioLimit(String Collection,List<Bson> Filtros,Integer inicio,Integer limit){
		//System.out.println("Linhas a ignorar:"+inicio );
		//System.out.println("Limite de Linhas:"+limit );
		//System.out.println("Total de Linhas:"+ (limit-inicio));
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(Filtros)).skip(inicio).limit(limit);
		return findIterable;
	}
	public Long CountSimplesComFiltroInicioLimit(String Collection,List<Bson> Filtros){
		Long linhas = db.getCollection(Collection).count(Filters.and(Filtros));
		return linhas;
	}
	public Long CountSimplesSemFiltroInicioLimit(String Collection){
		Long linhas = db.getCollection(Collection).count(new Document());
		return linhas;
	}
   public FindIterable<Document> ConsultaCollectioncomFiltrosLista(String Collection,List<Bson> Filtros){
	
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(Filtros));
		return findIterable;
	}
   public AggregateIterable<Document> ConsultaSitecomFiltrosListaAggregation(String longitude,String latitude, List<Bson> Filtros){
		Document campos = new Document();
		Document campos2 = new Document();
		Document onde = new Document();
		onde.append("type", "Point");
		onde.append("coordinates", verifica_coordenadas(longitude,latitude));
		//System.out.println(onde.toJson());
		campos2.append("near", onde);
		campos2.append("spherical", true);
		//campos2.append("key", "GEO.geometry");
		campos2.append("distanceField", "dist.calculated");
		campos2.append("minDistance", 1);
		campos2.append("maxDistance", 1000);
		campos.append("$geoNear", campos2);
		
		AggregateIterable<Document> findIterable = db.getCollection("sites").aggregate(Arrays.asList(campos,Aggregates.match(Filters.and(Filtros))));
		return findIterable;
	}
   public List<Double> verifica_coordenadas(String lng,String lat) {
		 try {
			 if(!lng.equals(null) && !lat.equals(null)) {
				 Double f_lat=Double.parseDouble(lat.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
				 Double f_lng=Double.parseDouble(lng.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
				 return Arrays.asList(f_lng,f_lat);
			 }else {
				 return Arrays.asList(0.0,0.0);
			 }
		 }catch (NumberFormatException e) {
				return Arrays.asList(0.0,0.0);
		}
	 }
	public FindIterable<Document> ConsultaSimplesComFiltro(String Collection,String campo,String valor,int empresa){
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(Filters.eq("Empresa",empresa),Filters.eq(campo, valor)));
		return findIterable;
	}
public FindIterable<Document> ConsultaSimplesComFiltro(String Collection,List<Document> filtros,int empresa){
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find((Bson) filtros);
		return findIterable;
	}
	public FindIterable<Document> ConsultaOrdenadaRolloutCompleto(String Collection,int empresa){
		Document ordenacao=new Document();
		ordenacao.append("recid", 1);
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(Filters.eq("Linha_ativa",'Y'),Filters.eq("Empresa", empresa))).sort(ordenacao);
		return findIterable;
	}
	public FindIterable<Document> ConsultaOrdenada(String Collection,String CampoOrdem,int ordem,int empresa){
		Document ordenacao=new Document();
		ordenacao.append(CampoOrdem, ordem);
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(Filters.eq("Empresa", empresa))).sort(ordenacao);
		return findIterable;
	}
	public FindIterable<Document> ConsultaOrdenadaFiltroListaLimit1(String Collection,String CampoOrdem,int ordem,List<Bson>filtros){
		Document ordenacao=new Document();
		ordenacao.append(CampoOrdem, ordem);
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(filtros)).sort(ordenacao).limit(1);
		return findIterable;
	}
	public FindIterable<Document> ConsultaSimplesComFiltro(String Collection,String campo,Integer valor,int empresa){
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(Filters.eq("Empresa",empresa),Filters.eq(campo, valor)));
		return findIterable;
	}
	public Document BuscaAtualizaByRecID(String Collection,Document filtro,Document updates){
		
		return db.getCollection(Collection).findOneAndUpdate(filtro, updates);
		
	}
	public FindIterable<Document> ConsultaSimplesComFiltroDate(String Collection,String campo,Date valor,int empresa){
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.and(Filters.eq("Empresa",empresa),Filters.gte(campo, valor)));
		return findIterable;
	}

	public FindIterable<Document> ConsultaComplexaArray(String Collection,String campo,List<String> valores){
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.in(campo, valores));
		
		return findIterable;
	}
	public FindIterable<Document> ConsultaComplexaArrayRecID(String Collection,String campo,List<Integer> valores){
		
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.in(campo, valores));
		
		return findIterable;
	}
	public List<Document> consultaaggregation(String Collection,String Campo, String Valor,String agregado) {
		List<Document> resultado = new ArrayList<Document>();
		 Block<Document> printBlock = new Block<Document>() {
		        @Override
		        public void apply(final Document document) {
		        	resultado.add(document);
		            //System.out.println(document.toJson());
		        }
		    };
		db.getCollection(Collection).aggregate(
			      Arrays.asList(
			              Aggregates.match(Filters.eq(Campo, Valor)),
			              
			              Aggregates.group("$"+agregado, Accumulators.sum("count", 1))
			      )
			).forEach(printBlock);
		return resultado;
	}
	public List<Document> consultaaggregation(String Collection,List<Bson>Filtros,String agregado) {
		List<Document> resultado = new ArrayList<Document>();
		 Block<Document> printBlock = new Block<Document>() {
		        @Override
		        public void apply(final Document document) {
		        	resultado.add(document);
		            //System.out.println(document.toJson());
		        }
		    };
		db.getCollection(Collection).aggregate(
			      Arrays.asList(
			              Aggregates.match(Filters.and(Filtros)),
			              
			              Aggregates.group("$"+agregado, Accumulators.sum("count", 1))
			      )
			).forEach(printBlock);
		return resultado;
	}
	public List<Document> consultaaggregationMilestone(String Collection,String Campo, String Valor,String agregado) {
		List<Document> resultado = new ArrayList<Document>();
		 Block<Document> printBlock = new Block<Document>() {
		        @Override
		        public void apply(final Document document) {
		        	resultado.add(document);
		            //System.out.println(document.toJson());
		        }
		    };
		db.getCollection(Collection).aggregate(
			      Arrays.asList(
			              Aggregates.match(Filters.elemMatch("Milestone", Filters.eq(Campo, Valor))),
			              Aggregates.group("$"+agregado, Accumulators.sum("count", 1))
			      )
			).forEach(printBlock);
		return resultado;
	}
	public List<Document> consultaaggregationMilestone(String Collection,List<Bson>Filtros,String agregado) {
		List<Document> resultado = new ArrayList<Document>();
		 Block<Document> printBlock = new Block<Document>() {
		        @Override
		        public void apply(final Document document) {
		        	resultado.add(document);
		            //System.out.println(document.toJson());
		        }
		    };
		db.getCollection(Collection).aggregate(
			      Arrays.asList(
			              Aggregates.match(Filters.and(Filtros)),
			              Aggregates.group("$"+agregado, Accumulators.sum("count", 1))
			      )
			).forEach(printBlock);
		return resultado;
	}
	public List<String> ConsultaSimplesDistinct(String Collection,String campo,List<Bson>Filtros){
		MongoCursor<String> c = db.getCollection(Collection).distinct(campo,String.class).filter(Filters.and(Filtros)).iterator();
		List<String> valores= new ArrayList<String>();
		while(c.hasNext()) {
			valores.add(c.next());
		}
		return valores;
	}
	
	public Long ConsultaCountComplexa(String Collection,List<Bson> filtro){
		
		Long resutado=db.getCollection(Collection).count(Filters.and(filtro));
		
		return resutado;
	}
	public FindIterable<Document> ConsultaRolloutPeriodoData(String Collection,List<Bson>Filtros,int empresa){
		FindIterable<Document> findIterable = null;
		findIterable = db.getCollection(Collection).find(Filters.and(Filtros));
		return findIterable;
	}
	public FindIterable<Document> ConsultaFiltrosHistoricoRollout(String Collection,List<String>site,List<String>campos,List<String>autor,String dtinicio,String dtfim){
		
		FindIterable<Document> findIterable = null;
		if(site.toArray().length>0) {
			if(autor.toArray().length>0) {
				if(campos.toArray().length>0) {
					System.out.println("Realizando Consulta com Sites,Campos e autor para "+dtinicio+ "até "+dtfim);
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("SiteID", site),Filters.in("update_by", autor),Filters.in("Campo", campos),Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
				}else {
					System.out.println("Realizando Consulta com Sites e autor para "+dtinicio+ "até "+dtfim);
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("SiteID", site),Filters.in("update_by", autor),Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
				}
			}else if(campos.toArray().length>0) {
				System.out.println("Realizando Consulta com Sites,Campos para "+dtinicio+ "até "+dtfim);
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("SiteID", site),Filters.in("Campo", campos),Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
			}else {
				System.out.println("Realizando Consulta com Sites e autor para "+dtinicio+ "até "+dtfim);
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("SiteID", site),Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
			}
			//findIterable = findIterable.filter(Filters.in("SiteID", site));
		}else if(autor.toArray().length>0) {
			if(campos.toArray().length>0) {
				System.out.println("Realizando Consulta com Campos e autor para "+dtinicio+ "até "+dtfim);
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("update_by", autor),Filters.in("Campo", campos),Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
			}else {
				System.out.println("Realizando Consulta com autor para "+dtinicio+ "até "+dtfim);
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("update_by", autor),Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
			}
		}else if(campos.toArray().length>0) { 
			System.out.println("Realizando Consulta com Campos para "+dtinicio+ "até "+dtfim);
			findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("Campo", campos),Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
		}else {
			System.out.println("Realizando Consulta  para "+dtinicio+ "até "+dtfim);
			findIterable = db.getCollection(Collection).find(Filters.and(Filters.gte("update_time", checa_formato_data(dtinicio)),Filters.lte("update_time", checa_formato_data(dtfim))));
		}
		
		
		return findIterable;
	}
	public FindIterable<Document> ConsultaComplexaComFiltro(String Collection,List<Integer> recid,String tipo_valor,String operador,String campo,String valor){
		//System.out.println("Realizando consulta em:");
		//System.out.println(Collection);
		//System.out.println(campo);
		//System.out.println(valor);
		//System.out.println(operador);
		
		FindIterable<Document> findIterable=null;
		if(tipo_valor.equals("Data")) {
			if(recid.size()>0) {
				if(operador.equals("GREATER_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.gte(campo, checa_formato_data(valor)),Filters.in("recid", recid)));
				}else if(operador.equals("LESS_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.lte(campo, checa_formato_data(valor)),Filters.in("recid", recid)));
				}else if(operador.equals("EQUAL")) {
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.eq(campo, checa_formato_data(valor)),Filters.in("recid", recid)));
				}
			}else {
				if(operador.equals("GREATER_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.gte(campo, checa_formato_data(valor)));
				}else if(operador.equals("LESS_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.lte(campo, checa_formato_data(valor)));
				}else if(operador.equals("EQUAL")) {
				findIterable = db.getCollection(Collection).find(Filters.eq(campo, checa_formato_data(valor)));
				}
			}
		}
		
		return findIterable;
	}
	public Date checa_formato_data(String data) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d1=format.parse(data);
			return d1;
		}catch (ParseException e) {
			//System.out.println(data + " - Data inválida");
			return null;
			
		} 
		
	}
	public boolean RemoverMuitosSemFiltro(String Collection,int empresa) {
		MongoCollection<Document> coll = db.getCollection(Collection);
		try {
		coll.deleteMany(Filters.eq("Empresa",empresa));
		System.out.println("Todos os documentos foram removidos, de "+ Collection );
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	public boolean RemoverFiltroList(String Collection,List<Bson>filtros) {
		MongoCollection<Document> coll = db.getCollection(Collection);
		try {
		coll.deleteMany(Filters.and(filtros));
		System.out.println("Todos os documentos filtrados foram removidos, de "+ Collection );
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	public boolean AtualizaUm(String Collection,Document campo_condicao, Document campo_valor) {
		MongoCollection<Document> coll = db.getCollection(Collection);
		Document resultado;
		//System.out.println("chegou na funcao do update");
		//System.out.println("Filtro:"+campo_condicao.toJson());
		//System.out.println("Update:"+campo_valor.toJson());
		resultado=coll.findOneAndUpdate(campo_condicao, campo_valor);
		if(resultado.isEmpty()) {
			return false;
		}else {
			System.out.println("Update Realizado com Sucesso!");
			return true;
		}
	}
	public boolean AtualizaMuitos(String Collection,Document campo_condicao, Document campo_valor) {
		MongoCollection<Document> coll = db.getCollection(Collection);
		UpdateResult resultado;
		System.out.println("chegou na funcao do update");
		System.out.println("Filtro:"+campo_condicao.toJson());
		System.out.println("Update:"+campo_valor.toJson());
		resultado=coll.updateMany(campo_condicao, campo_valor);
		if(resultado.getModifiedCount()==0) {
			return false;
		}else {
			System.out.println(resultado.getModifiedCount()+" - documentos atualizados");
			return true;
		}
	}
	public void criar2dsphereindex(){
		db.getCollection("sites").createIndex(Indexes.geo2dsphere("GEO.geometry"));
		
	}
	public void fecharConexao() {
		 this.mongoClient.close();
		 //System.out.println("Conexão encerrada com sucesso. Chamada de :"+origem);
	}
}
