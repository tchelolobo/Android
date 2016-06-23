package br.com.caelum.cadastro;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.jar.Manifest;

/**
 * Created by android6406 on 22/06/16.
 */
public class Permissao {

    private static final int CODE = 123;
    private static ArrayList<String> listaPermissoes = new ArrayList<>();

    public static void fazPermissao(Activity activity) {

        String[] permissoes = {android.Manifest.permission.CALL_PHONE,
                                android.Manifest.permission.RECEIVE_SMS,
                                android.Manifest.permission.INTERNET};

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            for(String permissao : permissoes){
                if(activity.checkSelfPermission(permissao)
                        !=PackageManager.PERMISSION_GRANTED){
                    listaPermissoes.add(permissao);
                }
            }
        }

//        for (String permissao:permissoes){
//
//            if (activity.checkSelfPermission(permissao)!= PackageManager.PERMISSION_GRANTED){
//                listaPermissoes.add(permissao);
//            }
//        }
        request(activity);
    }

    private static void request(Activity activity) {

        String[] array = listaPermissoes.toArray(new String[]{});

        if(listaPermissoes.size()>0){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                activity.requestPermissions(array, CODE);
            }
        }

    }
}
