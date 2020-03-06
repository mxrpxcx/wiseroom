package com.alura.wiseroom.ui.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.constants.Constants;
import com.alura.wiseroom.model.ColaboradorModel;
import com.alura.wiseroom.model.Event;
import com.alura.wiseroom.model.SalaModel;
import com.alura.wiseroom.network.HttpRequest;
import com.alura.wiseroom.ui.colaborador.ActivityPerfil;
import com.alura.wiseroom.ui.reserva.ActivityDatasReservadas;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() != null) {

                Log.i("teste qr ", intentResult.getContents());
                String idSalas = intentResult.getContents();

                verificaSala(idSalas);

            }
        } else {
            sair();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void recebeDados() {
        Intent intentMain = getIntent();

        if (intentMain.hasExtra("colaboradorLogado")) {

            ColaboradorModel col = (ColaboradorModel) intentMain.getSerializableExtra("colaboradorLogado");
            colaboradorLogado = col;

        }
    }

    public void verificaSala(final String idSala) {

        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("authorization", "secret");
            params.put("idSala", idSala);
            String url = Constants.url + "/sala/getSalaId";

            new HttpRequest(
                    getApplicationContext(),
                    params,
                    url,
                    "GET", "VerificaSala").doRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void customEventReceived(Event event) {
        if (event.getEventName().equals("VerificaSala" + Constants.eventSuccessLabel)) {
            Log.i("teste repsponse sala", event.getEventMsg());

            Gson gson = new Gson();

            SalaModel salaJson = gson.fromJson(event.getEventMsg(), SalaModel.class);
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
            finish();

        } else if (event.getEventName().equals("VerificaSala" + Constants.eventErrorLabel)) {
            Snackbar snackbar = Snackbar.make(null, "Erro ao receber salas", Snackbar.LENGTH_LONG);
            snackbar.getView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        sair();
    }

    public void sair() {
        Intent intent = new Intent(ActivityVerificarSala.this, ActivityPerfil.class);
        intent.putExtra("colaboradorLogado", colaboradorLogado);
        startActivity(intent);
        finish();
    }


}
