package com.viewnext.energyapp.data.api;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import co.infinum.retromock.Retromock;
import co.infinum.retromock.BodyFactory;

public class RetromockClient {

    private static Retromock retromock = null;

    public static Retromock getRetromockInstance(Context context) { // Creamos retromock de forma parecida a retrofit
        if (retromock == null) {
            retromock = new Retromock.Builder()
                    .retrofit(RetrofitClient.getRetrofitInstance()) // Usamos la instancia de Retrofit existente
                    .defaultBodyFactory(new ResourceBodyFactory(context)) // Define como generar el contenido
                    .build();
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
