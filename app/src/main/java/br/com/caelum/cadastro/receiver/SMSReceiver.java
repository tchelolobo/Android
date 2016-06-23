package br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import br.com.caelum.cadastro.DAO;
import br.com.caelum.cadastro.R;

/**
 * Created by android6406 on 23/06/16.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[]) bundle.get("pdus");
        byte[] mensagem = (byte[]) mensagens[0];

        String formato = (String) bundle.get("format");

        SmsMessage sms = SmsMessage.createFromPdu(mensagem,formato);

        DAO dao = new DAO(context);

        if(dao.isAluno(sms.getDisplayOriginatingAddress()))
        {

            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();

            Toast.makeText(context, "Chegou SMS: " + sms.getDisplayOriginatingAddress(), Toast.LENGTH_LONG).show();
        }
    }
}
