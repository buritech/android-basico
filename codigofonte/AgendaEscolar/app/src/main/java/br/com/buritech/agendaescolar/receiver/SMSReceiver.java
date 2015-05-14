package br.com.buritech.agendaescolar.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.buritech.agendaescolar.R;
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Recuperar dados do SMS recebido
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get("pdus");
        byte[] message = (byte[]) messages[0];
        //Objeto para acesso a dados do SMS
        SmsMessage sms = SmsMessage.createFromPdu(message);
        String telefone = sms.getDisplayOriginatingAddress();
        //Tocar uma MP3 salva em: /res/raw.sound.mp3
        MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
        mp.start();
        Toast.makeText(context,
                "SMS recebido do fone: " + telefone,
                Toast.LENGTH_LONG).show();
    }
}
