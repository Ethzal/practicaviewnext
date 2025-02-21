package com.viewnext.energyapp.data.api;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import co.infinum.retromock.Retromock;
import co.infinum.retromock.BodyFactory;

public class RetromockClient {

    private static volatile Retromock retromock = null;

    private RetromockClient(){
        // Constructor privado y vacío para evitar que se creen instancias externas
    }

    public static Retromock getRetromockInstance(Context context) {
        // Usamos sincronización doble para evitar crear múltiples instancias
        if (retromock == null) {
            synchronized (RetromockClient.class) {
                if (retromock == null) {
                    retromock = new Retromock.Builder()
                            .retrofit(RetrofitClient.getRetrofitInstance())  // Usa la instancia de Retrofit
                            .defaultBodyFactory(new ResourceBodyFactory(context))  // Define el generador de cuerpos mock
                            .build();
                }
            }
        }
        return retromock;
    }

    final static class ResourceBodyFactory implements BodyFactory { // BodyFactory para saber como generar el cuerpo de la respuesta

        private final Context context; // Contexto para acceder a los recursos de la aplicación como lo que haya en assets

        // Constructor
        ResourceBodyFactory(Context context) {
            this.context = context;
        }

        @Override
        public InputStream create(@NonNull final String input) throws IOException { // Metodo principal de BofyFactory
            if ("facturas_mock.json".equals(input)) {
                return context.getAssets().open("facturas_mock.json"); // Se carga las facturas desde assets
            }if ("detalles_mock.json".equals(input)) {
                return context.getAssets().open("detalles_mock.json"); // Se carga los detealles desde assets
            }

            return null;
        }
    }
}
