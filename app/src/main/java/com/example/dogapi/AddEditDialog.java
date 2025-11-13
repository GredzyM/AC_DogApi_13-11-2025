package com.example.dogapi;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddEditDialog {

    public interface OnSaveListener {
        void onSave(String name);
    }

    public static void show(Context context, String title, String oldValue, OnSaveListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        EditText input = new EditText(context);
        input.setText(oldValue);
        input.setHint("Nombre de la raza");

        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(40, 30, 40, 30);
        layout.addView(input);

        builder.setView(layout);
        builder.setPositiveButton("Guardar", (d, w) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) listener.onSave(name);
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
