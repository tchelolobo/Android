package task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import br.com.caelum.cadastro.support.WebClient;

/**
 * Created by android6406 on 23/06/16.
 */
public class EnviaAlunosTask extends AsyncTask<Object,Object,String> {

    private Context context;
    private String json;
    private ProgressDialog progress;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public EnviaAlunosTask(String json, Context context) {

        setContext(context);
        setJson(json);
    }

    @Override
    protected String doInBackground(Object...params) {


        WebClient client = new WebClient();
        String resposta = null;
        try {
            resposta = client.post(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resposta;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progress.dismiss();
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        progress = ProgressDialog.show(context,"Aguarde...","Envio de dados para a WEB",true,true);

    }
}
