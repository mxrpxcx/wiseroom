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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityVerificarSala extends AppCompatActivity {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);



        if(intentResult != null){
            if (intentResult.getContents() !=  null){

                Log.i("teste qr ", intentResult.getContents());
                String idSalas = intentResult.getContents();

                verificaSala(idSalas);

            }else{

                Intent intent = new Intent(ActivityVerificarSala.this, ActivityPerfil.class);
                startActivity(intent);
            }
        }else{
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
        String url = "http://172.30.248.130:8080/ReservaDeSala/rest/sala/getSalaId";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resposta) {
                        Log.i("teste repsponse sala", resposta);

                        Gson gson = new Gson();

                        SalaModel salaJson = gson.fromJson(resposta, SalaModel.class);
                        SalaModel salaRecebidaJson = new SalaModel();

                        salaRecebidaJson.setIdSala(salaJson.getIdSala());
                        salaRecebidaJson.setNomeSala(salaJson.getNomeSala());
                        salaRecebidaJson.setCapacidadeSala(salaJson.getCapacidadeSala());
                        salaRecebidaJson.setDescricaoSala(salaJson.getDescricaoSala());
                        salaRecebidaJson.setAreaSala(salaJson.getAreaSala());

                        Log.i("TESTE RODANDO ?? ", "model objeto " + salaRecebidaJson.toString());

                        Intent intent = new Intent(ActivityVerificarSala.this, ActivityDatasReservadas.class);
                        intent.putExtra("colaboradorLogado", colaboradorLogado);
                        intent.putExtra("salaSelecionada", salaRecebidaJson);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("teste repsponse sala error", String.valueOf(error));

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("authorization", "secret");
                params.put("idSala", idSala);
                return params;
            }
        };
        mQueue.add(request);
    }

}
