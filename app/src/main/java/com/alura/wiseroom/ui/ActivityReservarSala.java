package com.alura.wiseroom.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.SalaModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityReservarSala extends AppCompatActivity {
    final Activity activity = this;
    ColaboradorModel colaboradorLogado;
    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_sala);
        mQueue = Volley.newRequestQueue(this);
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Escolha a sala");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();

        recebeDados();

        Log.i("Teste id col", colaboradorLogado.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null) {
            if (intentResult.getContents() != null) {

                Log.i("teste qr ", intentResult.getContents());
                String idSalas = intentResult.getContents();

                        verificaSala(idSalas);

            }else{

                Log.i("TESTE REDIRECIONAMENTO  ", "REDIRECIOAAWFOAWFO");
                Intent intent = new Intent(ActivityReservarSala.this, ActivityPerfil.class);
                startActivity(intent);
            }
        }else{
            Log.i("TESTE REDIRECIONAMENTO MAIS DEBAIXO ", "REDIRECIOAAWFOAWFO");

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void recebeDados() {
        Intent intentMain = getIntent();
        if(intentMain.hasExtra("colaboradorLogado")){
            ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
            colaboradorLogado = col;
        }
    }

    public void verificaSala(final String idSala){
        String url = "http://172.30.248.130/listaSala.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resposta) {
                        if (resposta.length() > 0) {
                            try {
                                for (int i = 0; i < resposta.length(); i++) {

                                    JSONObject salaJson = resposta.getJSONObject(i);
                                    SalaModel salaRecebidaJson = new SalaModel();

                                    salaRecebidaJson.setIdSala(salaJson.getString("idSala"));
                                    salaRecebidaJson.setNomeSala(salaJson.getString("nomeSala"));
                                    salaRecebidaJson.setCapacidadeSala(salaJson.getInt("capacidadeSala"));
                                    salaRecebidaJson.setDescricaoSala(salaJson.getString("descricaoSala"));
                                    salaRecebidaJson.setAreaSala(salaJson.getDouble("areaSala"));

                                    Log.i("TESTE RODANDO ?? ", "model objeto " + salaRecebidaJson.toString());

                                    Intent intent = new Intent(ActivityReservarSala.this, ActivityAgendarDataSala.class);
                                    intent.putExtra("colaboradorLogado", colaboradorLogado);
                                    intent.putExtra("salaSelecionada", salaRecebidaJson);
                                    startActivity(intent);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idSala", idSala);
                return params;
            }
        };
        mQueue.add(request);
    }

}
