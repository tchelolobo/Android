package br.com.caelum.cadastro.support;

import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by android6406 on 23/06/16.
 */
public class WebClient {


    public String post(String json) throws IOException {

        String url = new String("https://www.caelum.com.br/mobile");
        MediaType tipo = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(tipo, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        Response response = client.newCall(request).execute();
        String resposta = response.body().string();

        return resposta;

    }
}
