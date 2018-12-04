package Classes;

import com.mercadopago.MP;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;



public class MercadoPago {
	private Pessoa pessoa;
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	private MP mp;
	
	public MercadoPago(){
		 mp = new MP ("1973664232249118","QtCnaHM1QLxwyGSWJjUGFcISixicxcBu");
		 JSONObject preference;
		try {
			preference = mp.getPreference("PREFERENCE_ID");
			System.out.println(preference.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
	}
	
	public void preferencias(){
		JSONObject preference;
		try {
			
			preference = mp.getPreference("PREFERENCE_ID");
			System.out.println(preference.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONArray pagamentos(){
		try{
			
			
		Map<String, Object> filters = new HashMap<String, Object> ();
		   filters.put("site_id", "MLB"); // Argentina: MLA; Brasil: MLB; Mexico: MLM; Venezuela: MLV; Colombia: MCO
		   filters.put("external_reference", "odontoFlow");
		   filters.put("email",pessoa.get_PessoaEmail());
		   System.out.println(filters.toString());
		   JSONObject searchResult2= mp.get ("/v1/customers/search",filters);
		   JSONArray clientes = searchResult2.getJSONObject("response").getJSONArray("results");
		// Search payment data according to filters
		JSONObject searchResult = mp.searchPayment (filters);
		
		System.out.println("Tamanho do usuario encontrado: "+ clientes.length());
		System.out.println(clientes.toString());
		JSONArray results = searchResult.getJSONObject("response").getJSONArray("results");
		System.out.println(results.toString());
		/*for (int i = 0; i < results.length(); i++) {
		    System.out.println(results.getJSONObject(i).getJSONObject("collection").getString("id"));
		    System.out.println(results.getJSONObject(i).getJSONObject("collection").getString("external_reference"));
		    System.out.println(results.getJSONObject(i).getJSONObject("collection").getString("status"));
		}*/
		 return results;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONArray results=null;
			return results;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONArray results=null;
			return results;
		}
	}
	public void site(){
		try {
			//JSONObject result = mp.get ("/sites", null, false);
			JSONObject result= mp.get ("/payments/search",null,true);
			System.out.println(result.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
