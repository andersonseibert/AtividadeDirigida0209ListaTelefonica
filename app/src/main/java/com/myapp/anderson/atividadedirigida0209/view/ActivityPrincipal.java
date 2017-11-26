package com.myapp.anderson.atividadedirigida0209.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.myapp.anderson.atividadedirigida0209.R;
import com.myapp.anderson.atividadedirigida0209.dao.ContatoDAO;
import com.myapp.anderson.atividadedirigida0209.dao.SimpleDatabaseHelper;
import com.myapp.anderson.atividadedirigida0209.model.Contato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anderson on 02/09/2017.
 */

public class ActivityPrincipal extends Activity {

    /* VARIAVEIS */
    private EditText edtNome;
    private EditText edtNumero;
    private ListView lvRegistros;
    private List<String> contatos;
    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtNumero = (EditText)findViewById(R.id.edtNumero);
        lvRegistros = (ListView)findViewById(R.id.lvContatos);
        lvRegistros.setLongClickable(true);
        contatos = new ArrayList<String>();

        /*CRUD*/
        ExcluirItem();
        //EditarItem();
        ListarItens();

    }

    public void AdicionarRegistro(View view){

        if(edtNome.getText().toString().matches("")){
            Toast.makeText(this, "Informe o Nome", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edtNumero.getText().toString().matches("")){
            Toast.makeText(this, "Informe o Número", Toast.LENGTH_SHORT).show();
            return;
        }

        contatos.add(edtNome.getText().toString() + " - " + edtNumero.getText().toString());
        edtNome.setText("");
        edtNumero.setText("");
        ListarItens();
    }

    public void ListarItens () {
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contatos);
        lvRegistros.setAdapter(adaptador);
    }

    public void EditarItem(){
        lvRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] contato = contatos.get(position).split("-");
                edtNome.setText((contato[0]));
                edtNumero.setText((contato[1]));
                contatos.remove(position);
                ListarItens();
            }
        });
    }

    public void ExcluirItem(){
        lvRegistros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int posicao, long id) {
                MostrarDialogo(posicao);
                return false;
            }
        });
    };

    public void MostrarDialogo(final int posicaoRemover){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Deseja Remover o Registro?");

        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                contatos.remove(posicaoRemover);
                ListarItens();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
